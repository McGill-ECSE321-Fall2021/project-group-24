package ca.mcgill.ecse321.librarysystem.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import ca.mcgill.ecse321.librarysystem.dto.LibrarianDto;
import ca.mcgill.ecse321.librarysystem.dto.RoomDto;
import ca.mcgill.ecse321.librarysystem.model.Room;
import ca.mcgill.ecse321.librarysystem.service.RoomService;

@CrossOrigin(origins = "*")
@RestController
public class RoomController {
	@Autowired
	private RoomService roomService;
	
	@GetMapping(value = { "/rooms", "/rooms/" })
	public List<RoomDto> getAllRooms() {
		System.out.println("Flag Get"); 
		return roomService.getAllRooms().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/rooms/{roomNumber}", "/rooms/{roomNumber}/" })
	public RoomDto getRoom(@PathVariable("roomNumber") String roomNumber) {
		System.out.println("Flag Get" + roomNumber); 
		return convertToDto(roomService.getRoom(roomNumber));
	}
	
	@PostMapping(value = { "/rooms/{roomNumber}", "/rooms/{roomNumber}/" })
	public RoomDto createRoom(@PathVariable("roomNumber") String roomNumber,
			@RequestParam Integer capacity) {
		System.out.print("Flag post");
		Room room = roomService.createRoom(roomNumber, capacity);
		System.out.print(room.getRoomNum());
		return convertToDto(room);
	}
	
	private RoomDto convertToDto(Room room){
		RoomDto roomDto = new RoomDto(room.getRoomNum(), room.getCapacity());
		System.out.println(roomDto.getRoomNum());
		return roomDto;
	}
	
}
