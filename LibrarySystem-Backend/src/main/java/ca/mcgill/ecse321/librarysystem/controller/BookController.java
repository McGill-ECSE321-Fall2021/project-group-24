package ca.mcgill.ecse321.librarysystem.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping(value = { "/books", "/books/" })
	public List<BookDto> getAllBooks() {
		System.out.println("Flag Get"); 
		return bookService.getAllBooks().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/books/{itemNum}", "/books/{itemNum}/" })
	public BookDto createBook(@PathVariable("itemNum") String itemNum) {
		System.out.println("Flag Post"); 
		Book book = bookService.createBook(itemNum);
		return convertToDto(book);
	}
	
	private BookDto convertToDto(Book book){
		BookDto bookDto = new BookDto(book.getItemTitle(), book.getDescription(), book.getImageUrl(), book.getPublisher(), book.getAuthor(), book.getIsCheckedOut(), book.getGenre(), book.getPublishDate(), book.getIsReservable(),book.getItemNumber());
		return bookDto;
	}
}
