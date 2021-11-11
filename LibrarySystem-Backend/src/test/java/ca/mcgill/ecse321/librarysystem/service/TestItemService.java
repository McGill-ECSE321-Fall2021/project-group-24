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
}
