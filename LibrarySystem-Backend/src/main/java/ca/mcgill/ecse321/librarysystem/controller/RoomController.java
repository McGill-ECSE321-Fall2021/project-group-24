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
	
	/** Method create room using room number
	 * @author Selena 
	 * @paramroomNumber, currentUserId, capacity
	 * @return room if successfully created
	 * 
	 * only librarians have permission to update room
	 */
	@PostMapping(value = { "/create_rooms/{roomNumber}", "/create_rooms/{roomNumber}/" })
	public RoomDto createRoom(@PathVariable("roomNumber") String roomNumber,
			@RequestParam String currentUserId,
			@RequestParam Integer capacity) {
		System.out.print("Flag post");
		Room room = null;
		try {
			room = roomService.createRoom(currentUserId, roomNumber, capacity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print(room.getRoomNum());
		return convertToDto(room);
	}
	
	
	/** Method update room using room number
	 * @author Selena 
	 * @param oldRoomNumber, currentUserId, capacity, newRoomNum
	 * @return room if successfully deleted
	 * 
	 * only librarians have permission to update room
	 */
	@PutMapping(value = { "/update_rooms/{oldRoomNumber}", "/update_rooms/{oldRoomNumber}/" })
	public RoomDto updateRoom(@PathVariable("oldRoomNumber") String oldRoomNumber,
			@RequestParam String currentUserId,
			@RequestParam Integer capacity,
			@RequestParam String newRoomNum) {
		
		System.out.print("Flag put");
		Room room = null;
		try {
			room = roomService.updateRoom(currentUserId, oldRoomNumber, newRoomNum, capacity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print(room.getRoomNum());
		return convertToDto(room);
	}
	
	/** Method update room using room number
	 * @author Selena 
	 * @param oldRoomNumber, currentUserId, capacity, newRoomNum
	 * @return room if successfully deleted
	 * 
	 * only librarians have permission to update room
	 */
	@DeleteMapping(value = { "/delete_rooms/{roomNum}", "/delete_rooms/{roomNum}/" })
	public RoomDto deleteRoom(@PathVariable("oldRoomNumber") String roomNum,
			@RequestParam String currentUserId) {
		System.out.print("Flag delete");
		Room room = null;
		try {
			room = roomService.deleteRoom(currentUserId, roomNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print(room.getRoomNum());
		return convertToDto(room);
	}
	
	private RoomDto convertToDto(Room room){
		RoomDto roomDto = new RoomDto(room.getRoomNum(), room.getCapacity());
		System.out.println(roomDto.getRoomNum());
		return roomDto;
	}
	
}
