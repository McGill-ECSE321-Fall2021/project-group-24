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
	
	@GetMapping(value = { "/itemReservations/patron/{idNum}", "/registrations/patron/{idNum}/" })
	public List<ItemReservationDto> getItemReservationsOfPatron(@PathVariable("idNum") String idNum) {
		return getItemReservationDtosForPatron(idNum);
	}
	
	@GetMapping(value = { "/itemReservations/item/{itemNumber}", "/registrations/item/{itemNumber}/" })
	public List<ItemReservationDto> getItemReservationsOfItem(@PathVariable("itemNumber") String itemNumber) {
		return getItemReservationDtosForItem(itemNumber);
	}
	
	@GetMapping(value = { "/itemReservations/{timeSlotId}", "/itemReservations/{timeSlotId}/" })
	public ItemReservationDto getItemReservation(@PathVariable("timeSlotId") String timeSlotId) {
		System.out.println("Flag Get" + timeSlotId); 
		return convertToDto(itemReservationService.getItemReservation(timeSlotId));
	}
	
	@PostMapping(value = {"/itemReservations/returnItem/{itemNumber}", "itemReservations/returnItem/{itemNumber}/"})
	public ItemReservationDto returnItem(@PathVariable("itemNumber") String itemNumber) {
		return convertToDto(itemReservationService.returnItemFromReservation(itemNumber));
		
	}
	
	@PostMapping(value = {"/itemReservations/checkoutItem/{itemNumber}/byPatron/{idNum}", "itemReservations/checkoutItem/{itemNumber}/byPatron/{idNum}"})
	public ItemReservationDto checkoutItem(@PathVariable("itemNumber") String itemNumber, @PathVariable("idNum") String idNum) {
		return convertToDto(itemReservationService.checkoutItem(itemNumber, idNum));
		
	}

	@PostMapping(value = { "/itemReservations/{timeSlotId}", "/itemReservations/{timeSlotId}/" })
	public ItemReservationDto createItemReservation(@PathVariable("timeSlotId") String timeSlotId,
			 @RequestParam Integer numOfRenewalsLeft,
			 @RequestParam String idNum,
			 @RequestParam String itemNumber,
			 @RequestParam boolean isCheckedOut
			) {
		System.out.println("Flag Post"); 
		Date startDate = itemReservationService.findNextAvailabilityForItem(itemNumber);
		//if they are at the library rn and the book is available 
		if (isCheckedOut && startDate.equals(Date.valueOf(LocalDate.now().plusDays(1))) ) {
			startDate = Date.valueOf(LocalDate.now());
		//if they are at the library rn but the book should not be available
		} else if (isCheckedOut) {
			throw new IllegalArgumentException("Book has a future reservation");
		}
		Date endDate = Date.valueOf(startDate.toLocalDate().plusWeeks(2));
		try {
			ItemReservation reservation = itemReservationService.createItemReservation(timeSlotId,
					startDate, Time.valueOf(LocalTime.parse("00:00")), endDate, Time.valueOf(LocalTime.parse("23:59")), idNum, itemNumber, numOfRenewalsLeft, isCheckedOut
			     );
			return convertToDto(reservation);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@PostMapping(value = { "/itemReservations/customDate/{timeSlotId}", "/itemReservations/customDate/{timeSlotId}/" })
	public ItemReservationDto createItemReservationCustomDates(@PathVariable("timeSlotId") String timeSlotId,
			 @RequestParam Integer numOfRenewalsLeft,
			 @RequestParam String idNum,
			 @RequestParam String itemNumber,
			 @RequestParam boolean isCheckedOut, @RequestParam String startDate
			) {
		System.out.println("Flag Post"); 
		Date endDate = Date.valueOf(LocalDate.parse(startDate).plusWeeks(2));
		try {
			ItemReservation reservation = itemReservationService.createItemReservation(timeSlotId,
					Date.valueOf(LocalDate.parse(startDate)), Time.valueOf(LocalTime.parse("00:00")), endDate, Time.valueOf(LocalTime.parse("23:59")), idNum, itemNumber, numOfRenewalsLeft, isCheckedOut
			     );
			return convertToDto(reservation);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@PostMapping(value = {"/itemReservations/renew/{timeSlotId}", "/itemReservations/renew/{timeSlotId}/"})
	public ItemReservationDto renewItemReservation(@PathVariable("timeSlotId") String timeSlotId) {
		return convertToDto(itemReservationService.renewByTimeSlotId(timeSlotId));
	}
	
	@PostMapping(value = {"/itemReservations/cancel/{timeSlotId}", "/itemReservations/cancel/{timeSlotId}/"})
	public boolean cancelItemReservation(@PathVariable("timeSlotId") String timeSlotId) {
		return itemReservationService.cancelItemReservation(timeSlotId);
	}
	
	private List<ItemReservationDto> getItemReservationDtosForPatron(String idNum) {
		List<ItemReservation> itemReservationsForPatron = itemReservationService.getItemReservationsByIdNum(idNum);
		List<ItemReservationDto> reservations = new ArrayList<>();
		for (ItemReservation reservation : itemReservationsForPatron) {
			reservations.add(convertToDto(reservation));
		}
		return reservations;
	}
	
	private List<ItemReservationDto> getItemReservationDtosForItem(String itemNumber) {
		List<ItemReservation> itemReservationsForItem = itemReservationService.getItemReservationsByItemNumber(itemNumber);
		List<ItemReservationDto> reservations = new ArrayList<>();
		for (ItemReservation reservation : itemReservationsForItem) {
			reservations.add(convertToDto(reservation));
		}
		return reservations;
	}
	
	
	private ItemReservationDto convertToDto(ItemReservation reservation){
		ItemReservationDto reservationDto = new ItemReservationDto(reservation.getTimeSlotId(), reservation.getStartDate(), reservation.getStartTime(), reservation.getEndDate(), reservation.getEndTime(), reservation.getNumOfRenewalsLeft(), reservation.getIdNum(), reservation.getItemNumber(), reservation.getIsCheckedOut());
	
		return reservationDto;
	}
}
