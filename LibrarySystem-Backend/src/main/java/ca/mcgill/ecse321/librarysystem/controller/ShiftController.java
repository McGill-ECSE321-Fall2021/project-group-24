package ca.mcgill.ecse321.librarysystem.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	/** Method adds a shift (if startDate is different from endDate then it's an overnight shift)
	 * @author Arman 
	 * @param librarianId, startDate, endDate, startTime, endTime 
	 * @return Response Entity 
	 */
	@PostMapping(value = {"/add_overnight_shift", "/add_overnight_shift/"})
	public ResponseEntity<?> addShift(@RequestParam String currentUserId, @RequestParam String librarianId, @RequestParam TimeSlot.DayOfWeek dayOfWeek, @RequestParam String startDate, @RequestParam String startTime, @RequestParam String endDate, @RequestParam String endTime) {
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
	 * @param currentUserId, librarianId, oldStartDate, oldStartTime, startDate, endDate, startTime, endTime 
	 * @return Response Entity 
	 */
	@PostMapping(value = {"/modify_shift", "/modify_shift/"})
	public  ResponseEntity<?> modifyShift(@RequestParam String currentUserId, @RequestParam TimeSlot.DayOfWeek oldDayOfWeek,@RequestParam TimeSlot.DayOfWeek newDayOfWeek, @RequestParam String librarianId, @RequestParam String oldStartTime, 
		 @RequestParam String startTime, @RequestParam String endTime) {
		Shift shift = null; 
		try {
			shift =shiftService.modifyShift(currentUserId, librarianId, oldDayOfWeek, newDayOfWeek, Time.valueOf(oldStartTime+":00"), 
					 Time.valueOf(startTime+":00"), Time.valueOf(endTime+":00")); 
			}		
			catch(IllegalArgumentException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity<>(convertToDto(shift), HttpStatus.CREATED);
	}
	
	/** Method removes an existing shift for a librarian
	 * @author Arman 
	 * @param currentUserId, librarianId, startDate, startTime
	 * @return true if the shift is successfully deleted 
	 */
	@PostMapping(value = {"/remove_shift", "/remove_shift/"}) 
	public boolean removeShift(@RequestParam String currentUserId, @RequestParam String librarianId,@RequestParam TimeSlot.DayOfWeek dayOfWeek, @RequestParam String startTime) {
		return shiftService.removeShift(currentUserId, librarianId, dayOfWeek, Time.valueOf(startTime + ":00"));   
	}
	
	/** Method removes all shifts for a librarian
	 * @author Arman 
	 * @param currentUserId, librarianId
	 * @return true if their shifts are successfully deleted
	 */
	@PostMapping(value = {"/remove_librarian_shifts", "/remove_librarian_shifts/"}) 
	public boolean removeLibrarianShifts(@RequestParam String currentUserId, @RequestParam String librarianId) {
		return shiftService.removeLibrarianShifts(currentUserId, librarianId); 
	}
	
	/** Method returns a specific shift
	 * @author Arman 
	 * @param currentUserId, librarianId, startDate, startTime
	 * @return ShiftDto
	 */
	@GetMapping(value = {"/view_shift", "/view_shift/"})
	public ShiftDto viewShift(@RequestParam String currentUserId, @RequestParam String librarianId, @RequestParam TimeSlot.DayOfWeek dayOfWeek, @RequestParam String startTime){
		return convertToDto(shiftService.getShift(currentUserId, librarianId,dayOfWeek, Time.valueOf(startTime)));
				
	}
	
	/** Method returns all the shifts for a certain librarian
	 * @author Arman 
	 * @param currentUserId, librarianId
	 * @return List of type ShiftDto 
	 */
	@GetMapping(value = {"/view_librarian_shifts", "/view_librarian_shifts/"})
	public List<ShiftDto> viewLibrarianShifts(@RequestParam String currentUserId, @RequestParam String librarianId){
		return 	shiftService.getAllShiftsForLibrarian(currentUserId, librarianId).stream().map(lh -> convertToDto(lh)).collect(Collectors.toList());
	}
	
	/** Method returns all the shifts for every librarian
	 * @author Arman 
	 * @return List of type ShiftDto 
	 */
	@GetMapping(value = {"/view_all_shifts", "/view_all_shifts/"})
	public List<ShiftDto> viewAllShifts(@RequestParam String currentUserId, @RequestParam String librarianId){
		return shiftService.getAllShifts(currentUserId).stream().map(lh -> convertToDto(lh)).collect(Collectors.toList());
	}
	
	/** Method converts a shift object into a shift DTO
	 * @author Arman 
	 * @param shift 
	 * @return shiftDTO
	 */
	private ShiftDto convertToDto(Shift shift){
		if (shift == null) throw new IllegalArgumentException("This shift does not exist");
		ShiftDto shiftDto = new ShiftDto(shift.getLibrarianId(), shift.getDayOfWeek(), shift.getStartTime(), shift.getStartTime()); 
		return shiftDto;
	}
}
