package ca.mcgill.ecse321.librarysystem.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


import ca.mcgill.ecse321.librarysystem.LibrarySystemApplication;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.service.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/roombookings")
public class RoomBookingController {
	@Autowired
	private RoomBookingService roomBookingService;
	
	/** Method get all roombookings
	 * @author Selena 
	 * @return all roombookings
	 */
	@GetMapping(value = { "/view_roombookings", "/view_roombookings/" })
	public List<RoomBookingDto> getRoomBooking() {
		System.out.println("Flag Get"); 
		return roomBookingService.getAllRoomBookings().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}
	
	/** Method get all roombookings of a patron provided idNum
	 * @author Selena 
	 * @param idNum
	 * @return all roombookings of a patron
	 */
	@GetMapping(value = { "/view_roombookings/patron/{idNum}", "/view_roombookings/patron/{idNum}/" })
	public List<RoomBookingDto> getRoomBookingOfPatron(@PathVariable("idNum") String idNum) {
		return getRoomBookingDtosForPatron(idNum);
	}
	
	/** Method get all roombookings of a room
	 * @author Selena 
	 * @param roomNum
	 * @return all roombookings of a room
	 */
	@GetMapping(value = { "/view_roombookings/room/{roomNum}", "/view_roombookings/room/{roomNum}/" })
	public List<RoomBookingDto> getRoomBookingOfRoom(@PathVariable("roomNum") String roomNum) {
		return getRoomBookingDtosForRoom(roomNum);
	}
	
	/** Method get the roombooking with timeSlotId
	 * @author Selena 
	 * @param timeSlotId
	 * @return roombooking with timeSlotId
	 */
	@GetMapping(value = { "/view_roombookings/{timeSlotId}", "/view_roombookings/{timeSlotId}/" })
	public RoomBookingDto getRoomBookingOfTimeSlot(@PathVariable("timeSlotId") String timeSlotId) {
		System.out.println("Flag Get" + timeSlotId); 
		return convertToDto(roomBookingService.getRoomBookingsByTimeSlotId(timeSlotId));
	}
	
	/** Method create roombooking, timeSlotId is automatically generated
	 * @author Selena 
	 * @param currentUserId, date, startTime, endTime, idNum, roomNum
	 * @return roombooking if successfully created
	 * 
	 * librarian are allowed to create roombookings for a patron, patron can only create roombookings for themselves
	 */
	@PostMapping(value = { "/add_roombookings", "/add_roombookings/" })
	public RoomBookingDto createRoomBooking(
			 @RequestParam String currentUserId,
			 @RequestParam String date,
			 @RequestParam String startTime,
			 @RequestParam String endTime,
			 @RequestParam String idNum,
			 @RequestParam String roomNum
			) {
		String timeSlotId = "RoomBooking-"+roomBookingService.getAllRoomBookings().size()+startTime+roomNum;
		System.out.println("Flag Post"); 
		RoomBooking booking = roomBookingService.createRoomBooking(
				currentUserId,
				timeSlotId,
				Date.valueOf(LocalDate.parse(date)),
				Time.valueOf(LocalTime.parse(startTime)),
				Time.valueOf(LocalTime.parse(endTime)),
				idNum,
				roomNum
		     );
		return convertToDto(booking);
		
	}
	/** Method update roombooking using timeSlotId
	 * @author Selena 
	 * @param currentUserId, date, startTime, endTime, idNum, roomNum
	 * @return roombooking if successfully updated
	 * 
	 * librarian are allowed to update any roombooking, patron can only update their own roombookings
	 */
	@PutMapping(value = { "/update_roombookings", "/update_roombookings/" })
	public RoomBookingDto updateRoomBooking(
			 @RequestParam String currentUserId,
			 @RequestParam String timeSlotId,
			 @RequestParam String date,
			 @RequestParam String startTime,
			 @RequestParam String endTime,
			 @RequestParam String idNum,
			 @RequestParam String roomNum
			) {
		System.out.println("Flag Put"); 
		RoomBooking booking = null;
		try {
			booking = roomBookingService.updateRoomBooking(
					currentUserId,
					timeSlotId,
					Date.valueOf(LocalDate.parse(date)),
					Time.valueOf(LocalTime.parse(startTime)),
					Time.valueOf(LocalTime.parse(endTime)),
					roomNum
			     );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertToDto(booking);
		
	}
	
	/** Method delete roombooking using timeSlotId
	 * @author Selena 
	 * @param currentUserId, timeSlotId
	 * @return roombooking if successfully deleted
	 * 
	 * librarian are allowed to delete any roombooking, patron can only delete their own roombookings
	 */
	@DeleteMapping(value = { "/delete_roombookings", "/delete_roombookings/" })
	public RoomBookingDto deleteRoomBooking(
			@RequestParam String currentUserId, 
			@RequestParam String timeSlotId) {
		RoomBooking booking = null;
		try {
			booking = roomBookingService.deleteRoomBooking(
					currentUserId,
					timeSlotId
			     );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertToDto(booking);
	}
	
	private List<RoomBookingDto> getRoomBookingDtosForPatron(String idNum) {
		List<RoomBooking> roomBookingForPatron = roomBookingService.getRoomBookingsByIdNum(idNum);
		List<RoomBookingDto> bookings = new ArrayList<>();
		for (RoomBooking booking : roomBookingForPatron) {
			bookings.add(convertToDto(booking));
		}
		return bookings;
	}
	
	private List<RoomBookingDto> getRoomBookingDtosForRoom(String roomNum) {
		List<RoomBooking> roomBookingsForRoomNum = roomBookingService.getRoomBookingsByRoomNum(roomNum);
		List<RoomBookingDto> roomBooking = new ArrayList<>();
		for (RoomBooking booking : roomBookingsForRoomNum) {
			roomBooking.add(convertToDto(booking));
		}
		return roomBooking;
	}
	
	
	private RoomBookingDto convertToDto(RoomBooking roomBooking){
		RoomBookingDto roomBookingDto = new RoomBookingDto(roomBooking.getTimeSlotId(), roomBooking.getDate(), roomBooking.getStartTime(), roomBooking.getEndTime(), roomBooking.getIdNum(), roomBooking.getRoomNum());
		return roomBookingDto;
	}
}
