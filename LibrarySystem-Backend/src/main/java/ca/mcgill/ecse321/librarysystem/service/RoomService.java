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
	public Room createRoom(String idNum) {
		Room room = new Room(); 
		room.setRoomNum("roomNum");
	    room.setCapacity(0);
	    
		roomRepository.save(room); 
		return room;
	}
	
	// looks for a room with the given ID number, returns them if found
	@Transactional 
	public Room getRoom(String roomNum) {
		Room room = roomRepository.findRoomByRoomNum(roomNum); 
		return room;
	}
	
	@Transactional 
	public List<Room> getAllRoom() {
		return toList(roomRepository.findAll()); 
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
