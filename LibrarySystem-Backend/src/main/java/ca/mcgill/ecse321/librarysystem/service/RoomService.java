package ca.mcgill.ecse321.librarysystem.service;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;

@Service
public class RoomService {
	
	@Autowired 
	RoomRepository roomRepository; 
	
	@Autowired 
	LibrarianRepository librarianRepository;
	
	@Autowired
	HeadLibrarianRepository headLibrarianRepository;
	
	
	// creates room, returns it so we know it's not null 
	@Transactional 
	public Room createRoom(
			String currentUserId,
			String roomNum,
		    Integer capacity)  throws Exception
	{
		// check capacity 
		if (capacity<1) throw new IllegalArgumentException("Capacity must be at least 1");
		
		// check room number
		if (!inputIsValid(roomNum)) throw new IllegalArgumentException("Room number cannot be null or empty");
		
		// check permission: only librarians have permission to create the rooms
		HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(currentUserId);
		Librarian currentLibrarian = librarianRepository.findUserByIdNum(currentUserId);
	    if (
	      (currentHeadLibrarian != null && currentHeadLibrarian.getIsLoggedIn() ) || ( currentLibrarian != null && currentLibrarian.getIsLoggedIn() )
	    ) {
	    
	    }else {
	    	 throw new IllegalArgumentException(
	    		        "You do not have permission to create a room"
	    		      );
	    }
	    
	    // check if roomNum is available
	    for (Room room: toList(roomRepository.findAll()) )  {
	    	if ( room.getRoomNum().equalsIgnoreCase(roomNum) ) throw new IllegalArgumentException("The room number already exists");
	    }
	    
	    
		Room room = new Room();
		room.setRoomNum(roomNum);
		room.setCapacity(capacity);

	    roomRepository.save(room);
	    return room;		
	}
	
	// helper method: check if the input is null or empty
	private boolean inputIsValid(String string) {
		if (string == null || string.length()==0) {
			return false;
		}
		return true;
		
	}
	
	// looks for a room with the given ID number, returns them if found
	@Transactional 
	public Room getRoom(String roomNum) {
		Room room = roomRepository.findRoomByRoomNum(roomNum); 
		return room;
	}
	
	@Transactional 
	public List<Room> getAllRooms() {
		return toList(roomRepository.findAll()); 
	}

	
	// method for modify rooms
	// only head librarian have permission to modify the rooms, they can modify the room number and the capacity, but the room numbers must be unique
	@Transactional 
	public Room updateRoom (String currentUserId, String oldRoomNum, String newRoomNum, int newCapacity) throws Exception
	{
		// check capacity 
		if (newCapacity<1) throw new IllegalArgumentException("Capacity must be at least 1");
		
		// check new room number
		if (!inputIsValid(newRoomNum)) throw new IllegalArgumentException("Room number cannot be null or empty");
		
		// check permission: only librarians have permission to update the rooms
		HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(currentUserId);
		Librarian currentLibrarian = librarianRepository.findUserByIdNum(currentUserId);
	    if (
	      (currentHeadLibrarian != null && currentHeadLibrarian.getIsLoggedIn() ) || ( currentLibrarian != null && currentLibrarian.getIsLoggedIn() )
	    ) {
	    
	    }else {
	    	 throw new IllegalArgumentException(
	    		        "You do not have permission to update a room"
	    		      );
	    }
		
	    // check if newRoomNum is available
	    for (Room room: toList(roomRepository.findAll()) )  {
	    	if ( room.getRoomNum().equalsIgnoreCase(newRoomNum) ) throw new IllegalArgumentException("The new room number already exists");
	    }
	    
	    
		Room toUpdate = roomRepository.findRoomByRoomNum(oldRoomNum);
		if (toUpdate == null) throw new IllegalArgumentException("Old room number is invalid");
		toUpdate.setCapacity(newCapacity);		
		toUpdate.setRoomNum(newRoomNum);
		

	    return toUpdate;		
	}
	
	// method for delete rooms
	@Transactional 
	public Room deleteRoom (String currentUserId, String roomNum) throws Exception
	{
		
		// check permission: only librarians have permission to delete rooms
		HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(currentUserId);
		Librarian currentLibrarian = librarianRepository.findUserByIdNum(currentUserId);
	    if (
	      (currentHeadLibrarian != null && currentHeadLibrarian.getIsLoggedIn() ) || ( currentLibrarian != null && currentLibrarian.getIsLoggedIn() )
	    ) {
	    
	    }else {
	    	 throw new IllegalArgumentException(
	    		        "You do not have permission to delete a room"
	    		      );
	    }
			    
		Room toDelete = roomRepository.findRoomByRoomNum(roomNum);
		
		// check if there is future room bookings, if there is, you cannot delete the room
		for (RoomBooking rb : toList(toDelete.getRoomBookings())) {
			if (rb.getDate().equals(Date.valueOf(LocalDate.now())) && rb.getEndTime().after(Time.valueOf(LocalTime.now())) || rb.getDate().after(Date.valueOf(LocalDate.now()))) throw new IllegalArgumentException("The room number has bookings in the future, cannot delete");
	
		}
		if (toDelete == null) throw new IllegalArgumentException("The room number is invalid");
		roomRepository.delete(toDelete);
	    return toDelete;		
	}
	
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	
}
