package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Date;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestLibrarySystemPersistence {

  @Autowired
  private ArchiveRepository archiveRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private HeadLibrarianRepository headLibrarianRepository;

  @Autowired
  private LibrarianRepository librarianRepository;

  @Autowired
  private LibrarySystemRepository librarySystemRepository;

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private MusicAlbumRepository musicAlbumRepository;

  @Autowired
  private PatronRepository patronRepository;

  @Autowired
  private PrintedMediaRepository printedMediaRepository;

  @Autowired
  private RoomRepository roomRepository;

  @BeforeEach
  public void clearDatabase() {
    // First, we clear registrations to avoid exceptions due to inconsistencies
    //librarySystemRepository.deleteAll();
    // Then we can clear the other tables
    archiveRepository.deleteAll();
    bookRepository.deleteAll();
    headLibrarianRepository.deleteAll();
    librarianRepository.deleteAll();
//    movieRepository.deleteAll();
    musicAlbumRepository.deleteAll();
    patronRepository.deleteAll();
    printedMediaRepository.deleteAll();
    roomRepository.deleteAll();
  }

  //------------------TESTING ARCHIVE------------------------//
//  @Test
//  public void testPersistAndLoadArchive() {
//    // First example for object save/load
//    Archive archive = new Archive();
//    // First example for attribute save/load
//    String itemTitle = "Archive Item Title";
//    String description = "Archive Description";
//    String imageURL = "Archive Image URL";
//    String itemNumber = "Archive Item Number";
//    String genre = "Archive Genre";
//    Date publishDate = Date.valueOf("2021-10-15");
//    boolean isReservable = true;
//    boolean isCheckedOut = true;
//    
//    archive.setItemTitle(itemTitle);
//    archive.setDescription(description);
//    archive.setImageUrl(imageURL);
//    archive.setItemNumber(itemNumber);
//    archive.setGenre(genre);
//    archive.setPublishDate(publishDate);
//    archive.setIsReservable(isReservable);
//    archive.setIsCheckedOut(isCheckedOut);
//
//    archiveRepository.save(archive);
//
//    archive = null;
//
//    archive = archiveRepository.findArchiveByItemNumber(itemNumber);
//
//    assertNotNull(archive);
//
//    assertEquals(itemTitle, archive.getItemTitle());
//    assertEquals(description, archive.getDescription());
//    assertEquals(imageURL, archive.getImageUrl());
//    assertEquals(itemNumber, archive.getItemNumber());
//    assertEquals(genre, archive.getGenre());
//    assertEquals(publishDate, archive.getPublishDate());
//    assertEquals(isReservable, archive.getIsReservable());
//    assertEquals(isCheckedOut, archive.getIsCheckedOut());
//  }
  //------------------TESTING BOOK------------------------//
  @Test
  public void testPersistAndLoadBook() {
    // First example for object save/load
    Book book = new Book();
    // First example for attribute save/load
    String author = "Book Author";
    String publisher = "Book Publisher";
    String itemTitle = "Book Item Title";
    String description = "Book Description";
    String imageURL = "Book Image URL";
    String itemNumber = "Book Item Number";
    String genre = "Book Genre";
    Date publishDate = Date.valueOf("2021-10-15");
    boolean isReservable = true;
    boolean isCheckedOut = true;

    book.setAuthor(author);
    book.setPublisher(publisher);
    book.setItemTitle(itemTitle);
    book.setDescription(description);
    book.setImageUrl(imageURL);
    book.setItemNumber(itemNumber);
    book.setGenre(genre);
    book.setPublishDate(publishDate);
    book.setIsReservable(isReservable);
    book.setIsCheckedOut(isCheckedOut);

    bookRepository.save(book);

    book = null;

    book = bookRepository.findItemByItemNumber(itemNumber);

    assertNotNull(book);

    assertEquals(author, book.getAuthor());
    assertEquals(publisher, book.getPublisher());
    assertEquals(itemTitle, book.getItemTitle());
    assertEquals(description, book.getDescription());
    assertEquals(imageURL, book.getImageUrl());
    assertEquals(itemNumber, book.getItemNumber());
    assertEquals(genre, book.getGenre());
    assertEquals(publishDate, book.getPublishDate());
    assertEquals(isReservable, book.getIsReservable());
    assertEquals(isCheckedOut, book.getIsCheckedOut());
  }
//
//  //------------------TESTING HEAD LIBRARIAN------------------------//
//  @Test
//  public void testPersistAndLoadHeadLibrarian() {
//    // First example for object save/load
//    HeadLibrarian headLibrarian = new HeadLibrarian();
//    // First example for attribute save/load
//    String username = "Head Librarian Username";
//    String password = "Head Librarian Password";
//    String email = "Head Librarian Email";
//    String idNum = "Head Librarian IDNum";
//    String firstName = "Head Librarian First Name";
//    String lastName = "Head Librarian Last Name";
//
//    headLibrarian.setUsername(username);
//    headLibrarian.setPassword(password);
//    headLibrarian.setEmail(email);
//    headLibrarian.setIdNum(idNum);
//    headLibrarian.setFirstName(firstName);
//    headLibrarian.setLastName(lastName);
//
//    headLibrarianRepository.save(headLibrarian);
//
//    headLibrarian = null;
//
//    headLibrarian = headLibrarianRepository.findUserByIdNum(idNum);
//
//    assertNotNull(headLibrarian);
//
//    assertEquals(username, headLibrarian.getUsername());
//    assertEquals(password, headLibrarian.getPassword());
//    assertEquals(email, headLibrarian.getEmail());
//    assertEquals(idNum, headLibrarian.getIdNum());
//    assertEquals(firstName, headLibrarian.getFirstName());
//    assertEquals(lastName, headLibrarian.getLastName());
//  }
//
//  //------------------TESTING LIBRARIAN------------------------//
//  @Test
//  public void testPersistAndLoadLibrarian() {
//    // First example for object save/load
//    Librarian librarian = new Librarian();
//    // First example for attribute save/load
//    String username = "Librarian Username";
//    String password = "Librarian Password";
//    String email = "Librarian Email";
//    String idNum = "Librarian IDNum";
//    String firstName = "Librarian First Name";
//    String lastName = "Librarian Last Name";
//
//    librarian.setUsername(username);
//    librarian.setPassword(password);
//    librarian.setEmail(email);
//    librarian.setIdNum(idNum);
//    librarian.setFirstName(firstName);
//    librarian.setLastName(lastName);
//
//    librarianRepository.save(librarian);
//
//    librarian = null;
//
//    librarian = librarianRepository.findUserByIdNum(idNum);
//
//    assertNotNull(librarian);
//
//    assertEquals(username, librarian.getUsername());
//    assertEquals(password, librarian.getPassword());
//    assertEquals(email, librarian.getEmail());
//    assertEquals(idNum, librarian.getIdNum());
//    assertEquals(firstName, librarian.getFirstName());
//    assertEquals(lastName, librarian.getLastName());
//  }

  //------------------TESTING MOVIE------------------------//
  @Test
  public void testPersistAndLoadMovie() {
    // First example for object save/load
    Movie movie = new Movie();
    // First example for attribute save/load
    String productionCompany = "Movie Production Company";
    String movieCast = "Movie Cast";
    String director = "Movie Director";
    String producer = "Movie Producer";
    String itemTitle = "Movie Item Title";
    String description = "Movie Description";
    String imageURL = "Movie Image URL";
    String itemNumber = "Movie Item Number";
    String genre = "Movie Genre";
    Date publishDate = Date.valueOf("2021-10-15");
    boolean isReservable = true;
    boolean isCheckedOut = true;
    
    movie.setProductionCompany(productionCompany);
    movie.setMovieCast(movieCast);
    movie.setDirector(director);
    movie.setProducer(producer);
    movie.setItemTitle(itemTitle);
    movie.setDescription(description);
    movie.setImageUrl(imageURL);
    movie.setItemNumber(itemNumber);
    movie.setGenre(genre);
    movie.setPublishDate(publishDate);
    movie.setIsReservable(isReservable);
    movie.setIsCheckedOut(isCheckedOut);

    movieRepository.save(movie);

    movie = null;

    movie = movieRepository.findItemByItemNumber(itemNumber);

    assertNotNull(movie);

    assertEquals(productionCompany, movie.getProductionCompany());
    assertEquals(movieCast, movie.getMovieCast());
    assertEquals(director, movie.getDirector());
    assertEquals(producer, movie.getProducer());
    assertEquals(itemTitle, movie.getItemTitle());
    assertEquals(description, movie.getDescription());
    assertEquals(imageURL, movie.getImageUrl());
    assertEquals(itemNumber, movie.getItemNumber());
    assertEquals(genre, movie.getGenre());
    assertEquals(publishDate, movie.getPublishDate());
    assertEquals(isReservable, movie.getIsReservable());
    assertEquals(isCheckedOut, movie.getIsCheckedOut());
  }

//  //------------------TESTING MUSIC ALBUM------------------------//
//  @Test
//  public void testPersistAndLoadMusicAlbum() {
//    // First example for object save/load
//    MusicAlbum musicAlbum = new MusicAlbum();
//    // First example for attribute save/load
//    String artist = "Music Album Artist";
//    String recordingLabel = "Music Album Recording Label";
//    String itemTitle = "Music Album Item Title";
//    String description = "Music Album Description";
//    String imageURL = "Music Album Image URL";
//    String itemNumber = "Music Album Item Number";
//    String genre = "Music Album Genre";
//    Date publishDate = Date.valueOf("2021-10-15");
//    boolean isReservable = true;
//    boolean isCheckedOut = true;
//
//    musicAlbum.setArtist(artist);
//    musicAlbum.setRecordingLabel(recordingLabel);
//    musicAlbum.setItemTitle(itemTitle);
//    musicAlbum.setDescription(description);
//    musicAlbum.setImageUrl(imageURL);
//    musicAlbum.setItemNumber(itemNumber);
//    musicAlbum.setGenre(genre);
//    musicAlbum.setPublishDate(publishDate);
//    musicAlbum.setIsReservable(isReservable);
//    musicAlbum.setIsCheckedOut(isCheckedOut);
//
//    musicAlbumRepository.save(musicAlbum);
//
//    musicAlbum = null;
//
//    musicAlbum = musicAlbumRepository.findMusicAlbumByItemNumber(itemNumber);
//
//    assertNotNull(musicAlbum);
//
//    assertEquals(artist, musicAlbum.getArtist());
//    assertEquals(recordingLabel, musicAlbum.getRecordingLabel());
//    assertEquals(itemTitle, musicAlbum.getItemTitle());
//    assertEquals(description, musicAlbum.getDescription());
//    assertEquals(imageURL, musicAlbum.getImageUrl());
//    assertEquals(itemNumber, musicAlbum.getItemNumber());
//    assertEquals(genre, musicAlbum.getGenre());
//    assertEquals(publishDate, musicAlbum.getPublishDate());
//    assertEquals(isReservable, musicAlbum.getIsReservable());
//    assertEquals(isCheckedOut, musicAlbum.getIsCheckedOut());
//  }
//
//  //------------------TESTING PATRON------------------------//
//  @Test
//  public void testPersistAndLoadPatron() {
//    // First example for object save/load
//    Patron patron = new Patron();
//    // First example for attribute save/load
//    String address = "Patron Address";
//    boolean isVerified = true;
//    boolean isResident = true;
//    boolean isRegisteredOnline = true;
//    String username = "Patron Username";
//    String password = "Patron Password";
//    String email = "Patron Email";
//    String idNum = "Patron IDNum";
//    String firstName = "Patron First Name";
//    String lastName = "Patron Last Name";
//
//    patron.setAddress(address);
//    patron.setIsVerified(isVerified);
//    patron.setIsResident(isResident);
//    patron.setIsRegisteredOnline(isRegisteredOnline);
//    patron.setUsername(username);
//    patron.setPassword(password);
//    patron.setEmail(email);
//    patron.setIdNum(idNum);
//    patron.setFirstName(firstName);
//    patron.setLastName(lastName);
//
//    patronRepository.save(patron);
//
//    patron = null;
//
//    patron = patronRepository.findUserByIdNum(idNum);
//
//    assertNotNull(patron);
//
//    assertEquals(address, patron.getAddress());
//    assertEquals(isVerified, patron.getIsVerified());
//    assertEquals(isResident, patron.getIsResident());
//    assertEquals(isRegisteredOnline, patron.getIsRegisteredOnline());
//    assertEquals(username, patron.getUsername());
//    assertEquals(password, patron.getPassword());
//    assertEquals(email, patron.getEmail());
//    assertEquals(idNum, patron.getIdNum());
//    assertEquals(firstName, patron.getFirstName());
//    assertEquals(lastName, patron.getLastName());
//  }
//
//  //------------------TESTING PRINTED MEDIA------------------------//
//  @Test
//  public void testPersistAndLoadPrintedMedia() {
//    // First example for object save/load
//    PrintedMedia printedMedia = new PrintedMedia();
//    // First example for attribute save/load
//    String issueNumber = "Printed Media Issue Number";
//    String itemTitle = "Printed Media Item Title";
//    String description = "Printed Media Description";
//    String imageURL = "Printed Media Image URL";
//    String itemNumber = "Printed Media Item Number";
//    String genre = "Printed Media Genre";
//    Date publishDate = Date.valueOf("2021-10-15");
//    boolean isReservable = true;
//    boolean isCheckedOut = true;
//
//    printedMedia.setIssueNumber(issueNumber);
//    printedMedia.setItemTitle(itemTitle);
//    printedMedia.setDescription(description);
//    printedMedia.setImageUrl(imageURL);
//    printedMedia.setItemNumber(itemNumber);
//    printedMedia.setGenre(genre);
//    printedMedia.setPublishDate(publishDate);
//    printedMedia.setIsReservable(isReservable);
//    printedMedia.setIsCheckedOut(isCheckedOut);
//
//    printedMediaRepository.save(printedMedia);
//
//    printedMedia = null;
//
//    printedMedia = printedMediaRepository.findPrintedMediaByItemNumber(itemNumber);
//
//    assertNotNull(printedMedia);
//
//    assertEquals(issueNumber, printedMedia.getIssueNumber());
//    assertEquals(itemTitle, printedMedia.getItemTitle());
//    assertEquals(description, printedMedia.getDescription());
//    assertEquals(imageURL, printedMedia.getImageUrl());
//    assertEquals(itemNumber, printedMedia.getItemNumber());
//    assertEquals(genre, printedMedia.getGenre());
//    assertEquals(publishDate, printedMedia.getPublishDate());
//    assertEquals(isReservable, printedMedia.getIsReservable());
//    assertEquals(isCheckedOut, printedMedia.getIsCheckedOut());
//  }
//
//  //------------------TESTING ROOM------------------------//
//  @Test
//  public void testPersistAndLoadRoom() {
//    // First example for object save/load
//    Room room = new Room();
//    // First example for attribute save/load
//    String roomNum = "Room Number";
//    int capacity = 69;
//
//    room.setRoomNum(roomNum);
//    room.setCapacity(capacity);
//
//    roomRepository.save(room);
//
//    room = null;
//
//    room = roomRepository.findRoomByRoomNum(roomNum);
//
//    assertNotNull(room);
//
//    assertEquals(roomNum, room.getRoomNum());
//    assertEquals(capacity, room.getCapacity());
//  }

}
