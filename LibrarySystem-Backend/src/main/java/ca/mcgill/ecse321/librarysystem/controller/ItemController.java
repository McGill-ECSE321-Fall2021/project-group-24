package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/items")
public class ItemController {

  @Autowired
  private ItemService itemService;

  /***
   * 
   * @return list of all items
   */
  @GetMapping(value = { "/all/", "/all" })
  public List<ItemDto> getAllItems() {
    System.out.println("Flag Get");
    return itemService
      .getAll()
      .stream()
      .map(lib -> convertToDto(lib))
      .collect(Collectors.toList());
  }

  /***
   * @param itemNumber
   * @return get single item
   */
  @GetMapping(value = { "/{itemNumber}", "/{itemNumber}/" })
  public ResponseEntity<?> getItem(@PathVariable("itemNumber") String itemNumber) {
    Item item = itemService.getItem(itemNumber);
	try {
		return new ResponseEntity<Object>(convertToDto(item), HttpStatus.OK);
	} catch (IllegalArgumentException e) {
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

  }

/***
 * 
 * @param itemTitle
 * @param description
 * @param imageURL
 * @param publisher
 * @param author
 * @param genre
 * @param publishDate
 * @param isReservable
 * @param currentUserId
 * @return
 */
@PostMapping(value = { "/create_book/{", "/create_book" })
  public ResponseEntity<?> createBook(
    @RequestParam String itemTitle,
    @RequestParam String description,
    @RequestParam String imageURL,
    @RequestParam String publisher,
    @RequestParam String author,
    @RequestParam String genre,
    @RequestParam String publishDate,
    @RequestParam boolean isReservable,
    @RequestParam String currentUserId
  ) {

    try {
    	 Book book = itemService.createBook(
    		      currentUserId,
    		      itemTitle,
    		      description,
    		      imageURL,
    		      genre,
    		      Date.valueOf(LocalDate.parse(publishDate)),
    		      isReservable,
    		      author,
    		      publisher
    		    );
    	return new ResponseEntity<Object>(convertToBookDto(book), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
    	return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
  }

/***
 * 
 * @param itemTitle
 * @param description
 * @param imageURL
 * @param publisher
 * @param author
 * @param genre
 * @param publishDate
 * @param isReservable
 * @param movieCast
 * @param productionCompany
 * @param director
 * @param producer
 * @param currentUserId
 * @return
 */
  @PostMapping(value = { "/create_movie", "/create_movie/" })
  public ResponseEntity<?> createMovie(
    @RequestParam String itemTitle,
    @RequestParam String description,
    @RequestParam String imageURL,
    @RequestParam String publisher,
    @RequestParam String author,
    @RequestParam String genre,
    @RequestParam String publishDate,
    @RequestParam boolean isReservable,
    @RequestParam String movieCast,
    @RequestParam String productionCompany,
    @RequestParam String director,
    @RequestParam String producer,
    @RequestParam String currentUserId
  ) {
   
    try {
    	 Movie movie = itemService.createMovie(
    		      currentUserId,
    		      itemTitle,
    		      description,
    		      imageURL,
    		      genre,
    		      Date.valueOf(LocalDate.parse(publishDate)),
    		      isReservable,
    		      productionCompany,
    		      movieCast,
    		      director,
    		      producer
    		    );
    	return new ResponseEntity<Object>(convertToMovieDto(movie), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
    	return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /***
   * 
   * @param itemTitle
   * @param description
   * @param imageURL
   * @param genre
   * @param publishDate
   * @param isReservable
   * @param currentUserId
   * @return
   */
  @PostMapping(value = { "/create_archive", "/create_archive/" })
  public ResponseEntity<?> createArchive(
    @RequestParam String itemTitle,
    @RequestParam String description,
    @RequestParam String imageURL,
    @RequestParam String genre,
    @RequestParam String publishDate,
    @RequestParam boolean isReservable,
    @RequestParam String currentUserId
  ) {
    System.out.println("Flag Post");

    try {
        Archive archive = itemService.createArchive(
        	      currentUserId,
        	      itemTitle,
        	      description,
        	      imageURL,
        	      genre,
        	      Date.valueOf(LocalDate.parse(publishDate)),
        	      isReservable
        	    );
    	return new ResponseEntity<Object>(convertToArchiveDto(archive), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
    	return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /***
   * 
   * @param itemTitle
   * @param description
   * @param imageURL
   * @param genre
   * @param publishDate
   * @param isReservable
   * @param currentUserId
   * @param artist
   * @param recordingLabel
   * @return
   */
  @PostMapping(
    value = { "/create_musicAlbum", "/create_musicAlbum/" }
  )
  public ResponseEntity<?> createMusicAlbum(
    @RequestParam String itemTitle,
    @RequestParam String description,
    @RequestParam String imageURL,
    @RequestParam String genre,
    @RequestParam String publishDate,
    @RequestParam boolean isReservable,
    @RequestParam String currentUserId,
    @RequestParam String artist,
    @RequestParam String recordingLabel
  ) {
    System.out.println("Flag Post");
  
    try {
    	  MusicAlbum musicAlbum = itemService.createMusicAlbum(
    		      currentUserId,
    		      itemTitle,
    		      description,
    		      imageURL,
    		      genre,
    		      Date.valueOf(LocalDate.parse(publishDate)),
    		      isReservable,
    		      artist,
    		      recordingLabel
    		    );
    	return new ResponseEntity<Object>(convertToMusicAlbumDto(musicAlbum), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
    	return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /***
   * 
   * @param itemTitle
   * @param description
   * @param imageURL
   * @param genre
   * @param publishDate
   * @param isReservable
   * @param currentUserId
   * @param issueNumber
   * @return
   */
  @PostMapping(
    value = { "/create_printedMedia", "/create_printedMedia/" }
  )
  public ResponseEntity<?> createPrintedMedia(
    @RequestParam String itemTitle,
    @RequestParam String description,
    @RequestParam String imageURL,
    @RequestParam String genre,
    @RequestParam String publishDate,
    @RequestParam boolean isReservable,
    @RequestParam String currentUserId,
    @RequestParam String issueNumber
  ) {
    System.out.println("Flag Post");

    try {
        PrintedMedia printedMedia = itemService.createPrintedMedia(
        	      currentUserId,
        	      itemTitle,
        	      description,
        	      imageURL,
        	      genre,
        	      Date.valueOf(LocalDate.parse(publishDate)),
        	      isReservable,
        	      issueNumber
        	    );
    	return new ResponseEntity<Object>(convertToPrintedMediaDto(printedMedia), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
    	return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

/***
 * 
 * @param itemNumber
 * @param currentUserId
 * @return
 */
  @DeleteMapping(
    value = { "/delete/{itemNumber}", "/delete/{itemNumber}/" }
  )
  public ResponseEntity<?> deleteItem(
    @PathVariable("itemNumber") String itemNumber,
    @RequestParam String currentUserId
  ) {
    Item item = itemService.getItem(itemNumber);
        try {
        	 itemService.deleteItem(currentUserId, itemNumber);
             String deletedItem =
               "Deleted item of type " +
               item.getType().toString() +
               " with itemNumber " +
               itemNumber;
    	return new ResponseEntity<Object>(deletedItem, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
    	return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  private ItemDto convertToDto(Item item) {
    if (item.getType().equals(Item.Type.Book)) {
      return convertToBookDto((Book) item);
    } else if (item.getType().equals(Item.Type.Archive)) {
      return convertToArchiveDto((Archive) item);
    } else if (item.getType().equals(Item.Type.Movie)) {
      return convertToMovieDto((Movie) item);
    } else if (item.getType().equals(Item.Type.PrintedMedia)) {
      return convertToPrintedMediaDto((PrintedMedia) item);
    } else {
      return convertToMusicAlbumDto((MusicAlbum) item);
    }
  }

  private BookDto convertToBookDto(Book book) {
    BookDto bookDto = new BookDto(
      book.getItemTitle(),
      book.getDescription(),
      book.getImageUrl(),
      book.getPublisher(),
      book.getAuthor(),
      book.getGenre(),
      book.getPublishDate(),
      book.getIsReservable(),
      book.getCurrentReservationId(),
      book.getItemNumber()
    );
    return bookDto;
  }

  private ArchiveDto convertToArchiveDto(Archive archive) {
    ArchiveDto archiveDto = new ArchiveDto(
      archive.getItemTitle(),
      archive.getDescription(),
      archive.getImageUrl(),
      archive.getGenre(),
      archive.getPublishDate(),
      archive.getIsReservable(),
      archive.getCurrentReservationId(),
      archive.getItemNumber()
    );
    return archiveDto;
  }

  private MovieDto convertToMovieDto(Movie movie) {
    MovieDto movieDto = new MovieDto(
      movie.getItemTitle(),
      movie.getDescription(),
      movie.getImageUrl(),
      movie.getGenre(),
      movie.getPublishDate(),
      movie.getIsReservable(),
      movie.getCurrentReservationId(),
      movie.getItemNumber(),
      movie.getProductionCompany(),
      movie.getMovieCast(),
      movie.getDirector(),
      movie.getProducer()
    );
    return movieDto;
  }

  private PrintedMediaDto convertToPrintedMediaDto(PrintedMedia printedMedia) {
    PrintedMediaDto printedMediaDto = new PrintedMediaDto(
      printedMedia.getItemTitle(),
      printedMedia.getDescription(),
      printedMedia.getImageUrl(),
      printedMedia.getGenre(),
      printedMedia.getPublishDate(),
      printedMedia.getIsReservable(),
      printedMedia.getCurrentReservationId(),
      printedMedia.getItemNumber(),
      printedMedia.getIssueNumber()
    );
    return printedMediaDto;
  }

  private MusicAlbumDto convertToMusicAlbumDto(MusicAlbum musicAlbum) {
    MusicAlbumDto musicAlbumDto = new MusicAlbumDto(
      musicAlbum.getItemTitle(),
      musicAlbum.getDescription(),
      musicAlbum.getImageUrl(),
      musicAlbum.getGenre(),
      musicAlbum.getPublishDate(),
      musicAlbum.getIsReservable(),
      musicAlbum.getCurrentReservationId(),
      musicAlbum.getItemNumber(),
      musicAlbum.getArtist(),
      musicAlbum.getRecordingLabel()
    );
    return musicAlbumDto;
  }
}
