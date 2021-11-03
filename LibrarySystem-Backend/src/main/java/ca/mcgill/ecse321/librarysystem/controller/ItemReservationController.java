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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.librarysystem.LibrarySystemApplication;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.service.*;

@CrossOrigin(origins = "*")
@RestController
public class ItemReservationController {
	@Autowired
	private ItemReservationService itemReservationService;
	
	@GetMapping(value = { "/itemReservations", "/itemReservations/" })
	public List<ItemReservationDto> getAllItemReservations() {
		System.out.println("Flag Get"); 
		return itemReservationService.getAllItemReservations().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/itemReservations/{timeSlotId}", "/itemReservations/{timeSlotId}/" })
	public ItemReservationDto getItemReservation(@PathVariable("timeSlotId") String timeSlotId) {
		System.out.println("Flag Get" + timeSlotId); 
		return convertToDto(itemReservationService.getItemReservation(timeSlotId));
	}

	@PostMapping(value = { "/itemReservations/{timeSlotId}", "/itemReservations/{timeSlotId}/" })
	public ItemReservationDto createItemReservation(@PathVariable("timeSlotId") String timeSlotId,
			 @RequestParam String startDate,
			 @RequestParam String startTime,
			 @RequestParam String endDate,
			 @RequestParam String endTime,
			 @RequestParam Integer numOfRenewalsLeft,
			 @RequestParam String idNum,
			 @RequestParam String itemNumber
			) {
		System.out.println("Flag Post"); 
		ItemReservation reservation = itemReservationService.createItemReservation(timeSlotId,
				Date.valueOf(LocalDate.parse(startDate)), Time.valueOf(LocalTime.parse(startTime)), Date.valueOf(LocalDate.parse(endDate)), Time.valueOf(LocalTime.parse(endTime)), idNum, itemNumber, numOfRenewalsLeft
		     );
		return convertToDto(reservation);
	}
	
	private ItemReservationDto convertToDto(ItemReservation reservation){
		ItemReservationDto reservationDto = new ItemReservationDto(reservation.getTimeSlotId(), reservation.getStartDate(), reservation.getStartTime(), reservation.getEndDate(), reservation.getEndTime(), reservation.getNumOfRenewalsLeft(), reservation.getIdNum(), reservation.getItemNumber());
	
		return reservationDto;
	}
}
