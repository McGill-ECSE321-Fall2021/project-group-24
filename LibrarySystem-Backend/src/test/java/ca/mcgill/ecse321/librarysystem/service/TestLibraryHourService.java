package ca.mcgill.ecse321.librarysystem.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
	@Mock
	private PatronRepository patronRepo;
	
	@InjectMocks
	private LibraryHourService libraryHourService; 
	
	
	
	private static final String HEAD_LIBRARIAN_ID = "admin"; 
	private static final String LIBRARIAN_ID = "librarianId"; 
	private static final String PATRON_ID = "patronId"; 
	
	private static final TimeSlot.DayOfWeek DAY_OF_WEEK = TimeSlot.DayOfWeek.MONDAY;  
	private static final TimeSlot.DayOfWeek DAY_OF_WEEK_1 = TimeSlot.DayOfWeek.WEDNESDAY;  
	private static final TimeSlot.DayOfWeek DAY_OF_WEEK_2 = TimeSlot.DayOfWeek.TUESDAY;  
	
	private static final Time START_TIME = Time.valueOf("9:00:00");  
	private static final Time END_TIME = Time.valueOf("17:00:00");  
	
	private static final Time START_TIME_1 = Time.valueOf("11:00:00");  
	private static final Time END_TIME_1 = Time.valueOf("14:00:00");  
	
	private static final Time START_TIME_2 = Time.valueOf("10:00:00");  
	private static final Time END_TIME_2 = Time.valueOf("19:00:00");  


	// adds a library hour, a librarian, and a head librarian and saves them to their respective repo's 
	@BeforeEach
	  public void setMockOutput() {
	    lenient() 
	      .when(libraryHourRepo.findAll())
	      .thenAnswer((InvocationOnMock invocation) -> {
	    	  List<LibraryHour> libraryHours = new ArrayList<LibraryHour>(); 
	    	  
		      LibraryHour libraryHour = new LibraryHour(); 
			  libraryHour.setDayOfWeek(DAY_OF_WEEK); 
		      libraryHour.setStartTime(START_TIME);
		      libraryHour.setEndTime(END_TIME);
		      LibraryHour libraryHour2 = new LibraryHour(); 
			  libraryHour2.setDayOfWeek(DAY_OF_WEEK_1); 
		      libraryHour2.setStartTime(START_TIME_1);
		      libraryHour2.setEndTime(END_TIME_1);
		      libraryHours.add(libraryHour);
		      libraryHours.add(libraryHour2);
		      return libraryHours; 
	      });
	    lenient() 
	      .when(libraryHourRepo.findHourByDayOfWeek(DAY_OF_WEEK))
	      .thenAnswer((InvocationOnMock invocation) -> {
		      LibraryHour libraryHour = new LibraryHour(); 
			  libraryHour.setDayOfWeek(DAY_OF_WEEK); 
		      libraryHour.setStartTime(START_TIME);
		      libraryHour.setEndTime(END_TIME);
		      return libraryHour; 
	      });

	    lenient()
	      .when(headLibrarianRepo.findUserByIdNum(HEAD_LIBRARIAN_ID))
	      .thenAnswer((InvocationOnMock invocation) -> {
		       HeadLibrarian headlibriarian = new HeadLibrarian();
		       headlibriarian.setIdNum(HEAD_LIBRARIAN_ID);
		       headlibriarian.setIsLoggedIn(true);
		       return headlibriarian;
	      });
	    
	    lenient()
	      .when(librarianRepo.findUserByIdNum(LIBRARIAN_ID))
	      .thenAnswer((InvocationOnMock invocation) -> {
		       Librarian librarian = new Librarian();
		       librarian.setIdNum(LIBRARIAN_ID);
		       librarian.setIsLoggedIn(true);
		       return librarian;
	      });
	    
	    lenient()
	      .when(patronRepo.findUserByIdNum(PATRON_ID))
	      .thenAnswer((InvocationOnMock invocation) -> {
	    	   Patron patron = new Patron(); 
	    	   patron.setIdNum(PATRON_ID);
		       patron.setIsLoggedIn(true);
		       return patron;
	      });
	    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
	        return invocation.getArgument(0);
	      };
	      
        lenient()
        .when(librarianRepo.save(any(Librarian.class)))
        .thenAnswer(returnParameterAsAnswer);

	    lenient()
	      .when(headLibrarianRepo.save(any(HeadLibrarian.class)))
	      .thenAnswer(returnParameterAsAnswer);
	    
	    lenient()
	      .when(libraryHourRepo.save(any(LibraryHour.class)))
	      .thenAnswer(returnParameterAsAnswer);
	    
	}
	
	@Test // create a library hour with valid parameters
	public void testCreateLibraryHour() {
		LibraryHour libraryHour = null; 
		
		try {
			libraryHour = libraryHourService.createLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK_2, START_TIME, END_TIME);
		} catch (Exception e) {
            fail(e.getMessage());
		}
		assertNotNull(libraryHour); 
		assertEquals(START_TIME, libraryHour.getStartTime()); 
		assertEquals(END_TIME, libraryHour.getEndTime());
		assertEquals(DAY_OF_WEEK_2, libraryHour.getDayOfWeek());

	}
	
	@Test // create a library hour as a librarian 
	public void testCreateLibraryHourAsLibrarian() {
		LibraryHour libraryHour = null; 
		
		try {
			libraryHour = libraryHourService.createLibraryHour(LIBRARIAN_ID, DAY_OF_WEEK_2, START_TIME, END_TIME);
			fail(); 
		} catch (Exception e) {
			assertEquals("Only the Head Librarian can create library hours", e.getMessage());
		}
		assertNull(libraryHour);
	}
	
	@Test // create library hour with null parameters
	public void testCreateLibraryHourNullParams() {
		LibraryHour libHour = null; 
		
		try {
			libHour = libraryHourService.createLibraryHour(HEAD_LIBRARIAN_ID, null, null, null); 
			fail();
		}
		catch (Exception e) {
			assertEquals("Fields cannot be blank", e.getMessage()); 
		}
		assertNull(libHour); 
	}
	
	@Test // create a library hour with start time after end time
	public void testCreateLibraryHourInvalidStartTime() {
		LibraryHour libraryHour = null; 
		
		try {
			libraryHour = libraryHourService.createLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK_2, END_TIME, START_TIME);
			fail(); 
		} catch (Exception e) {
			assertEquals("Start time cannot be after end time", e.getMessage());
		}
		assertNull(libraryHour); 
	}
	
	@Test // create a library hour on a day that already has one
	public void testCreateLibraryHourSameDay() {
		LibraryHour libraryHour = null; 

		try {
			libraryHour = libraryHourService.createLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK, START_TIME, END_TIME);
			fail(); 
		} catch (Exception e) {
			assertEquals("There's already a library hour for that day", e.getMessage());
		}
		assertNull(libraryHour); 

	}
	
	@Test // modify a library hour on a day that already has one with valid parameters
	public void testModifyLibraryHours() {
		LibraryHour libraryHour = null; 

		try {
			libraryHour = libraryHourService.modifyLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK, START_TIME_2, END_TIME_2); 
		} catch (Exception e) {
			fail(e.getMessage()); 
		}
		
		assertNotNull(libraryHour); 
		assertEquals(START_TIME_2, libraryHour.getStartTime()); 
		assertEquals(END_TIME_2, libraryHour.getEndTime());
		assertEquals(DAY_OF_WEEK, libraryHour.getDayOfWeek());
	}
	
	@Test // modify library hour with null parameters
	public void testModifyLibraryHourNullParams() {
		LibraryHour libHour = null; 
		
		try {
			libHour = libraryHourService.modifyLibraryHour(HEAD_LIBRARIAN_ID, null, null, null); 
			fail();
		}
		catch (Exception e) {
			assertEquals("Fields cannot be blank", e.getMessage()); 
		}
		assertNull(libHour); 
	}
	
	@Test // modify a library hour on a day that doesn't have one
	public void testModifyLibraryHoursInvalidDay() {
		LibraryHour libraryHour = null; 

		try {
			libraryHour = libraryHourService.modifyLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK_2, START_TIME_2, END_TIME_2); 
			fail();
		} catch (Exception e) {
			assertEquals("There's no library hour for that day to modify. Create one instead.", e.getMessage()); 
		}
		
		assertNull(libraryHour); 
	}
	
	@Test // modify a library hour with new start time after new end time
	public void testModifyLibraryHoursInvalidStartTime() {
		LibraryHour libraryHour = null; 

		try {
			libraryHour = libraryHourService.modifyLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK, END_TIME_2, START_TIME_2); 
			fail();
		} catch (Exception e) {
			assertEquals("Start time cannot be after end time", e.getMessage()); 
		}
		
		assertNull(libraryHour); 
	}
	
	@Test //  modify a library hour as a librarian
	public void testModifylibraryHourLibrarian() {
		LibraryHour libraryHour = null; 

		try {
			libraryHour = libraryHourService.modifyLibraryHour(LIBRARIAN_ID, DAY_OF_WEEK, START_TIME_2, END_TIME_2); 
			fail();
		} catch (Exception e) {
			assertEquals("Only the Head Librarian can modify library hours", e.getMessage()); 
		}
		
		assertNull(libraryHour); 
	}
	
	@Test // remove a library hour with valid parameters
	public void testRemoveLibraryHour() {
		boolean wasDeleted = false; 
		
		try {
			wasDeleted = libraryHourService.removeLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK); 
		}
		catch (Exception e) {
			fail(e.getMessage()); 
		}
		assertTrue(wasDeleted); 
	}
	
	@Test // attempt to remove a library hour that doesn't exist (DNE for short)
	public void testRemoveLibraryHourDne() {
		boolean wasDeleted = true; 
		
		try {
			wasDeleted = libraryHourService.removeLibraryHour(HEAD_LIBRARIAN_ID, DAY_OF_WEEK_2);
			fail();
		}
		catch (Exception e) {
			wasDeleted = false; 
			assertEquals("There's no library hour for that day to delete", e.getMessage());
		}
		assertFalse(wasDeleted); 
	}
	
	@Test // remove a library hour as a patron
	public void testRemoveLibraryHourPatron() {
		boolean wasDeleted = true; 
		
		try {
			wasDeleted = libraryHourService.removeLibraryHour(PATRON_ID, DAY_OF_WEEK); 
			fail(); 
		}
		catch (Exception e) {
			wasDeleted = false; 
			assertEquals("Only the Head Librarian can remove library hours", e.getMessage()); 
		}
		assertFalse(wasDeleted); 
	}
	
	@Test // remove a library hour as a Librarian
	public void testRemoveLibraryHourLibrarian() {
		boolean wasDeleted = true; 
		
		try {
			wasDeleted = libraryHourService.removeLibraryHour(LIBRARIAN_ID, DAY_OF_WEEK); 
			fail(); 
		}
		catch (Exception e) {
			wasDeleted = false; 
			assertEquals("Only the Head Librarian can remove library hours", e.getMessage()); 
		}
		assertFalse(wasDeleted); 
	}
	
	@Test // get library hour for a certain day
	public void testGetLibraryHourDay() {
		LibraryHour libraryHour = null; 
		try {
			libraryHour = libraryHourService.getLibraryHour(DAY_OF_WEEK); 
		}
		catch(Exception e) {
			fail(e.getMessage()); 
		}
		assertNotNull(libraryHour); 
		assertEquals(START_TIME, libraryHour.getStartTime()); 
		assertEquals(END_TIME, libraryHour.getEndTime()); 
		assertEquals(DAY_OF_WEEK, libraryHour.getDayOfWeek()); 
	}
	
	@Test // get all libraryHours
	public void testGetAllLibraryHours() {
		List<LibraryHour> libraryHours = new ArrayList<LibraryHour>(); 
		try {
			libraryHours = libraryHourService.getAllLibraryHours(); 
		}
		catch(Exception e) {
			fail(e.getMessage()); 
		}
		assertFalse(libraryHours.size()==0); 
		assertEquals(START_TIME, libraryHours.get(0).getStartTime()); 
		assertEquals(END_TIME, libraryHours.get(0).getEndTime()); 
		assertEquals(DAY_OF_WEEK, libraryHours.get(0).getDayOfWeek()); 
		
		assertEquals(START_TIME_1, libraryHours.get(1).getStartTime()); 
		assertEquals(END_TIME_1, libraryHours.get(1).getEndTime()); 
		assertEquals(DAY_OF_WEEK_1, libraryHours.get(1).getDayOfWeek()); 
	}
	
}
