package ca.mcgill.ecse321.librarysystem.controller;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.librarysystem.LibrarySystemApplication;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.service.*;

@CrossOrigin(origins = "*")
@RestController
public class BookController {
	@Autowired
	private BookService bookService;
	
	@GetMapping(value = { "/items/books", "/items/books/" })
	public List<BookDto> getAllBooks() {
		System.out.println("Flag Get"); 
		return bookService.getAllBooks().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/items/books/{itemNumber}", "/items/books/{itemNumber}/" })
	public BookDto getBook(@PathVariable("itemNumber") String itemNumber) {
		System.out.println("Flag Get" + itemNumber); 
		return convertToDto(bookService.getBook(itemNumber));
	}

	@PostMapping(value = { "/items/books/{itemNumber}", "/items/books/{itemNumber}/" })
	public BookDto createBook(@PathVariable("itemNumber") String itemNumber,
			@RequestParam String itemTitle,
			@RequestParam String description,
			@RequestParam String imageURL,
			@RequestParam String publisher,
			@RequestParam String author,
			@RequestParam String genre,
			@RequestParam String publishDate,
			@RequestParam boolean isReservable
			) {
		if (itemTitle == null) {
			itemTitle = "title";
		}
		if (description == null) {
			description = "description";
		}
		if (imageURL == null) {
			imageURL = "imageURL";
		}
		if (publisher == null) {
			publisher = "publisher";
		}
		if (author == null) {
			author = "author";
		}
		if (genre == null) {
			genre = "genre";
		}
		if (publishDate == null) {
			publishDate = LocalDate.now().toString();
		}
		System.out.println("Flag Post"); 
		Book book = bookService.createBook(itemTitle,
		     description,
		     imageURL,
		     itemNumber,
		     genre,
		     Date.valueOf(LocalDate.parse(publishDate)),
		     isReservable,
		     author,
		     publisher
		     );
		System.out.println(book.getAuthor());
		return convertToDto(book);
	}
	
	@PostMapping(value = { "/items/newEmptyBook/{itemNumber}", "/items/newEmptyBook/{itemNumber}/" })
	public BookDto createEmptyBook(@PathVariable("itemNumber") String itemNumber
	
			) {

			String itemTitle = "title";

	
			String description = "description";

			String imageURL = "imageURL";

			String publisher = "publisher";

	
			String author = "author";
			String genre = "genre";
		

			String publishDate = LocalDate.now().toString();
			boolean isReservable = true;
		System.out.println("Flag Post"); 
		Book book = bookService.createBook(itemTitle,
		     description,
		     imageURL,
		     itemNumber,
		     genre,
		     Date.valueOf(LocalDate.parse(publishDate)),
		     isReservable,
		     author,
		     publisher
		     );
		System.out.println(book.getAuthor());
		return convertToDto(book);
	}
	
	private BookDto convertToDto(Book book){
		BookDto bookDto = new BookDto(book.getItemTitle(), book.getDescription(), book.getImageUrl(),book.getPublisher(), book.getAuthor(), book.getGenre(), book.getPublishDate(), book.getIsReservable(),book.getCurrentReservationId(), book.getItemNumber());
		System.out.println(bookDto.getAuthor());
		return bookDto;
	}
}
