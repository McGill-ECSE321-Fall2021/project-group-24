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
import ca.mcgill.ecse321.librarysystem.model.TimeSlot.DayOfWeek;
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
 * 
 * @author Selena
 *
 */
@ExtendWith(MockitoExtension.class)
public class TestRoomBookingService {
	@Mock 
	private RoomRepository roomDao;
	
	@InjectMocks
	private RoomBookingService roomBookingService;
	
	@Mock
	private HeadLibrarianRepository headLibrarianDao;

	@Mock
	private LibrarianRepository librarianDao;
	
	@Mock 
	private PatronRepository patronDao;
	
	@Mock 
	private RoomBookingRepository roomBookingDao;
	
	@Mock 
	private LibraryHourRepository libraryHourDao;
	
	
	private static final int testCapacity = 10;
	private static final int invalidCapacity = -5;
	private static final String repeatedRoomNum = "room1";
	private static final String testRoomNum = "room2";
	private static final String invalidRoomNum = "INVALID ROOM";
	private static final int updatedCapacity = 15;
	private static final String roomWithbookings = "roomWithbookings";
	private static final String testPatron = "patron1";
	private static final String invalidPatron = "INVALID ID";
	private static final String testTimeSlotId="RoomBooking-112:00:00room1";
	
	
	private static final Time bookingStartTime = Time.valueOf("12:00:00");
	private static final Time bookingEndTime = Time.valueOf("14:00:00");
	private static final Time libraryStartTime = Time.valueOf("10:00:00");
	private static final Time libraryEndTime = Time.valueOf("18:00:00");
	
	private static final Time newStartTime = Time.valueOf("13:00:00");
	private static final Time newEndTime = Time.valueOf("15:00:00");
	
	private static final Date bookingDate = Date.valueOf("2022-02-14");
	private static final Date invalidBookingDate = Date.valueOf("2020-02-14");
	
	private static final Time afterEndTimeBookingStartTime = Time.valueOf("17:00:00");
	private static final Time invalidBookingStartTime = Time.valueOf("6:00:00");
	
	@BeforeEach
	  public void setMockOutput() {
	    lenient()
	      .when(roomDao.findAll())
	      .thenAnswer((InvocationOnMock invocation) -> {
	    	  List<Room> allRooms  = new ArrayList<Room>(); 
		       
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
		       rb.setDate(bookingDate);
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
			       
			       
			       room.setRoomNum(repeatedRoomNum);
			       return room;
	      });
	    lenient()
	      .when(roomDao.findRoomByRoomNum("room2"))
	      .thenAnswer((InvocationOnMock invocation) -> {
		    	   Room room = new Room();
			       room.setCapacity(testCapacity);
			       List<RoomBooking> list = new ArrayList<RoomBooking>();
			       RoomBooking rb = new RoomBooking();
			       rb.setDate(bookingDate);
			       list.add(rb);
			       Set<RoomBooking> set = new HashSet<>(list);
			       
			       room.setRoomBookings(set);
			       
			       room.setRoomNum(testRoomNum);
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
	      .when(patronDao.findUserByIdNum("patron2"))
	      .thenAnswer((InvocationOnMock invocation) -> {
		       Patron patron = new Patron();
		       patron.setIdNum("patron2");
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
	    lenient()
	      .when(roomBookingDao.findRoomBookingsByIdNum("patron1"))
	      .thenAnswer((InvocationOnMock invocation) -> {
	    	  	List<RoomBooking> roombookings = new ArrayList<RoomBooking>();
		    	   RoomBooking roombooking = new RoomBooking();
		    	   roombooking.setDate(bookingDate);
		    	   roombooking.setIdNum("patron1");
		    	   roombooking.setStartTime(Time.valueOf("12:00:00"));
		    	   roombooking.setEndTime(Time.valueOf("14:00:00"));
		    	   roombooking.setRoomNum(repeatedRoomNum);
		    	   roombookings.add(roombooking);
			       return roombookings;
	      });
	    lenient()
	      .when(roomBookingDao.findAll())
	      .thenAnswer((InvocationOnMock invocation) -> {
		    	   List<RoomBooking> roombookings = new ArrayList<RoomBooking>();
		    	   RoomBooking roombooking = new RoomBooking();
		    	   roombooking.setDate(bookingDate);
		    	   roombooking.setIdNum("patron1");
		    	   roombooking.setStartTime(Time.valueOf("12:00:00"));
		    	   roombooking.setEndTime(Time.valueOf("14:00:00"));
		    	   roombooking.setRoomNum(repeatedRoomNum);
		    	   roombookings.add(roombooking);
			       return roombookings;
	      });
	    lenient()
	      .when(roomBookingDao.findRoomBookingByTimeSlotId(testTimeSlotId))
	      .thenAnswer((InvocationOnMock invocation) -> {
		    	   RoomBooking roombooking = new RoomBooking();
		    	   roombooking.setDate(bookingDate);
		    	   roombooking.setIdNum("patron1");
		    	   roombooking.setStartTime(Time.valueOf("12:00:00"));
		    	   roombooking.setEndTime(Time.valueOf("14:00:00"));
		    	   roombooking.setRoomNum(repeatedRoomNum);
		    	   roombooking.setTimeSlotId(testTimeSlotId);
			       return roombooking;
	      });
	    
	    
	    lenient()
	      .when(libraryHourDao.findHourByDayOfWeek(DayOfWeek.MONDAY))
	      .thenAnswer((InvocationOnMock invocation) -> {
		    	   LibraryHour lh = new LibraryHour();
		    	   lh.setDayOfWeek(DayOfWeek.MONDAY);
		    	   lh.setStartTime(Time.valueOf("10:00:00"));
		    	   lh.setEndTime(Time.valueOf("18:00:00"));
		    	   return lh;
		    		   
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
	    lenient()
	      .when(roomBookingDao.save(any(RoomBooking.class)))
	      .thenAnswer(returnParameterAsAnswer);
	    lenient()
	      .when(libraryHourDao.save(any(LibraryHour.class)))
	      .thenAnswer(returnParameterAsAnswer);
	  }
	
	// Test 0: Create ItemReservation for an item with no other reservations
	// expected outcome: a RoomBooking is created with idNum of Patron
	@Test
	public void CreateRoomBooking() {
		RoomBooking rb = null;
		try {
			rb = roomBookingService.createRoomBooking("admin", bookingDate, bookingStartTime, bookingEndTime, "patron1", repeatedRoomNum);
		} catch (Exception e) {
			 fail();
		}
		
		assertNotNull(rb);
	    assertEquals(bookingDate, rb.getDate());		
	    assertEquals(bookingStartTime, rb.getStartTime());	
	    assertEquals(bookingEndTime, rb.getEndTime());		
	    assertEquals("patron1", rb.getIdNum());
	}
	
	// test case 1: Create a room as Librarian with empty roomNum
	// expected outcome: error message - Room number cannot be null or empty
	@Test
	public void createRoomBookingAsLibrarianWithEmptyRoomNum() {
		RoomBooking rb = null;
		String expectedErrorMsg =  "Room number cannot be null or empty";
		String actualErrorMsg = null;
		try {
			rb = roomBookingService.createRoomBooking("admin", bookingDate, bookingStartTime, bookingEndTime, "patron1", "");
		} catch (Exception e) {
			 actualErrorMsg = e.getMessage();
		}
		
		assertNull(rb);
	    assertEquals(expectedErrorMsg, actualErrorMsg);				
	}
	
	// test case 2: Attempt to create RoomBooking as Patron/Librarian with an invalid roomNum
	// expected outcome: error message - invalid roomNumbe
	@Test
	public void createRoomBookingAsLibrarianWithInvalidyRoomNum() {
		RoomBooking rb = null;
		String expectedErrorMsg =  "invalid roomNumber";
		String actualErrorMsg = null;
		try {
			rb = roomBookingService.createRoomBooking("admin", bookingDate, bookingStartTime, bookingEndTime, "patron1", invalidRoomNum);
		} catch (Exception e) {
			 actualErrorMsg = e.getMessage();
		}
		
		assertNull(rb);
	    assertEquals(expectedErrorMsg, actualErrorMsg);				
	}
	
	// test case 3: Attempt to create RoomBooking as Librarian for a Patron with an empty idNum 
	// expected outcome: error message - IdNum cannot be null or empty
	@Test
	public void createRoomBookingAsLibrarianWithEmptyIdNum() {
		RoomBooking rb = null;
		String expectedErrorMsg =  "IdNum cannot be null or empty";
		String actualErrorMsg = null;
		try {
			rb = roomBookingService.createRoomBooking("admin", bookingDate, bookingStartTime, bookingEndTime, "", testRoomNum);
		} catch (Exception e) {
			 actualErrorMsg = e.getMessage();
		}
		
		assertNull(rb);
	    assertEquals(expectedErrorMsg, actualErrorMsg);				
	}
	
	// test case 4: Attempt to create RoomBooking as Librarian for a Patron with an invalid idNum 
	// expected outcome: error message - invalid IdNum
	@Test
	public void createRoomBookingAsLibrarianWithInvalidIdNum() {
		RoomBooking rb = null;
		String expectedErrorMsg =  "invalid IdNum";
		String actualErrorMsg = null;
		try {
			rb = roomBookingService.createRoomBooking("admin", bookingDate, bookingStartTime, bookingEndTime, invalidPatron, testRoomNum);
		} catch (Exception e) {
			 actualErrorMsg = e.getMessage();
		}
		
		assertNull(rb);
	    assertEquals(expectedErrorMsg, actualErrorMsg);				
	}

	// test case 5: Patron creates RoomBooking with an their idNum
	// expected outcome: A RoomBooking created successfully with idNUm of Patron
	@Test
	public void createRoomBookingAsPatron() {
		RoomBooking rb = null;
		try {
			rb = roomBookingService.createRoomBooking("patron1", bookingDate, bookingStartTime, bookingEndTime, "patron1", repeatedRoomNum);
		} catch (Exception e) {
			fail();
		}
		
		assertNotNull(rb);
		assertEquals(bookingDate, rb.getDate());		
	    assertEquals(bookingStartTime, rb.getStartTime());	
	    assertEquals(bookingEndTime, rb.getEndTime());		
	    assertEquals("patron1", rb.getIdNum());
			
	}
	
	// test case 6: Patron creates RoomBooking with an empty idNum
	// expected outcome: error message - invalid IdNum
	@Test
	public void createRoomBookingAsPatronWithEmptyIdNum() {
	RoomBooking rb = null;
	String expectedErrorMsg =  "IdNum cannot be null or empty";
	String actualErrorMsg = null;
	try {
		rb = roomBookingService.createRoomBooking("patron1", bookingDate, bookingStartTime, bookingEndTime, "", testRoomNum);
	} catch (Exception e) {
		 actualErrorMsg = e.getMessage();
	}
	
	assertNull(rb);
    assertEquals(expectedErrorMsg, actualErrorMsg);	
	}
 
    // test case 7: Patron creates RoomBooking with an idNum that is not their own
 	// expected outcome: error message - You can only create room bookings for yourself
 	@Test
 	public void createRoomBookingAsPatronWithDiffIdNum() {
	 	RoomBooking rb = null;
	 	String expectedErrorMsg =  "You can only create room bookings for yourself";
	 	String actualErrorMsg = null;
	 	try {
	 		rb = roomBookingService.createRoomBooking("patron1", bookingDate, bookingStartTime, bookingEndTime, "patron2", repeatedRoomNum);
	 	} catch (Exception e) {
	 		 actualErrorMsg = e.getMessage();
	 	}
	 	
	 	assertNull(rb);
	    assertEquals(expectedErrorMsg, actualErrorMsg);			
	}
	 	
 	// test case 8: Get all RoomBookings as Librarian
  	// expected outcome: error message - You can only create room bookings for yourself
  	@Test
  	public void getAllRoomBookingsAsLibrarian() {
	  	List<RoomBooking> rb = null;
	  	try {
	  		rb = roomBookingService.getAllRoomBookings("admin");
	  	} catch (Exception e) {
	  		 fail();
	  	}
	  	
	  	assertNotNull(rb);
	  	assertEquals(1, rb.size());
				
	 }
 
  	// test case 9: Get all RoomBooking as Patron
  	// expected outcome: error message - Only a librarian or head librarian can get all roombooking
  	@Test
  	public void getRoomBookingsAsPatron() {
  		List<RoomBooking> rb = null;
  		String expectedErrorMsg =  "Only a librarian or head librarian can get all roombooking";
 		String actualErrorMsg = null;
  		try {
  			rb = roomBookingService.getAllRoomBookings("patron1");
  		} catch (Exception e) {
  			actualErrorMsg = e.getMessage();
  		}

 		assertNull(rb);
 	    assertEquals(expectedErrorMsg, actualErrorMsg);				
  	}
  	
  	// test case 10: Get all RoomBooking belonging to a room as Patron
   	// expected outcome: error message - Only a librarian or head librarian can get roombooking by room number
   	@Test
   	public void getRoomBookingsOfRoomAsPatron() {
   		List<RoomBooking> rb = null;
   		String expectedErrorMsg =  "Only a librarian or head librarian can get roombooking by room number";
  		String actualErrorMsg = null;
   		try {
   			rb = roomBookingService.getRoomBookingsByRoomNum("patron1", repeatedRoomNum);
   		} catch (Exception e) {
   			actualErrorMsg = e.getMessage();
   		}

  		assertNull(rb);
  	    assertEquals(expectedErrorMsg, actualErrorMsg);				
   	}
   	
   	// test case 11: Get all RoomBooking belonging to a room as Librarian 
   	// expected outcome: Return all RoomBookings as List
   	@Test
   	public void getRoomBookingsOfRoomAsLibrarian() {
   		List<RoomBooking> rb = null;
   		try {
   			rb = roomBookingService.getRoomBookingsByRoomNum("librarian1", repeatedRoomNum);
   		} catch (Exception e) {
   			fail();
   		}
   		
  	  	assertNotNull(rb);
	  	assertEquals(0, rb.size());  	
							
   	}
   	// test case 12: Get all RoomBooking belonging to themselves as a Patron
   	// expected outcome: Return all RoomBookings as List
   	@Test
   	public void getRoomBookingsOfPatronAsPatron() {
   		List<RoomBooking> rb = null;
   		try {
   			rb = roomBookingService.getRoomBookingsByIdNum("patron1", "patron1");
   		} catch (Exception e) {
   			fail();
   		}
   		
  	  	assertNotNull(rb);
	  	assertEquals(1, rb.size());  	
							
   	}
  	// test case 13: Get all RoomBookings belonging to another person as a Patron
   	// expected outcome: error message - Patrons are only allowed to view their own room bookings
   	@Test
   	public void getRoomBookingsOfPatronAsAnotherPatron() {
   		List<RoomBooking> rb = null;
   		String expectedErrorMsg =  "Patrons are only allowed to view their own room bookings";
	 	String actualErrorMsg = null;
   		try {
   			
   			rb = roomBookingService.getRoomBookingsByIdNum("patron1", "patron2");
   		} catch (Exception e) {
   			actualErrorMsg = e.getMessage();

   		}
  		
  	  	assertNull(rb);	
  	  	assertEquals(expectedErrorMsg, actualErrorMsg);
							
   	}
   	// test case 14: Get all RoomBooking belonging to a room as Librarian 
   	// expected outcome: Return all RoomBookings as List
   	@Test
   	public void getRoomBookingsOfPatronAsLibrarian() {
   		List<RoomBooking> rb = null;
   		try {
   			rb = roomBookingService.getRoomBookingsByIdNum("librarian1", "patron1");
   		} catch (Exception e) {
   		
   			fail();
   		}
   		
  	  	assertNotNull(rb);
	  	assertEquals(1, rb.size());  	
							
   	}
   	// test case 15: Get a specific RoomBooking using timeSlotId as Librarian 
   	// expected outcome: Return the RoomBooking
   	@Test
   	public void getRoomBookingWithTimeSlotIdAsLibrarian() {
   		RoomBooking rb = null;
   		try {
   			rb = roomBookingService.getRoomBookingsByTimeSlotId("librarian1", testTimeSlotId);
   		} catch (Exception e) {
   			fail();
   		}
   		
  	  	assertNotNull(rb);
		assertEquals(bookingDate, rb.getDate());		
	    assertEquals(bookingStartTime, rb.getStartTime());	
	    assertEquals(bookingEndTime, rb.getEndTime());		
	    assertEquals("patron1", rb.getIdNum());
 	
							
   	}
   	// test case 16: Get a specific RoomBooking using timeSlotId as Patron 
   	// expected outcome: error message - Only a librarian or head librarian can get roombooking by time slot id
  	@Test
   	public void getRoomBookingWithTimeSlotIdAsPatron() {
   		RoomBooking rb = null;
   		String expectedErrorMsg =  "Only a librarian or head librarian can get roombooking by time slot id";
	 	String actualErrorMsg = null;
   		try {
   			
   			rb = roomBookingService.getRoomBookingsByTimeSlotId("patron1", testTimeSlotId);
   		} catch (Exception e) {
   			actualErrorMsg = e.getMessage();

   		}
  		
  	  	assertNull(rb);	
  	  	assertEquals(expectedErrorMsg, actualErrorMsg);
							
   	}
  	
	// test case 16: Update RoomBooking as Librarian 
   	// expected outcome: Return the updated RoomBooking


  	@Test
   	public void UpdateRoomBookingAsLibrraian() {
   		RoomBooking rb = null;

   		try {
   			rb = roomBookingService.updateRoomBooking("admin", testTimeSlotId, bookingDate, newStartTime, newEndTime, repeatedRoomNum);
   		} catch (Exception e) {
   			fail();

   		}
  		
   	 	assertNotNull(rb);
		assertEquals(bookingDate, rb.getDate());		
	    assertEquals(newStartTime, rb.getStartTime());	
	    assertEquals(newEndTime, rb.getEndTime());		
	    assertEquals("patron1", rb.getIdNum());
							
   	}
  	
  	// test case 18: Update their own RoomBooking as Patron 
   	// expected outcome: Return the updated RoomBooking
  	@Test
   	public void UpdateOwnRoomBookingAsPatron() {
   		RoomBooking rb = null;

   		try {
   			rb = roomBookingService.updateRoomBooking("patron1", testTimeSlotId, bookingDate, newStartTime, newEndTime, repeatedRoomNum);
   		} catch (Exception e) {
   			fail();

   		}
  		
   	 	assertNotNull(rb);
		assertEquals(bookingDate, rb.getDate());		
	    assertEquals(newStartTime, rb.getStartTime());	
	    assertEquals(newEndTime, rb.getEndTime());		
	    assertEquals("patron1", rb.getIdNum());
							
   	}
   
	// test case 19: Update another patron’s RoomBooking as Patron 
   	// expected outcome: error message - You do not have permission to modify this room booking
  	@Test
   	public void UpdateOtherPatronRoomBookingAsPatron() {
   		RoomBooking rb = null;
   		String expectedErrorMsg =  "You do not have permission to modify this room booking";
  		String actualErrorMsg = null;
   		try {
   			rb = roomBookingService.updateRoomBooking("patron2", testTimeSlotId, bookingDate, newStartTime, newEndTime, repeatedRoomNum);
   		} catch (Exception e) {
   			actualErrorMsg = e.getMessage();

   		}
  		
   	 	assertNull(rb);
		assertEquals(expectedErrorMsg, actualErrorMsg);		

							
   	}
  	
  	// test case 20: Delete a RoomBooking
   	// expected outcome: return deleted RoomBooking
  	@Test
   	public void deleteRoomBookingAsLibrarian() {
   		RoomBooking rb = null;
   		try {
   			rb = roomBookingService.deleteRoomBooking("admin", testTimeSlotId);
   		} catch (Exception e) {
   			fail();	

   		}
  		
   		assertNotNull(rb);
		assertEquals(bookingDate, rb.getDate());		
	    assertEquals(bookingStartTime, rb.getStartTime());	
	    assertEquals(bookingEndTime, rb.getEndTime());		
	    assertEquals("patron1", rb.getIdNum());
							
   	}
  	
  	// test case 21: Delete own RoomBooking as Patron
   	// expected outcome: return deleted RoomBooking
  	@Test
   	public void deleteOwnRoomBookingAsPatron() {
   		RoomBooking rb = null;
   		try {
   			rb = roomBookingService.deleteRoomBooking("patron1", testTimeSlotId);
   		} catch (Exception e) {
   			fail();	

   		}
  		
   		assertNotNull(rb);
		assertEquals(bookingDate, rb.getDate());		
	    assertEquals(bookingStartTime, rb.getStartTime());	
	    assertEquals(bookingEndTime, rb.getEndTime());		
	    assertEquals("patron1", rb.getIdNum());
							
   	}
  	
  	// test case 22: Delete own RoomBooking as Patron
   	// expected outcome: return deleted RoomBooking
  	@Test
   	public void deleteDiffRoomBookingAsPatron() {
   		RoomBooking rb = null;
   		String expectedErrorMsg = "You do not have permission to delete this room booking";
   		String actualErrorMsg = null;
   		try {
   			rb = roomBookingService.deleteRoomBooking("patron2", testTimeSlotId);
   		} catch (Exception e) {
   			actualErrorMsg = e.getMessage();

   		}
  		
   		assertNull(rb);
		assertEquals(expectedErrorMsg, actualErrorMsg);
   	}
  	
  	// test case 23: Update roombooking as Librarian with startTime after EndTime
   	// expected outcome: error message - Start time must be before end time
  	@Test
   	public void updateRoomBookingAsLibrarianWithStartTimeAfterEndTime() {
   		RoomBooking rb = null;
   		String expectedErrorMsg = "Start time must be before end time";
   		String actualErrorMsg = null;
   		try {
   			rb = roomBookingService.updateRoomBooking("admin", testTimeSlotId, bookingDate, afterEndTimeBookingStartTime, newEndTime, repeatedRoomNum);
   		} catch (Exception e) {
   			actualErrorMsg = e.getMessage();

   		}

   		assertNull(rb);
		assertEquals(expectedErrorMsg, actualErrorMsg);
   	}
  	
  	// test case 24: Create room booking as Librarian with startTime after EndTime
   	// expected outcome: error message - Start time must be before end time
  	@Test
   	public void createRoomBookingAsLibrarianWithStartTimeAfterEndTime() {
   		RoomBooking rb = null;
   		String expectedErrorMsg = "Start time must be before end time";
   		String actualErrorMsg = null;
   		try {
   			rb = roomBookingService.createRoomBooking("admin", bookingDate, afterEndTimeBookingStartTime, bookingEndTime, "patron1", repeatedRoomNum);
   		} catch (Exception e) {
   			actualErrorMsg = e.getMessage();

   		}

   		assertNull(rb);
		assertEquals(expectedErrorMsg, actualErrorMsg);
   	}
  	
  	
}
