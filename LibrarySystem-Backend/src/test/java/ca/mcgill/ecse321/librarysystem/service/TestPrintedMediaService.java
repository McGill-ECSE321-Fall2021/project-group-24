package ca.mcgill.ecse321.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class TestPrintedMediaService {

  @Mock
  private ItemRepository printedMediaDao;

  @Mock
  private LibrarianRepository librarianDao;

  @Mock
  private PatronRepository patronDao;

  @Mock
  private HeadLibrarianRepository headLibrarianDao;

  @InjectMocks
  private ItemService printedMediaService;

  
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
      .when(printedMediaDao.findItemByItemNumber(anyString()))
      .thenAnswer((InvocationOnMock invocation) -> {
        if (invocation.getArgument(0).equals(correctString)) {
          PrintedMedia printedMedia = new PrintedMedia();
          printedMedia.setItemTitle(correctString);
          printedMedia.setDescription(correctString);
          printedMedia.setImageUrl(correctString);
          printedMedia.setItemNumber(correctString);
          printedMedia.setGenre(correctString);
          printedMedia.setPublishDate(correctDate);
          printedMedia.setIsReservable(true);
          printedMedia.setIssueNumber(correctString);
          printedMedia.setType(Item.Type.PrintedMedia);
          return printedMedia;
        } else {
          return null;
        }
      });
    lenient()
      .when(printedMediaDao.findAll())
      .thenAnswer((InvocationOnMock invocation) -> {
        PrintedMedia printedMedia1 = new PrintedMedia();
        printedMedia1.setItemTitle(correctString);
        printedMedia1.setDescription(correctString);
        printedMedia1.setImageUrl(correctString);
        printedMedia1.setItemNumber(correctString);
        printedMedia1.setGenre(correctString);
        printedMedia1.setPublishDate(correctDate);
        printedMedia1.setIsReservable(true);
        printedMedia1.setIssueNumber(correctString);
        printedMedia1.setType(Item.Type.PrintedMedia);

        PrintedMedia printedMedia2 = new PrintedMedia();
        printedMedia2.setItemTitle(correctString + "2");
        printedMedia2.setDescription(correctString + "2");
        printedMedia2.setImageUrl(correctString + "2");
        printedMedia2.setItemNumber(correctString + "2");
        printedMedia2.setGenre(correctString + "2");
        printedMedia2.setPublishDate(correctDate);
        printedMedia2.setIsReservable(false);
        printedMedia2.setIssueNumber(correctString);
        printedMedia2.setType(Item.Type.PrintedMedia);
        List<Item> list = new ArrayList<Item>();
        list.add(printedMedia1);
        list.add(printedMedia2);

        return list;
      });
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient()
      .when(printedMediaDao.save(any(PrintedMedia.class)))
      .thenAnswer(returnParameterAsAnswer);
    lenient()
      .when(librarianDao.save(any(Librarian.class)))
      .thenAnswer(returnParameterAsAnswer);
    lenient()
      .when(headLibrarianDao.save(any(HeadLibrarian.class)))
      .thenAnswer(returnParameterAsAnswer);
  }

  @Test
  //Create printed media
  public void testCreatePrintedMedia() {
    PrintedMedia printedMedia = null;
    try {
      printedMedia =
      printedMediaService.createPrintedMedia(
          "librarian",
          correctString,
          correctString,
          correctString,
          correctString,
          correctDate,
          true,
          correctString
        );
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(printedMedia);
    assertEquals(correctString, printedMedia.getItemTitle());
    assertEquals(correctString, printedMedia.getDescription());
    assertEquals(correctString, printedMedia.getImageUrl());
    assertEquals(correctString, printedMedia.getGenre());
    assertEquals(correctDate, printedMedia.getPublishDate());
    assertEquals(true, printedMedia.getIsReservable());
    System.out.println(printedMedia.getItemNumber());
    System.out.println("Printed media created!");
  }

  @Test
  //Create printed media as a patron
  public void testCreatePrintedMediaAsPatron() {
    String error = null;
    PrintedMedia printedMedia = null;
    try {
      printedMedia =
      printedMediaService.createPrintedMedia(
          "patron",
          correctString,
          correctString,
          correctString,
          correctString,
          correctDate,
          true,
          correctString
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("You do not have permission to create an item reservation", error);
    System.out.println(error);
    
  }

  @Test
  //Create printed media with no title
  public void testCreatePrintedMediaWithNoItemTitle() {
    String error = null;
    PrintedMedia printedMedia = null;
    try {
      printedMedia =
        printedMediaService.createPrintedMedia(
          "librarian",
          emptyString,
          correctString,
          correctString,
          correctString,
          correctDate,
          true,
          correctString
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertEquals("Item must have a title", error);
    System.out.println(error);
  }

  @Test
  //Create printed media with no description
  public void testCreatePrintedMediaWithNoItemDescription() {
    String error = null;
    PrintedMedia printedMedia = null;
    try {
      printedMedia =
        printedMediaService.createPrintedMedia(
          "librarian",
          correctString,
          emptyString,
          correctString,
          correctString,
          correctDate,
          true,
          correctString
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNotNull(printedMedia);
    assertEquals(correctString, printedMedia.getItemTitle());
    assertEquals(emptyString, printedMedia.getDescription());
    assertEquals(correctString, printedMedia.getImageUrl());
    assertEquals(correctString, printedMedia.getGenre());
    assertEquals(correctDate, printedMedia.getPublishDate());
    assertEquals(true, printedMedia.getIsReservable());
    System.out.println(printedMedia.getItemNumber());
    System.out.println("Printed media created!");
  }

  @Test
  //Get all printedMedia
  public void testGetAllPrintedMedias() {
    ArrayList<Item> printedMedias = null;

    try {
      printedMedias = new ArrayList<Item>(printedMediaService.getAllPrintedMedias());;
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(printedMedias);
    assertEquals(2, printedMedias.size());
    assertEquals(correctString, printedMedias.get(0).getItemTitle());
    assertEquals(correctString, printedMedias.get(0).getDescription());
    assertEquals(correctString, printedMedias.get(0).getImageUrl());
    assertEquals(correctString, printedMedias.get(0).getGenre());
    assertEquals(correctDate, printedMedias.get(0).getPublishDate());
    assertEquals(true, printedMedias.get(0).getIsReservable());
    assertEquals(correctString, printedMedias.get(1).getItemTitle());
    assertEquals(correctString, printedMedias.get(1).getDescription());
    assertEquals(correctString, printedMedias.get(1).getImageUrl());
    assertEquals(correctString, printedMedias.get(1).getGenre());
    assertEquals(correctDate, printedMedias.get(1).getPublishDate());
    assertEquals(true, printedMedias.get(1).getIsReservable());

    for (Item printedMedia : printedMedias) {
      System.out.println(printedMedia.getItemTitle());
    }
  }
}
