package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestItemPersistence {

  @Autowired
  private ItemRepository itemRepository;

  @BeforeEach
  public void clearDatabase() {
    // First, we clear registrations to avoid exceptions due to inconsistencies
    //	      librarySystemRepository.deleteAll();
    // Then we can clear the other tables

    itemRepository.deleteAll();
  }

  //------------------TESTING ARCHIVE------------------------//
  @Test
  public void testPersistAndLoadArchive() {
    // First example for object save/load
    Archive archive = new Archive();
    // First example for attribute save/load
    String itemTitle = "Archive Item Title";
    String description = "Archive Description";
    String imageURL = "Archive Image URL";
    String itemNumber = "Archive Item Number";
    String genre = "Archive Genre";
    Date publishDate = Date.valueOf("2021-10-15");
    boolean isReservable = true;
    String currentReservationId = null;

    archive.setItemTitle(itemTitle);
    archive.setDescription(description);
    archive.setImageUrl(imageURL);
    archive.setItemNumber(itemNumber);
    archive.setGenre(genre);
    archive.setPublishDate(publishDate);
    archive.setIsReservable(isReservable);
    archive.setCurrentReservationId(currentReservationId);

    itemRepository.save(archive);

    archive = null;

    archive = (Archive) itemRepository.findItemByItemNumber(itemNumber);
    assertNotNull(archive);

    assertEquals(itemTitle, archive.getItemTitle());
    assertEquals(description, archive.getDescription());
    assertEquals(imageURL, archive.getImageUrl());
    assertEquals(itemNumber, archive.getItemNumber());
    assertEquals(genre, archive.getGenre());
    assertEquals(publishDate, archive.getPublishDate());
    assertEquals(isReservable, archive.getIsReservable());
    assertEquals(currentReservationId, archive.getCurrentReservationId());
  }

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

    book.setAuthor(author);
    book.setPublisher(publisher);
    book.setItemTitle(itemTitle);
    book.setDescription(description);
    book.setImageUrl(imageURL);
    book.setItemNumber(itemNumber);
    book.setGenre(genre);
    book.setPublishDate(publishDate);
    book.setIsReservable(isReservable);
    book.setCurrentReservationId(null);

    itemRepository.save(book);

    book = null;

    book = (Book) itemRepository.findItemByItemNumber(itemNumber);

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
    assertEquals(null, book.getCurrentReservationId());
  }

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
    movie.setCurrentReservationId(null);

    itemRepository.save(movie);
  }

  //------------------TESTING MUSIC ALBUM------------------------//
  @Test
  public void testPersistAndLoadMusicAlbum() {
    // First example for object save/load
    MusicAlbum musicAlbum = new MusicAlbum();
    // First example for attribute save/load
    String artist = "Music Album Artist";
    String recordingLabel = "Music Album Recording Label";
    String itemTitle = "Music Album Item Title";
    String description = "Music Album Description";
    String imageURL = "Music Album Image URL";
    String itemNumber = "Music Album Item Number";
    String genre = "Music Album Genre";
    Date publishDate = Date.valueOf("2021-10-15");
    boolean isReservable = true;
    String currentReservationId = null;

    musicAlbum.setArtist(artist);
    musicAlbum.setRecordingLabel(recordingLabel);
    musicAlbum.setItemTitle(itemTitle);
    musicAlbum.setDescription(description);
    musicAlbum.setImageUrl(imageURL);
    musicAlbum.setItemNumber(itemNumber);
    musicAlbum.setGenre(genre);
    musicAlbum.setPublishDate(publishDate);
    musicAlbum.setIsReservable(isReservable);
    musicAlbum.setCurrentReservationId(currentReservationId);

    itemRepository.save(musicAlbum);

    musicAlbum = null;

    musicAlbum = (MusicAlbum) itemRepository.findItemByItemNumber(itemNumber);

    assertNotNull(musicAlbum);

    assertEquals(artist, musicAlbum.getArtist());
    assertEquals(recordingLabel, musicAlbum.getRecordingLabel());
    assertEquals(itemTitle, musicAlbum.getItemTitle());
    assertEquals(description, musicAlbum.getDescription());
    assertEquals(imageURL, musicAlbum.getImageUrl());
    assertEquals(itemNumber, musicAlbum.getItemNumber());
    assertEquals(genre, musicAlbum.getGenre());
    assertEquals(publishDate, musicAlbum.getPublishDate());
    assertEquals(isReservable, musicAlbum.getIsReservable());
    assertEquals(currentReservationId, musicAlbum.getCurrentReservationId());
  }

  //------------------TESTING PRINTED MEDIA------------------------//
  @Test
  public void testPersistAndLoadPrintedMedia() {
    // First example for object save/load
    PrintedMedia printedMedia = new PrintedMedia();
    // First example for attribute save/load
    String issueNumber = "Printed Media Issue Number";
    String itemTitle = "Printed Media Item Title";
    String description = "Printed Media Description";
    String imageURL = "Printed Media Image URL";
    String itemNumber = "Printed Media Item Number";
    String genre = "Printed Media Genre";
    Date publishDate = Date.valueOf("2021-10-15");
    boolean isReservable = true;

    printedMedia.setIssueNumber(issueNumber);
    printedMedia.setItemTitle(itemTitle);
    printedMedia.setDescription(description);
    printedMedia.setImageUrl(imageURL);
    printedMedia.setItemNumber(itemNumber);
    printedMedia.setGenre(genre);
    printedMedia.setPublishDate(publishDate);
    printedMedia.setIsReservable(isReservable);
    printedMedia.setCurrentReservationId(null);

    itemRepository.save(printedMedia);

    printedMedia = null;

    printedMedia =
      (PrintedMedia) itemRepository.findItemByItemNumber(itemNumber);

    assertNotNull(printedMedia);

    assertEquals(issueNumber, printedMedia.getIssueNumber());
    assertEquals(itemTitle, printedMedia.getItemTitle());
    assertEquals(description, printedMedia.getDescription());
    assertEquals(imageURL, printedMedia.getImageUrl());
    assertEquals(itemNumber, printedMedia.getItemNumber());
    assertEquals(genre, printedMedia.getGenre());
    assertEquals(publishDate, printedMedia.getPublishDate());
    assertEquals(isReservable, printedMedia.getIsReservable());
    assertEquals(null, printedMedia.getCurrentReservationId());
  }
}
