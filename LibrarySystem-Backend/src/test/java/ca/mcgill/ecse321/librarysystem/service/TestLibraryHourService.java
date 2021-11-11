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
import ca.mcgill.ecse321.librarysystem.dto.LibraryHourDto;
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
public class TestLibraryHourService {

	@Mock
	private LibraryHourRepository libraryHourRepo; 
	@Mock
	private LibrarianRepository librarianRepo;
	@Mock
	private HeadLibrarianRepository headLibrarianRepo; 
	
	
	@InjectMocks
	private LibraryHourService libraryHourService; 
	
	
	
	private static final String HEAD_LIBRARIAN_ID = "admin"; 
	private static final String LIBRARIAN_ID = "randomId"; 
	private static final TimeSlot.DayOfWeek DAY_OF_WEEK = TimeSlot.DayOfWeek.MONDAY;  
	
	private static final Time START_TIME = Time.valueOf("9:00:00");  
	private static final Time END_TIME = Time.valueOf("17:00:00");  
	
	private static final Time START_TIME_2 = Time.valueOf("10:00:00");  
	private static final Time END_TIME_2 = Time.valueOf("19:00:00");  


	@BeforeEach
	  public void setMockOutput() {
	    lenient() 
	      .when(libraryHourRepo.findAll())
	      .thenAnswer((InvocationOnMock invocation) -> {
	    	  List<LibraryHour> libraryHours  = new ArrayList<LibraryHour>(); 
		      LibraryHour libraryHour = new LibraryHour(); 
			  libraryHour.setDayOfWeek(DAY_OF_WEEK); 
		      libraryHour.setStartTime(START_TIME);
		      libraryHour.setEndTime(END_TIME);
		      libraryHours.add(libraryHour); 
		      return libraryHours;
	      });

	    lenient()
	      .when(headLibrarianRepo.findUserByIdNum(anyString()))
	      .thenAnswer((InvocationOnMock invocation) -> {
		       HeadLibrarian headlibriarian = new HeadLibrarian();
		       headlibriarian.setIdNum(HEAD_LIBRARIAN_ID);
		       headlibriarian.setIsLoggedIn(true);
		       return headlibriarian;
	      });
	    
	    lenient()
	      .when(librarianRepo.findUserByIdNum(anyString()))
	      .thenAnswer((InvocationOnMock invocation) -> {
		       Librarian librarian = new Librarian();
		       librarian.setIdNum(LIBRARIAN_ID);
		       librarian.setIsLoggedIn(true);
		       return librarian;
	      });
	}
	
	@Test // create a library hour with valid parameters
	public void testCreateLibraryHour() {
		LibraryHour libraryHour = null; 
		
		try {
			libraryHour = libraryHourService.createLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK, START_TIME, END_TIME);
		} catch (Exception e) {
            fail(e.getMessage());
		}
		assertNotNull(libraryHour); 
		assertEquals(START_TIME, libraryHour.getStartTime()); 
		assertEquals(END_TIME, libraryHour.getEndTime());
		assertEquals(DAY_OF_WEEK, libraryHour.getDayOfWeek());

	}
	
	@Test // create a library hour with as a librarian 
	public void testCreateLibraryHourAsLibrarian() {
		LibraryHour libraryHour = null; 
		
		try {
			libraryHour = libraryHourService.createLibraryHour(LIBRARIAN_ID, DAY_OF_WEEK, START_TIME, END_TIME);
		} catch (Exception e) {
			assertEquals("Only the Head Librarian can create library hours", e.getMessage());
		}
		assertNotNull(libraryHour); 
	}
	
	@Test // create a library hour with start time after end time
	public void testCreateLibraryHourInvalidStartTime() {
		LibraryHour libraryHour = null; 
		
		try {
			libraryHour = libraryHourService.createLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK, END_TIME, START_TIME);
		} catch (Exception e) {
			assertEquals("Start time cannot be after end time", e.getMessage());
		}
	}
	
	@Test // create a library hour on a day that already has one
	public void testCreateLibraryHourSameDay() {
		LibraryHour libraryHour = null; 
		
		try {
			libraryHour = libraryHourService.createLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK, START_TIME, END_TIME);
		} catch (Exception e) {
			assertEquals("There's alreadry a library hour on that day", e.getMessage());
		}
	}
	
	@Test // create a library hour on a day that already has one
	public void testModifyLibraryHours() {
		LibraryHour libraryHour = null; 

		try {
			libraryHourService.createLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK, START_TIME, END_TIME);
			libraryHour = libraryHourService.modifyLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK, START_TIME_2, END_TIME_2); 
		} catch (Exception e) {
			fail(e.getMessage()); 
		}
		
		assertNotNull(libraryHour); 
		assertEquals(START_TIME_2, libraryHour.getStartTime()); 
		assertEquals(END_TIME_2, libraryHour.getEndTime());
		assertEquals(DAY_OF_WEEK, libraryHour.getDayOfWeek());
	}
	
	
}
