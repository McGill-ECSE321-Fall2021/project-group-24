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

public class TestShiftService {
	@Mock
	private ShiftRepository shiftRepo; 
	@Mock
	private LibrarianRepository librarianRepo;
	@Mock
	private HeadLibrarianRepository headLibrarianRepo; 
	@Mock
	private PatronRepository patronRepo; 
	
	
	@InjectMocks
	private ShiftService shiftService; 
	
	
	
	private static final String HEAD_LIBRARIAN_ID = "admin"; 
	private static final String LIBRARIAN_ID = "librarianId"; 
	private static final String LIBRARIAN_ID2 = "librarianId2"; 

	private static final String PATRON_ID = "patronId"; 
	
	private static final String TIME_SLOT_ID = "MONDAYlibrarianId9:00:0018:00:000"; 
	private static final String TIME_SLOT_ID_2 = "timeSlotId2"; 
	private static final String TIME_SLOT_ID_3 = "timeSlotId3"; 

	
	private static final TimeSlot.DayOfWeek DAY_OF_WEEK = TimeSlot.DayOfWeek.MONDAY;  
	private static final TimeSlot.DayOfWeek DAY_OF_WEEK_2 = TimeSlot.DayOfWeek.TUESDAY;  
	private static final TimeSlot.DayOfWeek DAY_OF_WEEK_3 = TimeSlot.DayOfWeek.WEDNESDAY;  

	
	private static final Time START_TIME = Time.valueOf("9:00:00");  
	private static final Time END_TIME = Time.valueOf("17:00:00");  
	
	private static final Time START_TIME_2 = Time.valueOf("10:00:00");  
	private static final Time END_TIME_2 = Time.valueOf("19:00:00");  
	
	private static final Time START_TIME_3 = Time.valueOf("13:00:00");  
	private static final Time END_TIME_3 = Time.valueOf("22:00:00");  
	

	// adds a library hour, a librarian, and a head librarian and saves them to their respective repo's 
	@BeforeEach
	  public void setMockOutput() {
	    lenient() 
	      .when(shiftRepo.findAll())
	      .thenAnswer((InvocationOnMock invocation) -> {
	    	 List<Shift> shifts = new ArrayList<Shift>();
	    	 
		     Shift shift = new Shift(); 
		     shift.setDayOfWeek(DAY_OF_WEEK); 
		     shift.setStartTime(START_TIME);
		     shift.setEndTime(END_TIME);
		     shift.setLibrarianId(LIBRARIAN_ID); 
		     shift.setTimeSlotId(TIME_SLOT_ID);
		     
		     Shift shift2 = new Shift(); 
		     shift2.setDayOfWeek(DAY_OF_WEEK_2);
		     shift2.setStartTime(START_TIME_2);
		     shift2.setEndTime(END_TIME_2);
		     shift2.setLibrarianId(LIBRARIAN_ID2);
		     shift2.setTimeSlotId(TIME_SLOT_ID_2);
		     
		     shifts.add(shift);
		     shifts.add(shift2);
		     return shifts;
		     
	      });
	    
	    lenient() 
	      .when(shiftRepo.findShiftByTimeSlotId(TIME_SLOT_ID))
	      .thenAnswer((InvocationOnMock invocation) -> {	    	 
		     Shift shift = new Shift(); 
		     shift.setDayOfWeek(DAY_OF_WEEK); 
		     shift.setStartTime(START_TIME);
		     shift.setEndTime(END_TIME);
		     shift.setLibrarianId(LIBRARIAN_ID); 
		     shift.setTimeSlotId(TIME_SLOT_ID);
		     
		     return shift;
		     
	      });
	    
	    lenient() 
	      .when(shiftRepo.findShiftByLibrarianIdAndDayOfWeek(LIBRARIAN_ID, DAY_OF_WEEK))
	      .thenAnswer((InvocationOnMock invocation) -> {	    	 
		     Shift shift = new Shift(); 
		     shift.setDayOfWeek(DAY_OF_WEEK); 
		     shift.setStartTime(START_TIME);
		     shift.setEndTime(END_TIME);
		     shift.setLibrarianId(LIBRARIAN_ID); 
		     shift.setTimeSlotId(TIME_SLOT_ID);
		     
		     return shift;
		     
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
	      .when(shiftRepo.save(any(Shift.class)))
	      .thenAnswer(returnParameterAsAnswer);
	    
	}
	
	
    @Test // Creates a shift with valid inputs
    public void testCreateShift() {
    	Shift shift = null; 
    	try {
    		shift = shiftService.createShift(HEAD_LIBRARIAN_ID, LIBRARIAN_ID, DAY_OF_WEEK_3, START_TIME_3, END_TIME_3);
    	}
    	catch (Exception e) {
    		fail(e.getMessage());
    	}
    	assertNotNull(shift);
    	assertEquals(LIBRARIAN_ID, shift.getLibrarianId());
    	assertEquals(DAY_OF_WEEK_3, shift.getDayOfWeek());
    	assertEquals(START_TIME_3, shift.getStartTime());
    	assertEquals(END_TIME_3, shift.getEndTime()); 
    	
    }
    
    @Test // Attempts to create shift as librarian
    public void testCreateShiftAsLibrarian() {
    	Shift shift = null; 
    	try {
    		shift = shiftService.createShift(LIBRARIAN_ID, HEAD_LIBRARIAN_ID, DAY_OF_WEEK_3, START_TIME_3, END_TIME_3);
    		fail();
    	}
    	catch (Exception e) {
    		assertEquals("Only the Head Librarian can create librarian shifts", e.getMessage());
    	}
    	assertNull(shift);
    }
    
    @Test // Attempts to create shift for a librarian that doesn't exist
    public void testCreateShiftForFakeLibrarian() {
    	Shift shift = null; 
    	try {
    		shift = shiftService.createShift(HEAD_LIBRARIAN_ID, "boogeyman", DAY_OF_WEEK_3, START_TIME_3, END_TIME_3);
    		fail();
    	}
    	catch (Exception e) {
    		assertEquals("No librarian with this ID exists", e.getMessage());
    	}
    	assertNull(shift);
    }
	
    @Test // Attempts to create shift with start time after end time
    public void testCreateShiftInvalidStartTime() {
    	Shift shift = null; 
    	try {
    		shift = shiftService.createShift(HEAD_LIBRARIAN_ID, LIBRARIAN_ID, DAY_OF_WEEK_3, END_TIME_3, START_TIME_3);
    		fail();
    	}
    	catch (Exception e) {
    		assertEquals("Shift end time cannot be before its start time", e.getMessage());
    	}
    	assertNull(shift);
    }
    
    @Test // Attempts to create shift that would cause librarian to have overlapping shifts
    public void testCreateShiftOverlappingShifts() {
    	Shift shift = null; 
    	try {
    		shift = shiftService.createShift(HEAD_LIBRARIAN_ID, LIBRARIAN_ID, DAY_OF_WEEK, START_TIME_2, END_TIME_2);
    		fail();
    	}
    	catch (Exception e) {
    		assertEquals("Librarian cannot have overlapping shifts", e.getMessage());
    	}
    	assertNull(shift);
    }
    
    @Test // Modifies a shift with valid inputs
    public void testModifyShift() {
    	Shift shift = null; 
    	try {
    		shift = shiftService.modifyShift(HEAD_LIBRARIAN_ID, TIME_SLOT_ID, LIBRARIAN_ID, DAY_OF_WEEK_2, START_TIME_2, END_TIME_2);
    	}
    	catch (Exception e) {
    		fail(e.getMessage());
    	}
    	assertNotNull(shift);
    	assertEquals(LIBRARIAN_ID, shift.getLibrarianId());
    	assertEquals(DAY_OF_WEEK_2, shift.getDayOfWeek());
    	assertEquals(START_TIME_2, shift.getStartTime());
    	assertEquals(END_TIME_2, shift.getEndTime()); 
    }
    
    @Test // Attempts to modify a shift as librarian
    public void testModifyShiftAsLibrarian() {
    	Shift shift = null; 
    	try {
    		shift = shiftService.modifyShift(LIBRARIAN_ID, TIME_SLOT_ID, LIBRARIAN_ID, DAY_OF_WEEK_2, START_TIME_2, END_TIME_2);
    		fail();
    	}
    	catch (Exception e) {
    		assertEquals("Only the Head Librarian can modify librarian shifts", e.getMessage());
    	}
    	assertNull(shift);
    }
    
    @Test // Attempts to modify shift for a librarian that doesn't exist
    public void testModifyShiftForFakeLibrarian() {
    	Shift shift = null; 
    	try {
    		shift = shiftService.modifyShift(HEAD_LIBRARIAN_ID, TIME_SLOT_ID, "bogus", DAY_OF_WEEK_2, START_TIME_2, END_TIME_2);
    		fail();
    	}
    	catch (Exception e) {
    		assertEquals("No librarian with this ID exists", e.getMessage());
    	}
    	assertNull(shift);
    }
    
    @Test // Attempts to modify shift with start time after end time
    public void testModifyShiftInvalidStartTime() {
    	Shift shift = null; 
    	try {
    		shift = shiftService.modifyShift(HEAD_LIBRARIAN_ID, TIME_SLOT_ID, LIBRARIAN_ID, DAY_OF_WEEK_2, END_TIME_2, START_TIME_2);
    		fail();
    	}
    	catch (Exception e) {
    		assertEquals("Shift end time cannot be before its start time", e.getMessage());
    	}
    	assertNull(shift);
    }
    
    @Test // Attempts to modify shift that does not exist (DNE)
    public void testModifyShiftThatDne() {
    	Shift shift = null; 
    	try {
    		shift = shiftService.modifyShift(HEAD_LIBRARIAN_ID, "BEWARE OF MY WRATH", LIBRARIAN_ID, DAY_OF_WEEK_2, START_TIME_2, END_TIME_2);
    		fail();
    	}
    	catch (Exception e) {
    		assertEquals("Old shift does not exist so cannot modify", e.getMessage());
    	}
    	assertNull(shift);
    }
    
    @Test // Removes a shift with valid inputs
    public void testRemoveShift() {
    	boolean wasDeleted = false; 
    	try {
    		wasDeleted = shiftService.removeShift(HEAD_LIBRARIAN_ID, TIME_SLOT_ID);
    	}
    	catch (Exception e) {
    		fail(e.getMessage());
    	}

    	assertTrue(wasDeleted);
    }
    
    @Test // Attempts to remove a shift as librarian
    public void testRemoveShiftAsLibrarian() {
    	boolean wasDeleted = false;
    	try {
    		wasDeleted = shiftService.removeShift(LIBRARIAN_ID, TIME_SLOT_ID);
    		fail();
    	}
    	catch (Exception e) {
    		assertEquals("Only the Head Librarian can remove librarian shifts", e.getMessage());
    	}
    	assertFalse(wasDeleted);
    }
		
}
