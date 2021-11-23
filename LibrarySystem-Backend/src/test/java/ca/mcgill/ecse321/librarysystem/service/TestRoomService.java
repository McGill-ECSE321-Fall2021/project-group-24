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
import ca.mcgill.ecse321.librarysystem.service.HeadLibrarianService;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.SourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

/**
 * @author Selena
 */
@ExtendWith(MockitoExtension.class)
public class TestRoomService {

  @Mock
  private RoomRepository roomDao;

  @InjectMocks
  private RoomService roomService;

  @Mock
  private HeadLibrarianRepository headLibrarianDao;

  @Mock
  private LibrarianRepository librarianDao;

  @Mock
  private PatronRepository patronDao;

  private static final int testCapacity = 10;
  private static final int invalidCapacity = -5;
  private static final String repeatedRoomNum = "room1";
  private static final String testRoomNum = "room2";
  private static final int updatedCapacity = 15;
  private static final String roomWithbookings = "roomWithbookings";

  @BeforeEach
  public void setMockOutput() {
    lenient()
      .when(roomDao.findAll())
      .thenAnswer((InvocationOnMock invocation) -> {
        List<Room> allRooms = new ArrayList<Room>();

        Room room = new Room();
        room.setCapacity(testCapacity);
        room.setRoomBookings(null);
        room.setRoomNum(repeatedRoomNum);
        allRooms.add(room);

        Room roomWithBookings = new Room();
        roomWithBookings.setCapacity(testCapacity);

        List<RoomBooking> list = new ArrayList<RoomBooking>();
        RoomBooking rb = new RoomBooking();
        rb.setDate(Date.valueOf(LocalDate.now().plusDays(1)));
        list.add(rb);
        Set<RoomBooking> set = new HashSet<>(list);

        roomWithBookings.setRoomBookings(set);
        roomWithBookings.setRoomNum(roomWithbookings);
        allRooms.add(roomWithBookings);
        return allRooms;
      });
    lenient()
      .when(roomDao.findRoomByRoomNum(roomWithbookings))
      .thenAnswer((InvocationOnMock invocation) -> {
        Room roomWithBookings = new Room();
        roomWithBookings.setCapacity(testCapacity);

        List<RoomBooking> list = new ArrayList<RoomBooking>();
        RoomBooking rb = new RoomBooking();
        rb.setDate(Date.valueOf(LocalDate.now().plusDays(1)));
        list.add(rb);
        Set<RoomBooking> set = new HashSet<>(list);

        roomWithBookings.setRoomBookings(set);
        roomWithBookings.setRoomNum(roomWithbookings);
        return roomWithBookings;
      });
    lenient()
      .when(roomDao.findRoomByRoomNum(repeatedRoomNum))
      .thenAnswer((InvocationOnMock invocation) -> {
        Room room = new Room();
        room.setCapacity(testCapacity);
        room.setRoomBookings(null);
        room.setRoomNum(repeatedRoomNum);
        return room;
      });
    lenient()
      .when(headLibrarianDao.findUserByIdNum("admin"))
      .thenAnswer((InvocationOnMock invocation) -> {
        HeadLibrarian headlibriarian = new HeadLibrarian();
        headlibriarian.setIdNum("admin");
        headlibriarian.setIsLoggedIn(true);
        return headlibriarian;
      });
    lenient()
      .when(patronDao.findUserByIdNum("patron1"))
      .thenAnswer((InvocationOnMock invocation) -> {
        Patron patron = new Patron();
        patron.setIdNum("patron1");
        patron.setIsLoggedIn(true);
        return patron;
      });

    lenient()
      .when(librarianDao.findUserByIdNum("librarian1"))
      .thenAnswer((InvocationOnMock invocation) -> {
        Librarian libriarian = new Librarian();
        libriarian.setIdNum("librarian1");
        libriarian.setIsLoggedIn(true);

        return libriarian;
      });

    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient()
      .when(roomDao.save(any(Room.class)))
      .thenAnswer(returnParameterAsAnswer);
    lenient()
      .when(headLibrarianDao.save(any(HeadLibrarian.class)))
      .thenAnswer(returnParameterAsAnswer);
    lenient()
      .when(librarianDao.save(any(Librarian.class)))
      .thenAnswer(returnParameterAsAnswer);
  }

  // test case 0: Create a room as HeadLibrarian with valid parameters
  // expected outcome: Room created
  @Test
  public void createRoomAsHeadLibrarianSuccessfully() {
    Room r = null;
    try {
      r = roomService.createRoom("admin", testRoomNum, testCapacity);
    } catch (Exception e) {
      System.out.print(e.getMessage());
    }

    assertNotNull(r);
    assertEquals(testRoomNum, r.getRoomNum());
    assertEquals(testCapacity, r.getCapacity());
  }

  // test case 1: Create a room as HeadLibrarian with invalid capacity
  // expected outcome: error message --  Capacity must be at least 1
  @Test
  public void createRoomWithInvalidCapcity() {
    Room r = null;
    String expectedErrorMsg = "Capacity must be at least 1";
    String actualErrorMsg = null;
    try {
      r = roomService.createRoom("librarian1", testRoomNum, invalidCapacity);
    } catch (Exception e) {
      actualErrorMsg = e.getMessage();
    }

    assertNull(r);
    assertEquals(expectedErrorMsg, actualErrorMsg);
  }

  // test case 2: Create a room as Librarian with valid parameters
  // expected outcome: room
  @Test
  public void createRoomAsLibrarianSuccessfully() {
    Room r = null;
    try {
      r = roomService.createRoom("librarian1", testRoomNum, testCapacity);
    } catch (Exception e) {
      System.out.print(e.getMessage());
    }

    assertNotNull(r);
    assertEquals(testRoomNum, r.getRoomNum());
    assertEquals(testCapacity, r.getCapacity());
  }

  // test case 3: Create a room as Librarian with null roomNum
  // expected outcome: error message - "Room number cannot be null or empty"
  @Test
  public void createRoomAsLibrarianWithNullRoomNum() {
    Room r = null;
    String expectedErrorMsg = "Room number cannot be null or empty";
    String actualErrorMsg = null;
    try {
      r = roomService.createRoom("librarian1", null, testCapacity);
    } catch (Exception e) {
      actualErrorMsg = e.getMessage();
    }

    assertNull(r);
    assertEquals(expectedErrorMsg, actualErrorMsg);
  }

  // test case 4: Create a room as Librarian with empty roomNum
  // expected outcome: error message - "Room number cannot be null or empty"
  @Test
  public void createRoomAsLibrarianWithEmptyRoomNum() {
    Room r = null;
    String expectedErrorMsg = "Room number cannot be null or empty";
    String actualErrorMsg = null;
    try {
      r = roomService.createRoom("librarian1", "", testCapacity);
    } catch (Exception e) {
      actualErrorMsg = e.getMessage();
    }

    assertNull(r);
    assertEquals(expectedErrorMsg, actualErrorMsg);
  }

  // test case 5: Create a room as Librarian with invalid roomNum
  // expected outcome: error message - "Room numbers must be unique"
  @Test
  public void getAllRooms() {
    List<Room> r = null;
    try {
      r = roomService.getAllRooms();
    } catch (Exception e) {
      fail();
    }

    assertEquals(2, r.size());
    assertEquals(testCapacity, r.get(0).getCapacity());
    assertEquals(repeatedRoomNum, r.get(0).getRoomNum());
  }

  // test case 6: Get a specific room using roomNum
  // expected outcome: returns Room with roomNum
  @Test
  public void getRoomWithRoomNum() {
    Room r = null;
    try {
      r = roomService.getRoom(repeatedRoomNum);
    } catch (Exception e) {
      fail();
    }

    assertNotNull(r);
    assertEquals(testCapacity, r.getCapacity());
    assertEquals(repeatedRoomNum, r.getRoomNum());
  }

  // test case 7: Create a room with valid parameters as a patron
  // expected outcome: Error message: You do not have permission to create a room
  @Test
  public void createRoomAsPatron() {
    Room r = null;
    String expectedErrorMsg = "You do not have permission to create a room";
    String actualErrorMsg = null;
    try {
      r = roomService.createRoom("patron1", testRoomNum, testCapacity);
    } catch (Exception e) {
      actualErrorMsg = e.getMessage();
    }

    assertNull(r);
    assertEquals(expectedErrorMsg, actualErrorMsg);
  }

  // test case 8: Create a room with invalid (not unique) roomNum as a head librarian
  // expected outcome: Error message: Room numbers must be unique
  @Test
  public void createRoomAsWithInvalidRoomNum() {
    Room r = null;
    String expectedErrorMsg = "Room numbers must be unique";
    String actualErrorMsg = null;
    try {
      r = roomService.createRoom("admin", repeatedRoomNum, testCapacity);
    } catch (Exception e) {
      actualErrorMsg = e.getMessage();
    }

    assertNull(r);
    assertEquals(expectedErrorMsg, actualErrorMsg);
  }

  // test case 9: Update Room as Librarian with valid parameters
  // expected outcome: Room updated
  @Test
  public void UpdateRoomSuccessfully() {
    Room r = null;
    try {
      r =
        roomService.updateRoom("librarian1", repeatedRoomNum, updatedCapacity);
    } catch (Exception e) {
      fail();
    }

    assertNotNull(r);
    assertEquals(repeatedRoomNum, r.getRoomNum());
    assertEquals(updatedCapacity, r.getCapacity());
  }

  // test case 10: Update Room with invalid capacity
  // expected outcome: Error message: Capacity must be at least 1
  @Test
  public void UpdateRoomWithInvalidCapacity() {
    Room r = null;
    String expectedErrorMsg = "Capacity must be at least 1";
    String actualErrorMsg = null;
    try {
      r =
        roomService.updateRoom("librarian1", repeatedRoomNum, invalidCapacity);
    } catch (Exception e) {
      actualErrorMsg = e.getMessage();
    }

    assertNull(r);
    assertEquals(expectedErrorMsg, actualErrorMsg);
  }

  // test case 11: Update Room as Patron
  // expected outcome: Error message: You do not have permission to update a room
  @Test
  public void UpdateRoomAsPatron() {
    Room r = null;
    String expectedErrorMsg = "You do not have permission to update a room";
    String actualErrorMsg = null;
    try {
      r = roomService.updateRoom("patron1", repeatedRoomNum, updatedCapacity);
    } catch (Exception e) {
      actualErrorMsg = e.getMessage();
    }

    assertNull(r);
    assertEquals(expectedErrorMsg, actualErrorMsg);
  }

  // test case 12: Delete Room as Patron
  // expected outcome: Error message: You do not have permission to delete a room
  @Test
  public void DeleteRoomAsPatron() {
    Room r = null;
    String expectedErrorMsg = "You do not have permission to delete a room";
    String actualErrorMsg = null;
    try {
      r = roomService.deleteRoom("patron1", repeatedRoomNum);
    } catch (Exception e) {
      actualErrorMsg = e.getMessage();
    }

    assertNull(r);
    assertEquals(expectedErrorMsg, actualErrorMsg);
  }

  // test case 13: Delete Room Successfully
  // expected outcome: Room deleted
  @Test
  public void DeleteRoomSuccessfully() {
    Room r = null;
    try {
      r = roomService.deleteRoom("librarian1", repeatedRoomNum);
    } catch (Exception e) {
      fail();
    }
    assertNotNull(r);
    assertEquals(repeatedRoomNum, r.getRoomNum());
  }

  // test case 14: Delete Room with future RoomBookings
  // expected outcome: Error message: The room has bookings in the future, cannot delete
  @Test
  public void DeleteRoomWithRoombookings() {
    Room r = null;
    String expectedErrorMsg =
      "The room has bookings in the future, cannot delete";
    String actualErrorMsg = null;
    try {
      r = roomService.deleteRoom("librarian1", roomWithbookings);
    } catch (Exception e) {
      actualErrorMsg = e.getMessage();
    }
    assertNull(r);
    assertEquals(expectedErrorMsg, actualErrorMsg);
  }
}
