package ca.mcgill.ecse321.librarysystem.controller;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.service.*;

@CrossOrigin(origins = "*")
@RestController
public class ItemReservationController {
	@Autowired
	private ItemReservationService itemReservationService;
	
	@GetMapping(value = { "/itemReservations", "/itemReservations/" })
	public List<ItemReservationDto> getAllItemReservations(@RequestParam String currentUserId) {
		System.out.println("Flag Get"); 
		return itemReservationService.getAllItemReservations(currentUserId).stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/itemReservations/patron/{idNum}", "/registrations/patron/{idNum}/" })
	public List<ItemReservationDto> getItemReservationsOfPatron(@PathVariable("idNum") String idNum, @RequestParam String currentUserId) {
		return getItemReservationDtosForPatron(currentUserId,idNum);
	}
	
	@GetMapping(value = { "/itemReservations/item/{itemNumber}", "/registrations/item/{itemNumber}/" })
	public List<ItemReservationDto> getItemReservationsOfItem(@PathVariable("itemNumber") String itemNumber, @RequestParam String currentUserId) {
		return getItemReservationDtosForItem(currentUserId, itemNumber);
	}
	
	@GetMapping(value = { "/itemReservations/{itemReservationId}", "/itemReservations/{itemReservationId}/" })
	public ItemReservationDto getItemReservation(@PathVariable("itemReservationId") String itemReservationId, @RequestParam String currentUserId) {
		System.out.println("Flag Get" + itemReservationId); 
		return convertToDto(itemReservationService.getItemReservation(currentUserId, itemReservationId));
	}
	
	@PostMapping(value = {"/itemReservations/return_item/{itemNumber}", "itemReservations/return_item/{itemNumber}/"})
	public ItemReservationDto returnItem(@PathVariable("itemNumber") String itemNumber, @RequestParam String currentUserId) {
		return convertToDto(itemReservationService.returnItemFromReservation(currentUserId, itemNumber));
		
	}
	
	@PostMapping(value = {"/itemReservations/checkout_item/{itemNumber}/byPatron/{idNum}", "itemReservations/checkout_item/{itemNumber}/byPatron/{idNum}"})
	public ItemReservationDto checkoutItem(@PathVariable("itemNumber") String itemNumber, @PathVariable("idNum") String idNum, @RequestParam String currentUserId) {
		return convertToDto(itemReservationService.checkoutItem(currentUserId, itemNumber, idNum));
		
	}

	@PostMapping(value = { "/itemReservations/", "/itemReservations" })
	public ItemReservationDto createItemReservation(@RequestParam String currentUserId, @RequestParam String idNum, @RequestParam String itemNumber, @RequestParam boolean isCheckedOut
			) {
		System.out.println("Flag Post"); 
		ItemReservation reservation = itemReservationService.createItemReservation(currentUserId,
				null, idNum, itemNumber, isCheckedOut
		     );
		return convertToDto(reservation);
	}
	
	@PostMapping(value = { "/itemReservations/customDate", "/itemReservations/customDate/" })
	public ItemReservationDto createItemReservationCustomDate(@RequestParam String currentUserId,
			 @RequestParam Integer numOfRenewalsLeft,
			 @RequestParam String idNum,
			 @RequestParam String itemNumber,
			 @RequestParam boolean isCheckedOut, @RequestParam String startDate
			) {
		System.out.println("Flag Post"); 
		ItemReservation reservation = itemReservationService.createItemReservation(currentUserId,
				Date.valueOf(LocalDate.parse(startDate)), idNum, itemNumber, isCheckedOut
		     );
		return convertToDto(reservation);
	}
	
	@PostMapping(value = {"/itemReservations/renew/{itemReservationId}", "/itemReservations/renew/{itemReservationId}/"})
	public ItemReservationDto renewItemReservation(@PathVariable("itemReservationId") String itemReservationId,@RequestParam String currentUserId) {
		return convertToDto(itemReservationService.renewByItemReservationId(currentUserId,itemReservationId));
	}
	
	@PostMapping(value = {"/itemReservations/cancel/{itemReservationId}", "/itemReservations/cancel/{itemReservationId}/"})
	public boolean cancelItemReservation(@PathVariable("itemReservationId") String itemReservationId,@RequestParam String currentUserId) {
		return itemReservationService.cancelItemReservation(currentUserId,itemReservationId);
	}
	
	private List<ItemReservationDto> getItemReservationDtosForPatron(String currentUserId, String idNum) {
		List<ItemReservation> itemReservationsForPatron = itemReservationService.getItemReservationsByIdNum(currentUserId,idNum);
		List<ItemReservationDto> reservations = new ArrayList<>();
		for (ItemReservation reservation : itemReservationsForPatron) {
			reservations.add(convertToDto(reservation));
		}
		return reservations;
	}
	
	private List<ItemReservationDto> getItemReservationDtosForItem(String currentUserId, String itemNumber) {
		List<ItemReservation> itemReservationsForItem = itemReservationService.getItemReservationsByItemNumber(currentUserId, itemNumber);
		List<ItemReservationDto> reservations = new ArrayList<>();
		for (ItemReservation reservation : itemReservationsForItem) {
			reservations.add(convertToDto(reservation));
		}
		return reservations;
	}
	
	
	private ItemReservationDto convertToDto(ItemReservation reservation){
		ItemReservationDto reservationDto = new ItemReservationDto(reservation.getItemReservationId(), reservation.getStartDate(), reservation.getEndDate(), reservation.getNumOfRenewalsLeft(), reservation.getIdNum(), reservation.getItemNumber(), reservation.getIsCheckedOut());
	
		return reservationDto;
	}
}
