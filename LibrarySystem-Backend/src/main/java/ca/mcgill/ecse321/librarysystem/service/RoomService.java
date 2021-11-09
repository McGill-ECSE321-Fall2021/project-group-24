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
			String roomNum,
		    Integer capacity) 
	{
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

	
	// TODO add method for modify rooms
	// only let head librarian modify rooms
	
	// TODO add method for delete rooms
	@Transactional 
	public Room deleteRoom (String currentUserId, String roomNum) 
	{
		Librarian currentLibrarian = librarianRepository.findUserByIdNum(
			      currentUserId
			    );
			    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
			      currentUserId
			    );
			    if (
			      currentLibrarian == null ||
			      !currentLibrarian.getIsLoggedIn() &&
			      (currentHeadLibrarian == null || !currentHeadLibrarian.getIsLoggedIn())
			    ) {
			      throw new IllegalArgumentException(
			        "You do not have permission to create an item reservation"
			      );
			    }
			    
		Room toDelete = roomRepository.findRoomByRoomNum(roomNum);
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
