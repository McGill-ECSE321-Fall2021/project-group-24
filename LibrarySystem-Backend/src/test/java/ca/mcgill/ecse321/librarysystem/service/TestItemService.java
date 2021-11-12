package ca.mcgill.ecse321.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hibernate.annotations.SourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class TestItemService {

  @Mock
  private ItemRepository itemDao;

  @Mock
  private PatronRepository patronDao;


  @InjectMocks
  private ItemService itemService;

  
  private static final String correctString = "test";
  private static final String wrongString = "wrong";
  private static final Date correctDate = Date.valueOf("2021-11-10");
  private static final Date wrongDate = Date.valueOf("2019-01-10");
  private static final String emptyString = "";
  private static final int testID = 1;
  private static final int wrongID = 0;

  @BeforeEach
  public void setMockOutput() {
	  lenient()
	    .when(itemDao.findItemsByType(Item.Type.Book))
	    .thenAnswer((InvocationOnMock invocation) -> {
	      Book book1 = new Book();
	      book1.setItemTitle("book");
	      book1.setDescription(correctString);
	      book1.setImageUrl(correctString);
	      book1.setItemNumber(correctString);
	      book1.setGenre(correctString);
	      book1.setPublishDate(correctDate);
	      book1.setIsReservable(true);
	      book1.setType(Item.Type.Book);
	      book1.setAuthor(correctString);
	      book1.setPublisher(correctString);

	      Book book2 = new Book();
	      book2.setItemTitle("book" + "2");
	      book2.setDescription(correctString + "2");
	      book2.setImageUrl(correctString + "2");
	      book2.setItemNumber(correctString + "2");
	      book2.setGenre(correctString + "2");
	      book2.setPublishDate(correctDate);
	      book2.setIsReservable(true);
	      book2.setType(Item.Type.Book);
	      book2.setAuthor(correctString + "2");
	      book2.setPublisher(correctString + "2");
	      List<Item> list = new ArrayList<Item>();
	      list.add(book1);
	      list.add(book2);

	      return list;
	    });
	    
	    lenient()
	    .when(itemDao.findItemsByType(Item.Type.Archive))
	    .thenAnswer((InvocationOnMock invocation) -> {
	      Archive archive1 = new Archive();
	      archive1.setItemTitle("archive");
	      archive1.setDescription(correctString);
	      archive1.setImageUrl(correctString);
	      archive1.setItemNumber(correctString);
	      archive1.setGenre(correctString);
	      archive1.setPublishDate(correctDate);
	      archive1.setIsReservable(true);
	      archive1.setType(Item.Type.Archive);
	   

	      Archive archive2 = new Archive();
	      archive2.setItemTitle("archive" + "2");
	      archive2.setDescription(correctString + "2");
	      archive2.setImageUrl(correctString + "2");
	      archive2.setItemNumber(correctString + "2");
	      archive2.setGenre(correctString + "2");
	      archive2.setPublishDate(correctDate);
	      archive2.setIsReservable(true);
	      archive2.setType(Item.Type.Archive);

	      List<Item> list = new ArrayList<Item>();
	      list.add(archive1);
	      list.add(archive2);

	      return list;
	    });
	    
	    lenient()
	    .when(itemDao.findItemsByType(Item.Type.Movie))
	    .thenAnswer((InvocationOnMock invocation) -> {
	      Movie movie1 = new Movie();
	      movie1.setItemTitle("movie");
	      movie1.setDescription(correctString);
	      movie1.setImageUrl(correctString);
	      movie1.setItemNumber(correctString);
	      movie1.setGenre(correctString);
	      movie1.setPublishDate(correctDate);
	      movie1.setIsReservable(true);
	      movie1.setType(Item.Type.Movie);
	      movie1.setDirector("bob");
	   

	      Movie movie2 = new Movie();
	      movie2.setItemTitle("movie" + "2");
	      movie2.setDescription(correctString + "2");
	      movie2.setImageUrl(correctString + "2");
	      movie2.setItemNumber(correctString + "2");
	      movie2.setGenre(correctString + "2");
	      movie2.setPublishDate(correctDate);
	      movie2.setIsReservable(true);
	      movie2.setType(Item.Type.Movie);

	      List<Item> list = new ArrayList<Item>();
	      list.add(movie1);
	      list.add(movie2);

	      return list;
	    });
	    
	    lenient()
	    .when(itemDao.findItemsByType(Item.Type.PrintedMedia))
	    .thenAnswer((InvocationOnMock invocation) -> {
	      PrintedMedia pm1 = new PrintedMedia();
	      pm1.setItemTitle("printedMedia");
	      pm1.setDescription(correctString);
	      pm1.setImageUrl(correctString);
	      pm1.setItemNumber(correctString);
	      pm1.setGenre(correctString);
	      pm1.setPublishDate(correctDate);
	      pm1.setIsReservable(true);
	      pm1.setType(Item.Type.PrintedMedia);
	      pm1.setIssueNumber("bob");
	   

	      PrintedMedia pb2 = new PrintedMedia();
	      pb2.setItemTitle("printedMedia" + "2");
	      pb2.setDescription(correctString + "2");
	      pb2.setImageUrl(correctString + "2");
	      pb2.setItemNumber(correctString + "2");
	      pb2.setGenre(correctString + "2");
	      pb2.setPublishDate(correctDate);
	      pb2.setIsReservable(true);
	      pb2.setType(Item.Type.PrintedMedia);

	      List<Item> list = new ArrayList<Item>();
	      list.add(pm1);
	      list.add(pb2);

	      return list;
	    });
	    
	    lenient()
	    .when(itemDao.findItemsByType(Item.Type.MusicAlbum))
	    .thenAnswer((InvocationOnMock invocation) -> {
	      MusicAlbum pm1 = new MusicAlbum();
	      pm1.setItemTitle("musicAlbum");
	      pm1.setDescription(correctString);
	      pm1.setImageUrl(correctString);
	      pm1.setItemNumber(correctString);
	      pm1.setGenre(correctString);
	      pm1.setPublishDate(correctDate);
	      pm1.setIsReservable(true);
	      pm1.setType(Item.Type.MusicAlbum);
	      pm1.setArtist("bob");
	   

	      MusicAlbum pb2 = new MusicAlbum();
	      pb2.setItemTitle("musicAlbum" + "2");
	      pb2.setDescription(correctString + "2");
	      pb2.setImageUrl(correctString + "2");
	      pb2.setItemNumber(correctString + "2");
	      pb2.setGenre(correctString + "2");
	      pb2.setPublishDate(correctDate);
	      pb2.setIsReservable(true);
	      pb2.setType(Item.Type.MusicAlbum);

	      List<Item> list = new ArrayList<Item>();
	      list.add(pm1);
	      list.add(pb2);

	      return list;
	    });
    lenient()
      .when(patronDao.findUserByIdNum("patron"))
      .thenAnswer((InvocationOnMock invocation) -> {
        Patron patron = new Patron();
        patron.setIdNum("patron");
        patron.setIsLoggedIn(true);
        System.out.print("Patron logged in");
        return patron;
        
      });
    lenient()
      .when(itemDao.findItemByItemNumber(anyString()))
      .thenAnswer((InvocationOnMock invocation) -> {
        if (invocation.getArgument(0).equals(correctString)) {
          Archive archive = new Archive();
          archive.setItemTitle(correctString);
          archive.setDescription(correctString);
          archive.setImageUrl(correctString);
          archive.setItemNumber(correctString);
          archive.setGenre(correctString);
          archive.setPublishDate(correctDate);
          archive.setIsReservable(true);
          archive.setType(Item.Type.Archive);
          return archive;
        } else {
          return null;
        }
      });
    lenient()
      .when(itemDao.findAll())
      .thenAnswer((InvocationOnMock invocation) -> {
        Archive archive1 = new Archive();
        archive1.setItemTitle(correctString);
        archive1.setDescription(correctString);
        archive1.setImageUrl(correctString);
        archive1.setItemNumber(correctString);
        archive1.setGenre(correctString);
        archive1.setPublishDate(correctDate);
        archive1.setIsReservable(true);
        archive1.setType(Item.Type.Archive);

        Archive archive2 = new Archive();
        archive2.setItemTitle(correctString + "2");
        archive2.setDescription(correctString + "2");
        archive2.setImageUrl(correctString + "2");
        archive2.setItemNumber(correctString + "2");
        archive2.setGenre(correctString + "2");
        archive2.setPublishDate(correctDate);
        archive2.setIsReservable(false);
        archive2.setType(Item.Type.Archive);
        List<Item> list = new ArrayList<Item>();
        list.add(archive1);
        list.add(archive2);

        return list;
      });
    
    lenient()
    .when(itemDao.findItemsByType(Item.Type.Book))
    .thenAnswer((InvocationOnMock invocation) -> {
      Book book1 = new Book();
      book1.setItemTitle("book");
      book1.setDescription(correctString);
      book1.setImageUrl(correctString);
      book1.setItemNumber(correctString);
      book1.setGenre(correctString);
      book1.setPublishDate(correctDate);
      book1.setIsReservable(true);
      book1.setType(Item.Type.Book);
      book1.setAuthor(correctString);
      book1.setPublisher(correctString);

      Book book2 = new Book();
      book2.setItemTitle("book" + "2");
      book2.setDescription(correctString + "2");
      book2.setImageUrl(correctString + "2");
      book2.setItemNumber(correctString + "2");
      book2.setGenre(correctString + "2");
      book2.setPublishDate(correctDate);
      book2.setIsReservable(true);
      book2.setType(Item.Type.Book);
      book2.setAuthor(correctString + "2");
      book2.setPublisher(correctString + "2");
      List<Item> list = new ArrayList<Item>();
      list.add(book1);
      list.add(book2);

      return list;
    });
    
    lenient()
    .when(itemDao.findItemsByType(Item.Type.Archive))
    .thenAnswer((InvocationOnMock invocation) -> {
      Archive archive1 = new Archive();
      archive1.setItemTitle("archive");
      archive1.setDescription(correctString);
      archive1.setImageUrl(correctString);
      archive1.setItemNumber(correctString);
      archive1.setGenre(correctString);
      archive1.setPublishDate(correctDate);
      archive1.setIsReservable(true);
      archive1.setType(Item.Type.Archive);
   

      Archive archive2 = new Archive();
      archive2.setItemTitle("archive" + "2");
      archive2.setDescription(correctString + "2");
      archive2.setImageUrl(correctString + "2");
      archive2.setItemNumber(correctString + "2");
      archive2.setGenre(correctString + "2");
      archive2.setPublishDate(correctDate);
      archive2.setIsReservable(true);
      archive2.setType(Item.Type.Archive);

      List<Item> list = new ArrayList<Item>();
      list.add(archive1);
      list.add(archive2);

      return list;
    });
    
    lenient()
    .when(itemDao.findItemsByType(Item.Type.Movie))
    .thenAnswer((InvocationOnMock invocation) -> {
      Movie movie1 = new Movie();
      movie1.setItemTitle("movie");
      movie1.setDescription(correctString);
      movie1.setImageUrl(correctString);
      movie1.setItemNumber(correctString);
      movie1.setGenre(correctString);
      movie1.setPublishDate(correctDate);
      movie1.setIsReservable(true);
      movie1.setType(Item.Type.Movie);
      movie1.setDirector("bob");
   

      Movie movie2 = new Movie();
      movie2.setItemTitle("movie" + "2");
      movie2.setDescription(correctString + "2");
      movie2.setImageUrl(correctString + "2");
      movie2.setItemNumber(correctString + "2");
      movie2.setGenre(correctString + "2");
      movie2.setPublishDate(correctDate);
      movie2.setIsReservable(true);
      movie2.setType(Item.Type.Movie);

      List<Item> list = new ArrayList<Item>();
      list.add(movie1);
      list.add(movie2);

      return list;
    });
    
    lenient()
    .when(itemDao.findItemsByType(Item.Type.PrintedMedia))
    .thenAnswer((InvocationOnMock invocation) -> {
      PrintedMedia pm1 = new PrintedMedia();
      pm1.setItemTitle("printedMedia");
      pm1.setDescription(correctString);
      pm1.setImageUrl(correctString);
      pm1.setItemNumber(correctString);
      pm1.setGenre(correctString);
      pm1.setPublishDate(correctDate);
      pm1.setIsReservable(true);
      pm1.setType(Item.Type.PrintedMedia);
      pm1.setIssueNumber("bob");
   

      PrintedMedia pb2 = new PrintedMedia();
      pb2.setItemTitle("printedMedia" + "2");
      pb2.setDescription(correctString + "2");
      pb2.setImageUrl(correctString + "2");
      pb2.setItemNumber(correctString + "2");
      pb2.setGenre(correctString + "2");
      pb2.setPublishDate(correctDate);
      pb2.setIsReservable(true);
      pb2.setType(Item.Type.PrintedMedia);

      List<Item> list = new ArrayList<Item>();
      list.add(pm1);
      list.add(pb2);

      return list;
    });
    
    lenient()
    .when(itemDao.findItemsByType(Item.Type.MusicAlbum))
    .thenAnswer((InvocationOnMock invocation) -> {
      MusicAlbum pm1 = new MusicAlbum();
      pm1.setItemTitle("musicAlbum");
      pm1.setDescription(correctString);
      pm1.setImageUrl(correctString);
      pm1.setItemNumber(correctString);
      pm1.setGenre(correctString);
      pm1.setPublishDate(correctDate);
      pm1.setIsReservable(true);
      pm1.setType(Item.Type.MusicAlbum);
      pm1.setArtist("bob");
   

      MusicAlbum pb2 = new MusicAlbum();
      pb2.setItemTitle("musicAlbum" + "2");
      pb2.setDescription(correctString + "2");
      pb2.setImageUrl(correctString + "2");
      pb2.setItemNumber(correctString + "2");
      pb2.setGenre(correctString + "2");
      pb2.setPublishDate(correctDate);
      pb2.setIsReservable(true);
      pb2.setType(Item.Type.MusicAlbum);

      List<Item> list = new ArrayList<Item>();
      list.add(pm1);
      list.add(pb2);

      return list;
    });
    
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient()
      .when(itemDao.save(any(Archive.class)))
      .thenAnswer(returnParameterAsAnswer);
  }

  @Test
  //Find an item with a valid item number 
  public void testFindValidItem() {
    Item item = null;
    try {
      item = itemDao.findItemByItemNumber(correctString);
    } 
    catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(item);
    assertEquals(correctString, item.getItemTitle());
    assertEquals(correctString, item.getDescription());
    assertEquals(correctString, item.getImageUrl());
    assertEquals(correctString, item.getGenre());
    assertEquals(correctDate, item.getPublishDate());
    assertEquals(true, item.getIsReservable());
    System.out.println(item.getItemNumber());
    System.out.println("Item found!");
  }

  @Test
  //Find an item with an invalid item number 
  public void testFindInvalidItem() {
    String error = null;
    Item item = null;
    try {
      item = itemDao.findItemByItemNumber(wrongString);
      if (item == null) {
				throw new IllegalArgumentException("The item does not exist");
      }
    } 
    catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(item);
    assertEquals("The item does not exist", error);
  }

 
  @Test
  //Get all items
  public void testGetAllItems() {
    ArrayList<Item> items = null;

    try {
      items = new ArrayList<Item>(itemService.getAll());;
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(items);
    assertEquals(2, items.size());
    assertEquals(correctString, items.get(0).getItemTitle());
    assertEquals(correctString, items.get(0).getDescription());
    assertEquals(correctString, items.get(0).getImageUrl());
    assertEquals(correctString, items.get(0).getGenre());
    assertEquals(correctDate, items.get(0).getPublishDate());
    assertEquals(true, items.get(0).getIsReservable());
    assertEquals(correctString + "2", items.get(1).getItemTitle());
    assertEquals(correctString + "2", items.get(1).getDescription());
    assertEquals(correctString + "2", items.get(1).getImageUrl());
    assertEquals(correctString + "2", items.get(1).getGenre());
    assertEquals(correctDate, items.get(1).getPublishDate());
    assertEquals(false, items.get(1).getIsReservable());

    for (Item item : items) {
      System.out.println(item.getItemTitle());
    }
  }
  
  @Test
  //Get all items
  public void testGetAllBooks() {
    ArrayList<Item> items = null;

    try {
      items = new ArrayList<Item>(itemService.getAllBooks());;
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(items);
    assertEquals(2, items.size());
    assertEquals("book", items.get(0).getItemTitle());
    assertEquals(correctString, items.get(0).getDescription());
    assertEquals(correctString, items.get(0).getImageUrl());
    assertEquals(correctString, items.get(0).getGenre());
    assertEquals(correctDate, items.get(0).getPublishDate());
    assertEquals(true, items.get(0).getIsReservable());
    assertEquals("book" + "2", items.get(1).getItemTitle());
    assertEquals(correctString + "2", items.get(1).getDescription());
    assertEquals(correctString + "2", items.get(1).getImageUrl());
    assertEquals(correctString + "2", items.get(1).getGenre());
    assertEquals(correctDate, items.get(1).getPublishDate());
    assertEquals(true, items.get(1).getIsReservable());

    for (Item item : items) {
      System.out.println(item.getItemTitle());
    }
  }
  
  @Test
  //Get all items
  public void testGetAllArchives() {
    ArrayList<Item> items = null;

    try {
      items = new ArrayList<Item>(itemService.getAllArchives());;
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(items);
    assertEquals(2, items.size());
    assertEquals("archive", items.get(0).getItemTitle());
    assertEquals(correctString, items.get(0).getDescription());
    assertEquals(correctString, items.get(0).getImageUrl());
    assertEquals(correctString, items.get(0).getGenre());
    assertEquals(correctDate, items.get(0).getPublishDate());
    assertEquals(true, items.get(0).getIsReservable());
    assertEquals("archive" + "2", items.get(1).getItemTitle());
    assertEquals(correctString + "2", items.get(1).getDescription());
    assertEquals(correctString + "2", items.get(1).getImageUrl());
    assertEquals(correctString + "2", items.get(1).getGenre());
    assertEquals(correctDate, items.get(1).getPublishDate());
    assertEquals(true, items.get(1).getIsReservable());

    for (Item item : items) {
      System.out.println(item.getItemTitle());
    }
  }
  
  @Test
  //Get all movies
  public void testGetAllMovies() {
    ArrayList<Item> items = null;

    try {
      items = new ArrayList<Item>(itemService.getAllMovies());;
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(items);
    assertEquals(2, items.size());
    assertEquals("movie", items.get(0).getItemTitle());
    assertEquals(correctString, items.get(0).getDescription());
    assertEquals(correctString, items.get(0).getImageUrl());
    assertEquals(correctString, items.get(0).getGenre());
    assertEquals(correctDate, items.get(0).getPublishDate());
    assertEquals("bob", ((Movie)items.get(0)).getDirector());
    assertEquals(true, items.get(0).getIsReservable());
    assertEquals("movie" + "2", items.get(1).getItemTitle());
    assertEquals(correctString + "2", items.get(1).getDescription());
    assertEquals(correctString + "2", items.get(1).getImageUrl());
    assertEquals(correctString + "2", items.get(1).getGenre());
    assertEquals(correctDate, items.get(1).getPublishDate());
    assertEquals(true, items.get(1).getIsReservable());

    for (Item item : items) {
      System.out.println(item.getItemTitle());
    }
  }
  @Test
  //Get all items
  public void testGetAllPrintedMedias() {
    ArrayList<Item> items = null;

    try {
      items = new ArrayList<Item>(itemService.getAllPrintedMedias());;
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(items);
    assertEquals(2, items.size());
    assertEquals("printedMedia", items.get(0).getItemTitle());
    assertEquals(correctString, items.get(0).getDescription());
    assertEquals(correctString, items.get(0).getImageUrl());
    assertEquals(correctString, items.get(0).getGenre());
    assertEquals(correctDate, items.get(0).getPublishDate());
    assertEquals(Item.Type.PrintedMedia, items.get(0).getType());
    assertEquals("bob", ((PrintedMedia)items.get(0)).getIssueNumber());
    assertEquals(true, items.get(0).getIsReservable());
    assertEquals("printedMedia" + "2", items.get(1).getItemTitle());
    assertEquals(correctString + "2", items.get(1).getDescription());
    assertEquals(correctString + "2", items.get(1).getImageUrl());
    assertEquals(correctString + "2", items.get(1).getGenre());
    assertEquals(correctDate, items.get(1).getPublishDate());
    assertEquals(true, items.get(1).getIsReservable());

    for (Item item : items) {
      System.out.println(item.getItemTitle());
    }
  }
  @Test
  //Get all items
  public void testGetAllMusicAlbums() {
    ArrayList<Item> items = null;

    try {
      items = new ArrayList<Item>(itemService.getAllMusicAlbums());;
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(items);
    assertEquals(2, items.size());
    assertEquals("musicAlbum", items.get(0).getItemTitle());
    assertEquals(correctString, items.get(0).getDescription());
    assertEquals(correctString, items.get(0).getImageUrl());
    assertEquals(correctString, items.get(0).getGenre());
    assertEquals(correctDate, items.get(0).getPublishDate());
    assertEquals(Item.Type.MusicAlbum, items.get(0).getType());
    assertEquals("bob", ((MusicAlbum)items.get(0)).getArtist());
    assertEquals(true, items.get(0).getIsReservable());
    assertEquals("musicAlbum" + "2", items.get(1).getItemTitle());
    assertEquals(correctString + "2", items.get(1).getDescription());
    assertEquals(correctString + "2", items.get(1).getImageUrl());
    assertEquals(correctString + "2", items.get(1).getGenre());
    assertEquals(correctDate, items.get(1).getPublishDate());
    assertEquals(true, items.get(1).getIsReservable());

    for (Item item : items) {
      System.out.println(item.getItemTitle());
    }
  }
  
}
