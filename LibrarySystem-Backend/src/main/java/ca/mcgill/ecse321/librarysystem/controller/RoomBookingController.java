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
public class RoomBookingController {
	@Autowired
	private RoomBookingService roomBookingService;
	
	@GetMapping(value = { "/roomBookings", "/roomBookings/" })
	public List<RoomBookingDto> getRoomBooking() {
		System.out.println("Flag Get"); 
		return roomBookingService.getAllRoomBookings().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/roomBookings/patron/{idNum}", "/roomBookings/patron/{idNum}/" })
	public List<RoomBookingDto> getRoomBookingOfPatron(@PathVariable("idNum") String idNum) {
		return getRoomBookingDtosForPatron(idNum);
	}
	
	@GetMapping(value = { "/roomBookings/room/{roomNum}", "/roomBookings/room/{roomNum}/" })
	public List<RoomBookingDto> getRoomBookingOfRoom(@PathVariable("roomNum") String roomNum) {
		return getRoomBookingDtosForRoom(roomNum);
	}
	
	@GetMapping(value = { "/roomBookings/{timeSlotId}", "/roomBookings/{timeSlotId}/" })
	public RoomBookingDto getRoomBookingOfTimeSlot(@PathVariable("timeSlotId") String timeSlotId) {
		System.out.println("Flag Get" + timeSlotId); 
		return convertToDto(roomBookingService.getRoomBookingsByTimeSlotId(timeSlotId));
	}
	
//	@PostMapping(value = {"/roomBookings/returnItem/{itemNumber}", "roomBookings/returnItem/{itemNumber}/"})
//	public RoomBookingService returnItem(@PathVariable("itemNumber") String itemNumber) {
//		return convertToDto(roomBookingService.returnItemFromReservation(itemNumber));
//		
//	}

	@PostMapping(value = { "/roomBookings", "/roomBookings/" })
	public RoomBookingDto createRoomBooking(
			 @RequestParam String startDate,
			 @RequestParam String startTime,
			 @RequestParam String endDate,
			 @RequestParam String endTime,
			 @RequestParam String idNum,
			 @RequestParam String roomNum
			) {
		String timeSlotId = "RoomBooking-"+roomBookingService.getAllRoomBookings().size()+startTime+roomNum;
		System.out.println("Flag Post"); 
		RoomBooking booking = roomBookingService.createRoomBooking(
				timeSlotId,
				Date.valueOf(LocalDate.parse(startDate)),
				Time.valueOf(LocalTime.parse(startTime)),
				Time.valueOf(LocalTime.parse(endTime)),
				idNum,
				roomNum
		     );
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
