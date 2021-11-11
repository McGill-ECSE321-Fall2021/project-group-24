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
public class TestArchiveService {

  @Mock
  private ItemRepository archiveDao;

  @Mock
  private LibrarianRepository librarianDao;

  @Mock
  private PatronRepository patronDao;

  @Mock
  private HeadLibrarianRepository headLibrarianDao;

  @InjectMocks
  private ItemService archiveService;

  
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
      .when(librarianDao.findUserByIdNum("librarian"))
      .thenAnswer((InvocationOnMock invocation) -> {
        Librarian librarian = new Librarian();
        librarian.setIdNum("librarian");
        librarian.setIsLoggedIn(true);
        System.out.print("Librarian logged in");
        return librarian;
        
      });
    lenient()
    .when(headLibrarianDao.findUserByIdNum("admin"))
    .thenAnswer((InvocationOnMock invocation) -> {
      HeadLibrarian headLibrarian = new HeadLibrarian();
      headLibrarian.setIdNum("admin");
      headLibrarian.setIsLoggedIn(true);
      System.out.print("KKOOOL?");
      return headLibrarian;
      
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
      .when(archiveDao.findItemByItemNumber(anyString()))
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
      .when(archiveDao.findAll())
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
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient()
      .when(archiveDao.save(any(Archive.class)))
      .thenAnswer(returnParameterAsAnswer);
    lenient()
      .when(librarianDao.save(any(Librarian.class)))
      .thenAnswer(returnParameterAsAnswer);
    lenient()
      .when(headLibrarianDao.save(any(HeadLibrarian.class)))
      .thenAnswer(returnParameterAsAnswer);
  }

  @Test
  //Create an archive
  public void testCreateArchive() {
    Archive archive = null;
    try {
      archive =
        archiveService.createArchive(
          "librarian",
          correctString,
          correctString,
          correctString,
          correctString,
          correctDate,
          true
        );
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(archive);
    assertEquals(correctString, archive.getItemTitle());
    assertEquals(correctString, archive.getDescription());
    assertEquals(correctString, archive.getImageUrl());
    assertEquals(correctString, archive.getGenre());
    assertEquals(correctDate, archive.getPublishDate());
    assertEquals(true, archive.getIsReservable());
    System.out.println(archive.getItemNumber());
    System.out.println("Archive created!");
  }

  @Test
  //Create an archive as a patron
  public void testCreateArchiveAsPatron() {
    String error = null;
    Archive archive = null;
    try {
      archive =
        archiveService.createArchive(
          "patron",
          correctString,
          correctString,
          correctString,
          correctString,
          correctDate,
          true
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("You do not have permission to create an item reservation", error);
    System.out.println(error);
    
  }

  @Test
  //Create an archive with no title
  public void testCreateArchiveWithNoItemTitle() {
    String error = null;
    Archive archive = null;
    try {
      archive =
        archiveService.createArchive(
          "librarian",
          emptyString,
          correctString,
          correctString,
          correctString,
          correctDate,
          true
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Item must have a title", error);
    System.out.println(error);
  }

  @Test
  //Create an archive with no description
  public void testCreateArchiveWithNoItemDescription() {
    String error = null;
    Archive archive = null;
    try {
      archive =
        archiveService.createArchive(
          "librarian",
          correctString,
          emptyString,
          correctString,
          correctString,
          correctDate,
          true
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNotNull(archive);
    assertEquals(correctString, archive.getItemTitle());
    assertEquals(emptyString, archive.getDescription());
    assertEquals(correctString, archive.getImageUrl());
    assertEquals(correctString, archive.getGenre());
    assertEquals(correctDate, archive.getPublishDate());
    assertEquals(true, archive.getIsReservable());
    System.out.println(archive.getItemNumber());
    System.out.println("Archive created!");
  }

  @Test
  //Get all archives
  public void testGetAllArchives() {
    ArrayList<Item> archives = null;

    try {
      archives = new ArrayList<Item>(archiveService.getAllArchives());;
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(archives);
    assertEquals(2, archives.size());
    assertEquals(correctString, archives.get(0).getItemTitle());
    assertEquals(correctString, archives.get(0).getDescription());
    assertEquals(correctString, archives.get(0).getImageUrl());
    assertEquals(correctString, archives.get(0).getGenre());
    assertEquals(correctDate, archives.get(0).getPublishDate());
    assertEquals(true, archives.get(0).getIsReservable());
    assertEquals(correctString, archives.get(1).getItemTitle());
    assertEquals(correctString, archives.get(1).getDescription());
    assertEquals(correctString, archives.get(1).getImageUrl());
    assertEquals(correctString, archives.get(1).getGenre());
    assertEquals(correctDate, archives.get(1).getPublishDate());
    assertEquals(true, archives.get(1).getIsReservable());

    for (Item archive : archives) {
      System.out.println(archive.getItemTitle());
    }
  }
}
