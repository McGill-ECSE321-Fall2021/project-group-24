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
public class TestRoomBookingPersistence {
	  
	  @Autowired
	  private RoomBookingRepository roomBookingRepository;
	  
	    @BeforeEach
	    public void clearDatabase() {
	      // First, we clear registrations to avoid exceptions due to inconsistencies
//	      librarySystemRepository.deleteAll();
	      // Then we can clear the other tables

	    	roomBookingRepository.deleteAll();
	    }
	    //------------------TESTING RoomBooking------------------------//
	      @Test
	      public void testPersistAndLoadRoomBooking() {

	        RoomBooking roomBooking = new RoomBooking();
	        
	        //set shift attributes
	        String roomNum = "Room Number";
	        Time roomBookingTime = Time.valueOf("00:00:00");
	        String roomBookingId = "room booking id";
	        
	        roomBooking.setRoomNum(roomNum);
	        roomBooking.setTimeSlotId(roomBookingId);
	        roomBooking.setDayOfWeek(TimeSlot.DayOfWeek.Monday);
	        roomBooking.setStartTime(roomBookingTime);
	        roomBooking.setEndTime(roomBookingTime);

	       
	        roomBookingRepository.save(roomBooking);

	        roomBooking = null;

	        roomBooking = roomBookingRepository.findRoomBookingByTimeSlotId(roomBookingId);

	        assertNotNull(roomBooking);

	        assertEquals(roomNum, roomBooking.getRoomNum());
	        assertEquals(roomBookingId, roomBooking.getTimeSlotId());
	        assertEquals(TimeSlot.DayOfWeek.Monday, roomBooking.getDayOfWeek());
	        assertEquals(roomBookingTime, roomBooking.getStartTime());
	        assertEquals(roomBookingTime, roomBooking.getEndTime());

	  }



}
