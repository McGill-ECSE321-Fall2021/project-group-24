package ca.mcgill.ecse321.librarysystem.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;

@Service
public class BookService  {
	
	@Autowired 
	BookRepository bookRepository;
		
	// creates book, returns it so we know it's not null 
	@Transactional 
	public Book createBook(
		String itemTitle,
		String description,
		String imageUrl,
		String itemNumber,
		String genre,
		Date publishDate,
		boolean isReservable,
		boolean isCheckedOut,
		String author,
		String publisher)
	{
		Book book = new Book();
	    book.setItemTitle(itemTitle);
	    book.setDescription(description);
	    book.setImageUrl(imageUrl);
	    book.setItemNumber(itemNumber);
	    book.setGenre(genre);
	    book.setPublishDate(publishDate);
	    book.setIsReservable(isReservable);
		book.setIsCheckedOut(isCheckedOut);
		book.setAuthor(author);
		book.setPublisher(publisher);

	    bookRepository.save(book);
	    return book;		
	}
	public Book createBook(String itemNumber) {
		Book book = new Book(); 
		long currentMillis = System.currentTimeMillis();
		Date currentDay = new Date(currentMillis);

		book.setItemTitle("itemTitle");
	    book.setDescription("description");
	    book.setImageUrl("imageUrl");
	    book.setItemNumber(itemNumber);
	    book.setGenre("genre");
	    book.setPublishDate(currentDay);
	    book.setIsReservable(true);
		book.setIsCheckedOut(false);
	    book.setAuthor("author");
		book.setPublisher("publisher");
		bookRepository.save(book);
		return book;
	}
	
	// looks for a book with the given item number, returns them if found
	@Transactional 
	public Book getBook(String itemNumber) {
		Book book = bookRepository.findBookByItemNumber(itemNumber); 
		return book;
	}
	
	@Transactional 
	public List<Book> getAllBooks() {
		return toList(bookRepository.findAll()); 
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
