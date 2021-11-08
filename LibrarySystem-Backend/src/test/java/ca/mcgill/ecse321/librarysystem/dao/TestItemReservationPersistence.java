package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestItemReservationPersistence {
	  
	  @Autowired
	  private ItemReservationRepository itemReservationRepository;
	  
	    @BeforeEach
	    public void clearDatabase() {
	      // First, we clear registrations to avoid exceptions due to inconsistencies
//	      librarySystemRepository.deleteAll();
	      // Then we can clear the other tables

	      itemReservationRepository.deleteAll();
	    }
	    //------------------TESTING ITEM RESERVATION------------------------//
	    @Test
	    public void testPersistAndLoadItemReservation() {
	      // First example for object save/load
	      ItemReservation itemReservation = new ItemReservation();
	      // First example for attribute save/load

	      String idNum = "Item Reservation ID Number";
	      String itemNumber = "Item Reservation Item Number";
	      String timeSlotId = "Item Reservation Time Slot ID";
	      Date startDate = Date.valueOf("2021-10-15");
	      Date endDate = Date.valueOf("2021-10-15");
	      
	      
	      
	      itemReservation.setIdNum(idNum);
	      itemReservation.setItemNumber(itemNumber);
	      itemReservation.setItemReservationId(timeSlotId);
	      itemReservation.setStartDate(startDate);
	      itemReservation.setEndDate(endDate);

	      itemReservationRepository.save(itemReservation);

	      itemReservation = null;

	      itemReservation = itemReservationRepository.findItemReservationByItemReservationId(timeSlotId);
	      assertNotNull(itemReservation);

	      assertEquals(idNum, itemReservation.getIdNum());
	      assertEquals(itemNumber, itemReservation.getItemNumber());
	      assertEquals(startDate, itemReservation.getStartDate());
	      assertEquals(endDate, itemReservation.getEndDate());

	    }



}
