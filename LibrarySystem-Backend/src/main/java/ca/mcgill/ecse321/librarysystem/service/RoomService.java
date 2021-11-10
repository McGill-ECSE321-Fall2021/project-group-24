package ca.mcgill.ecse321.librarysystem.service;
import java.util.ArrayList;
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
		// check permission: only head librarian have permission to modify the rooms
		HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(currentUserId);
	    if (
	      (currentHeadLibrarian == null || !currentHeadLibrarian.getIsLoggedIn())
	    ) {
	      throw new IllegalArgumentException(
	        "You do not have permission to create a room"
	      );
	    }
	    
	    // check if newRoomNum is available
	    for (Room room: toList(roomRepository.findAll()) )  {
	    	if ( room.getRoomNum().equalsIgnoreCase(roomNum) ) throw new IllegalArgumentException("The room number already exists");
	    }
	    
	    
		Room room = new Room();
	    room.setRoomNum(roomNum);
	    room.setCapacity(capacity);

	    roomRepository.save(room);
	    return room;		
	}
	
	// looks for a room with the given ID number, returns them if found
	@Transactional 
	public Room getRoom(String roomNum) {
		System.out.println("ROOOOM" + roomNum);
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
		// check permission: only head librarian have permission to modify the rooms
		HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(currentUserId);
	    if (
	      (currentHeadLibrarian == null || !currentHeadLibrarian.getIsLoggedIn())
	    ) {
	      throw new IllegalArgumentException(
	        "You do not have permission to update a room"
	      );
	    }
		
	    // check if newRoomNum is available
	    for (Room room: toList(roomRepository.findAll()) )  {
	    	if ( room.getRoomNum().equalsIgnoreCase(newRoomNum) ) throw new IllegalArgumentException("The new room number already exists");
	    }
		Room toUpdate = roomRepository.findRoomByRoomNum(oldRoomNum);
		toUpdate.setCapacity(newCapacity);
		toUpdate.setRoomNum(newRoomNum);
		

	    return toUpdate;		
	}
	
	// method for delete rooms
	@Transactional 
	public Room deleteRoom (String currentUserId, String roomNum) throws Exception
	{
		HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(currentUserId);
	    if (
	      (currentHeadLibrarian == null || !currentHeadLibrarian.getIsLoggedIn())
	    ) {
	      throw new IllegalArgumentException(
	        "You do not have permission to delete a room"
	      );
	    }
			    
		Room toDelete = roomRepository.findRoomByRoomNum(roomNum);
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
