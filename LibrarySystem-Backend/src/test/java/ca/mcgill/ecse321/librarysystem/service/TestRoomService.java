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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hibernate.annotations.SourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

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
	
	private static final int testCapacity = 10;
	private static final String testRoomNum = "room1";
	
	

	@BeforeEach
	  public void setMockOutput() {
	    lenient()
	      .when(roomDao.findAll())
	      .thenAnswer((InvocationOnMock invocation) -> {
	       Room room = new Room();
	       room.setCapacity(testCapacity);
	       room.setRoomBookings(null);
	       room.setRoomNum(testRoomNum);
	       return room;
	      });
	    lenient()
	      .when(roomDao.findById(anyString()))
	      .thenAnswer((InvocationOnMock invocation) -> {
	    	  Room room = new Room();
		       room.setCapacity(testCapacity);
		       room.setRoomBookings(null);
		       room.setRoomNum(testRoomNum);
		       return room;
	      });
	    lenient()
	      .when(roomDao.findRoomByRoomNum(anyString()))
	      .thenAnswer((InvocationOnMock invocation) -> {
	    	  Room room = new Room();
		       room.setCapacity(testCapacity);
		       room.setRoomBookings(null);
		       room.setRoomNum(testRoomNum);
		       return room;
	      });
	    lenient()
	      .when(headLibrarianDao.findUserByIdNum(anyString()))
	      .thenAnswer((InvocationOnMock invocation) -> {
		       HeadLibrarian headlibriarian = new HeadLibrarian();
		       headlibriarian.setIdNum("admin");
		       headlibriarian.setIsLoggedIn(true);
		       return headlibriarian;
	      });
	    lenient()
	      .when(librarianDao.findUserByIdNum(anyString()))
	      .thenAnswer((InvocationOnMock invocation) -> {
		       Librarian libriarian = new Librarian();
		       libriarian.setIdNum("admin");
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
	
	@Test
	public void createRoom() {
		Room r = null;
		try {
			r = roomService.createRoom("admin", testRoomNum, testCapacity );
		} catch (Exception e) {
		      fail();
		}
		
		assertNotNull(r);
	    assertEquals(testRoomNum, r.getRoomNum());
	    assertEquals(testCapacity, r.getCapacity());
		
		
	}
}
