package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestShiftPersistence {
	  
	  @Autowired
	  private ShiftRepository shiftRepository;
	  
	    @BeforeEach
	    public void clearDatabase() {
	      // First, we clear registrations to avoid exceptions due to inconsistencies
//	      librarySystemRepository.deleteAll();
	      // Then we can clear the other tables

	      shiftRepository.deleteAll();
	    }
	    //------------------TESTING Shift------------------------//
	    @Test
	    public void testPersistAndLoadShift() {

	      Shift shift = new Shift();
	      
	      // set shift attributes
	      Time shiftTime = Time.valueOf("00:00:00");
	      String shiftId = "shift id";
	      shift.setTimeSlotId(shiftId);
	      shift.setDayOfWeek(TimeSlot.DayOfWeek.MONDAY);
	      shift.setStartTime(shiftTime);
	      shift.setEndTime(shiftTime);

	      shiftRepository.save(shift);

	      shift = null;

	      shift = shiftRepository.findShiftByTimeSlotId(shiftId);

	      assertNotNull(shift);

	      assertEquals(shiftId, shift.getTimeSlotId());
	      assertEquals(TimeSlot.DayOfWeek.MONDAY, shift.getDayOfWeek());
	      assertEquals(shiftTime, shift.getStartTime());
	      assertEquals(shiftTime, shift.getEndTime());
	      }



}
