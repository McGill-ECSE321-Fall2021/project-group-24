package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestRoomPersistence {
	  
	  @Autowired
	  private RoomRepository roomRepository;
	  
	    @BeforeEach
	    public void clearDatabase() {
	      // First, we clear registrations to avoid exceptions due to inconsistencies
//	      librarySystemRepository.deleteAll();
	      // Then we can clear the other tables

	      roomRepository.deleteAll();
	    }
	    //------------------TESTING ROOM------------------------//
	    @Test
	    public void testPersistAndLoadRoom() {
	      // First example for object save/load
	      Room room = new Room();
	      // First example for attribute save/load
	      String roomNum = "Room Number";
	      int capacity = 69;
	  
	      room.setRoomNum(roomNum);
	      room.setCapacity(capacity);
	  
	      roomRepository.save(room);
	  
	      room = null;
	  
	      room = roomRepository.findRoomByRoomNum(roomNum);
	  
	      assertNotNull(room);
	  
	      assertEquals(roomNum, room.getRoomNum());
	      assertEquals(capacity, room.getCapacity());
	    }



}
