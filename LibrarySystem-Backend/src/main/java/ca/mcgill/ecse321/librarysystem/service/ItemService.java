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
public class ItemService  {
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	LibrarianRepository librarianRepository;
		
	// creates book, returns it so we know it's not null 
	@Transactional 
	public Book createBook(String idNum,
		String itemTitle,
		String description,
		String imageUrl,
		String genre,
		Date publishDate,
		boolean isReservable,
		String author,
		String publisher)
	{
		if (librarianRepository.findById(idNum) == null) {
			throw new IllegalArgumentException("You do not have permission to create an item");
		}
		String itemNumber = "Book-" + getAllBooks().size() + itemTitle.trim();
		Book book = new Book();
	    book.setItemTitle(itemTitle);
	    book.setDescription(description);
	    book.setImageUrl(imageUrl);
	    book.setItemNumber(itemNumber);
	    book.setGenre(genre);
	    book.setPublishDate(publishDate);
	    book.setIsReservable(isReservable);
		book.setAuthor(author);
		book.setPublisher(publisher);
		book.setType(Item.Type.Book);
		itemRepository.save(book);
	    return book;		
	}

	@Transactional 
	public Archive createArchive(String idNum,
		String itemTitle,
		String description,
		String imageUrl,
		String genre,
		Date publishDate,
		boolean isReservable)
	{
		if (librarianRepository.findById(idNum) == null) {
			throw new IllegalArgumentException("You do not have permission to create an item");
		}
		String itemNumber = "Archive-" + itemRepository.findItemsByType(Item.Type.Archive).size() + itemTitle.trim();
		Archive archive = new Archive();
		archive.setItemTitle(itemTitle);
		archive.setDescription(description);
		archive.setImageUrl(imageUrl);
		archive.setItemNumber(itemNumber);
		archive.setGenre(genre);
		archive.setPublishDate(publishDate);
		archive.setIsReservable(isReservable);
		archive.setType(Item.Type.Archive);
		itemRepository.save(archive);
	    return archive;		
	}
	
	@Transactional 
	public MusicAlbum createMusicAlbum(String idNum,
		String itemTitle,
		String description,
		String imageUrl,
		String genre,
		Date publishDate,
		boolean isReservable,String artist, String recordingLabel)
	{
		if (librarianRepository.findById(idNum) == null) {
			throw new IllegalArgumentException("You do not have permission to create an item");
		}
		String itemNumber = "MusicAlbum-" + itemRepository.findItemsByType(Item.Type.MusicAlbum).size() + itemTitle.trim();
		MusicAlbum musicAlbum = new MusicAlbum();
		musicAlbum.setItemTitle(itemTitle);
		musicAlbum.setDescription(description);
		musicAlbum.setImageUrl(imageUrl);
		musicAlbum.setItemNumber(itemNumber);
		musicAlbum.setGenre(genre);
		musicAlbum.setPublishDate(publishDate);
		musicAlbum.setIsReservable(isReservable);
		musicAlbum.setType(Item.Type.MusicAlbum);
		musicAlbum.setArtist(artist);
		musicAlbum.setRecordingLabel(recordingLabel);
		itemRepository.save(musicAlbum);
	    return musicAlbum;		
	}
	
	@Transactional 
	public PrintedMedia createPrintedMedia(String idNum,
		String itemTitle,
		String description,
		String imageUrl,
		String genre,
		Date publishDate,
		boolean isReservable, String issueNumber)
	{
		if (librarianRepository.findById(idNum) == null) {
			throw new IllegalArgumentException("You do not have permission to create an item");
		}
		String itemNumber = "PrintedMedia-" + itemRepository.findItemsByType(Item.Type.MusicAlbum).size() + itemTitle.trim();
		PrintedMedia printedMedia = new PrintedMedia();
		printedMedia.setItemTitle(itemTitle);
		printedMedia.setDescription(description);
		printedMedia.setImageUrl(imageUrl);
		printedMedia.setItemNumber(itemNumber);
		printedMedia.setGenre(genre);
		printedMedia.setPublishDate(publishDate);
		printedMedia.setIsReservable(isReservable);
		printedMedia.setType(Item.Type.PrintedMedia);
		printedMedia.setIssueNumber(issueNumber);
		itemRepository.save(printedMedia);
	    return printedMedia;		
	}
	
	@Transactional 
	public Movie createMovie(String idNum,
		String itemTitle,
		String description,
		String imageUrl,
		String genre,
		Date publishDate,
		boolean isReservable,  String productionCompany,
		 String movieCast,
		 String director,
		 String producer)
	{
		if (librarianRepository.findById(idNum) == null) {
			throw new IllegalArgumentException("You do not have permission to create an item");
		}
		String itemNumber = "Movie-" + itemRepository.findItemsByType(Item.Type.Movie).size() + itemTitle.trim();
		Movie movie = new Movie();
		movie.setItemTitle(itemTitle);
		movie.setDescription(description);
		movie.setImageUrl(imageUrl);
		movie.setItemNumber(itemNumber);
		movie.setGenre(genre);
		movie.setPublishDate(publishDate);
		movie.setIsReservable(isReservable);
		movie.setType(Item.Type.Movie);
		movie.setProductionCompany(productionCompany);
		movie.setProducer(producer);
		movie.setDirector(director);
		movie.setMovieCast(movieCast);
		itemRepository.save(movie);
	    return movie;		
	}
	
	// looks for a book with the given item number, returns them if found
	@Transactional 
	public Item getItem(String itemNumber) {
		Item item = itemRepository.findItemByItemNumber(itemNumber); 
		return item;
	}
	
	@Transactional 
	public List<Item> getAll() {
		return toList(itemRepository.findAll()); 
	}
	
	@Transactional 
	public List<Book> getAllBooks() {
		return toList(itemRepository.findItemsByType(Item.Type.Book)); 
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
