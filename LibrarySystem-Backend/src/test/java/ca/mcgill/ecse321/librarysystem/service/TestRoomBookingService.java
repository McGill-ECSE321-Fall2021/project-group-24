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
	private static final int updatedCapacity = 15;
	private static final String roomWithbookings = "roomWithbookings";
	private static final String testPatron = "patron1";
	
	private static final Time bookingStartTime = Time.valueOf("12:00:00");
	private static final Time bookingEndTime = Time.valueOf("14:00:00");
	private static final Time libraryStartTime = Time.valueOf("10:00:00");
	private static final Time libraryEndTime = Time.valueOf("18:00:00");
	
	private static final Date bookingDate = Date.valueOf("2022-02-14");
	private static final Date invalidBookingDate = Date.valueOf("2020-02-14");
	
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
	    lenient()
	      .when(roomBookingDao.findRoomBookingByIdNum("patron1"))
	      .thenAnswer((InvocationOnMock invocation) -> {
		    	   RoomBooking roombooking = new RoomBooking();
		    	   roombooking.setDate(Date.valueOf(LocalDate.now()));
		    	   roombooking.setIdNum("patron1");
		    	   roombooking.setStartTime(Time.valueOf("12:00:00"));
		    	   roombooking.setEndTime(Time.valueOf("14:00:00"));
		    	   roombooking.setRoomNum(repeatedRoomNum);
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
	
	// Test 1: Create ItemReservation for an item with no other reservations
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
}
