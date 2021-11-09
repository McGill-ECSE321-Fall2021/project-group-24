package ca.mcgill.ecse321.librarysystem.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import ca.mcgill.ecse321.librarysystem.dto.LibrarianDto;
import ca.mcgill.ecse321.librarysystem.dto.RoomDto;
import ca.mcgill.ecse321.librarysystem.model.Room;
import ca.mcgill.ecse321.librarysystem.service.LibraryHourService;
import ca.mcgill.ecse321.librarysystem.service.RoomService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/rooms")
public class RoomController {
	@Autowired
	private RoomService roomService;
	
	@GetMapping(value = { "/view_all_rooms", "/view_all_rooms/" })
	public List<RoomDto> getAllRooms() {
		System.out.println("Flag Get"); 
		return roomService.getAllRooms().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/view_rooms/{roomNumber}", "/view_rooms/{roomNumber}/" })
	public RoomDto getRoom(@PathVariable("roomNumber") String roomNumber) {
		System.out.println("Flag Get" + roomNumber); 
		return convertToDto(roomService.getRoom(roomNumber));
	}
	
	// TODO only let head librarian create rooms
	@PostMapping(value = { "/create_rooms/{roomNumber}", "/create_rooms/{roomNumber}/" })
	public RoomDto createRoom(@PathVariable("roomNumber") String roomNumber,
			@RequestParam Integer capacity) {
		System.out.print("Flag post");
		Room room = roomService.createRoom(roomNumber, capacity);
		System.out.print(room.getRoomNum());
		return convertToDto(room);
	}
	
	
	// TODO add method for modify rooms
	// only let head librarian modify rooms
	
	// TODO add method for delete rooms
	// only let head librarian delete rooms
	
	private RoomDto convertToDto(Room room){
		RoomDto roomDto = new RoomDto(room.getRoomNum(), room.getCapacity());
		System.out.println(roomDto.getRoomNum());
		return roomDto;
	}
	
}
