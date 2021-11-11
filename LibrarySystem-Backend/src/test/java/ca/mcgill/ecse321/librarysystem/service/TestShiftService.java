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
	private static final String LIBRARIAN_ID2 = "librarianId"; 

	private static final String PATRON_ID = "patronId"; 
	
	private static final String TIME_SLOT_ID = "timeSlotId"; 
	private static final String TIME_SLOT_ID_2 = "timeSlotId2"; 

	
	private static final TimeSlot.DayOfWeek DAY_OF_WEEK = TimeSlot.DayOfWeek.MONDAY;  
	private static final TimeSlot.DayOfWeek DAY_OF_WEEK_2 = TimeSlot.DayOfWeek.TUESDAY;  
	
	private static final Time START_TIME = Time.valueOf("9:00:00");  
	private static final Time END_TIME = Time.valueOf("17:00:00");  
	
	private static final Time START_TIME_2 = Time.valueOf("10:00:00");  
	private static final Time END_TIME_2 = Time.valueOf("19:00:00");  
	

	// adds a library hour, a librarian, and a head librarian and saves them to their respective repo's 
	@BeforeEach
	  public void setMockOutput() {
	    lenient() 
	      .when(shiftRepo.findAll())
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
	
}
