package ca.mcgill.ecse321.librarysystem.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.service.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/shift")
public class ShiftController {
	@Autowired 
	ShiftService shiftService; 
	
	/** Method adds a shift
	 * @author Arman 
	 * @param currentUserId, librarianId, dayOfWeek, startTime, endTime 
	 * @return Response Entity 
	 */
	@PostMapping(value = {"/add_shift", "/add_shift/"})
	public ResponseEntity<?> addShift(@RequestParam String currentUserId, @RequestParam String librarianId, @RequestParam TimeSlot.DayOfWeek dayOfWeek, 
			 @RequestParam String startTime, @RequestParam String endTime) {
		Shift shift = null; 
		// request param usernameId, do that in shift methods as well so that we verify that the username is one in headLibrarian Repo
		try {
			shift =shiftService.createShift(currentUserId, librarianId, dayOfWeek, Time.valueOf(startTime + ":00"),  Time.valueOf(endTime+":00")); 
			}		
			catch(IllegalArgumentException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity<>(convertToDto(shift), HttpStatus.CREATED);
	}
	
	/** Method modifies a shift
	 * @author Arman 
	 * @param currentUserId, librarianId, timeSlotId, dayOfWeek, startTime, endTime 
	 * @return Response Entity 
	 */
	@PutMapping(value = {"/modify_shift", "/modify_shift/"})
	public  ResponseEntity<?> modifyShift(@RequestParam String currentUserId, @RequestParam String timeSlotId, @RequestParam String librarianId, 
			@RequestParam TimeSlot.DayOfWeek dayOfWeek, @RequestParam String startTime, @RequestParam String endTime) {
		Shift shift = null; 
		try {
			shift =shiftService.modifyShift(currentUserId, timeSlotId, librarianId, dayOfWeek, Time.valueOf(startTime+":00"), Time.valueOf(endTime+":00")); 
			}		
			catch(IllegalArgumentException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity<>(convertToDto(shift), HttpStatus.CREATED);
	}
	
	/** Method removes an existing shift for a librarian
	 * @author Arman 
	 * @param currentUserId, timeSlotId
	 * @return true if the shift is successfully deleted 
	 */
	@PostMapping(value = {"/remove_shift", "/remove_shift/"}) 
	public ResponseEntity<?> removeShift(@RequestParam String currentUserId, @RequestParam String timeSlotId) {
		boolean isDeleted = false; 
		try {
			isDeleted = shiftService.removeShift(currentUserId, timeSlotId);  
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
		return new ResponseEntity<>(isDeleted, HttpStatus.CREATED); 
	}
	
	/** Method removes all shifts for a librarian
	 * @author Arman 
	 * @param currentUserId, librarianId
	 * @return true if their shifts are successfully deleted
	 */
	@PostMapping(value = {"/remove_librarian_shifts", "/remove_librarian_shifts/"}) 
	public ResponseEntity<?> removeLibrarianShifts(@RequestParam String currentUserId, @RequestParam String librarianId) {
		boolean isDeleted = false; 
		try {
			isDeleted = shiftService.removeLibrarianShifts(currentUserId, librarianId); 
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
		return new ResponseEntity<>(isDeleted, HttpStatus.CREATED); 
	}
	
	/** Method returns a specific shift
	 * @author Arman 
	 * @param currentUserId, timeSlotId
	 * @return ShiftDto
	 */
	@GetMapping(value = {"/view_shift", "/view_shift/"})
	public ResponseEntity<?> viewShift(@RequestParam String currentUserId, @RequestParam String timeSlotId){
		Shift shift = null; 
		try {
			shift = shiftService.getShift(currentUserId, timeSlotId); 
		}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
		return new ResponseEntity<>(convertToDto(shift), HttpStatus.OK); 
	}
	
	/** Method returns all the shifts for a certain librarian
	 * @author Arman 
	 * @param currentUserId, librarianId
	 * @return List of type ShiftDto 
	 */
	@GetMapping(value = {"/view_librarian_shifts", "/view_librarian_shifts/"})
	public ResponseEntity<?> viewLibrarianShifts(@RequestParam String currentUserId, @RequestParam String librarianId){
		List<ShiftDto> shifts = new ArrayList<ShiftDto>(); 
		try {
			shifts = shiftService.getAllShiftsForLibrarian(currentUserId, librarianId).stream().map(lh -> convertToDto(lh)).collect(Collectors.toList());
		}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
		return new ResponseEntity<>(shifts, HttpStatus.OK); 
	}
	
	/** Method returns all the shifts for every librarian
	 * @author Arman 
	 * @return List of type ShiftDto 
	 */
	@GetMapping(value = {"/view_all_shifts", "/view_all_shifts/"})
	public ResponseEntity<?> viewAllShifts(@RequestParam String currentUserId){
		List<ShiftDto> shifts = new ArrayList<ShiftDto>(); 
		try {
			shifts = shiftService.getAllShifts(currentUserId).stream().map(lh -> convertToDto(lh)).collect(Collectors.toList());
		}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		}
		return new ResponseEntity<>(shifts, HttpStatus.OK); 
	}
	
	/** Method converts a shift object into a shift DTO
	 * @author Arman 
	 * @param shift 
	 * @return shiftDTO
	 */
	private ShiftDto convertToDto(Shift shift){
		if (shift == null) throw new IllegalArgumentException("This shift does not exist");
		ShiftDto shiftDto = new ShiftDto(shift.getTimeSlotId(), shift.getLibrarianId(), shift.getDayOfWeek(), shift.getStartTime(), shift.getEndTime()); 
		return shiftDto;
	}
}
