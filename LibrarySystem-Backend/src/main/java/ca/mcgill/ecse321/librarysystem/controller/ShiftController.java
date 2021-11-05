package ca.mcgill.ecse321.librarysystem.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ca.mcgill.ecse321.librarysystem.model.LibraryHour.DayOfWeek;
import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.service.*;

@CrossOrigin(origins = "*")
@RestController
public class ShiftController {
	@Autowired 
	ShiftService shiftService; 
	/** Method adds a shift (not overnight) 
	 * @author Arman 
	 * @param librarianId, startDate, startTime, endTime 
	 * @return Response Entity 
	 */
	@PostMapping(value = {"/add_shift", "/add_shift/"})
	public  ResponseEntity<?> addShift(@RequestParam String librarianId, @RequestParam String startDate, @RequestParam String startTime, @RequestParam String endTime) {
		Shift shift = null; 
		String endDate = startDate; 
		try {
			shift =shiftService.createShift(librarianId, Date.valueOf(startDate), Time.valueOf(startTime), Date.valueOf(endDate), Time.valueOf(endTime)); 
			}		
			catch(IllegalArgumentException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity<>(convertToDto(shift), HttpStatus.CREATED);
	}
	
	/** Method adds an overnight shift (start date is different from end date)
	 * @author Arman 
	 * @param librarianId, startDate, endDate, startTime, endTime 
	 * @return Response Entity 
	 */
	@PostMapping(value = {"/add_overnight_shift", "/add_overnight_shift/"})
	public  ResponseEntity<?> addShift(@RequestParam String librarianId, @RequestParam String startDate, @RequestParam String startTime, @RequestParam String endDate, @RequestParam String endTime) {
		Shift shift = null; 
		try {
			shift =shiftService.createShift(librarianId, Date.valueOf(startDate), Time.valueOf(startTime), Date.valueOf(endDate), Time.valueOf(endTime)); 
			}		
			catch(IllegalArgumentException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity<>(convertToDto(shift), HttpStatus.CREATED);
	}
	
	/** Method modifies a shift (new shift is not overnight)
	 * @author Arman 
	 * @param librarianId, startDate, endDate, startTime, endTime 
	 * @return Response Entity 
	 */
	@PostMapping(value = {"/modify_shift", "/modify_shift/"})
	public  ResponseEntity<?> modifyShift(@RequestParam String librarianId, @RequestParam String oldStartDate, @RequestParam String oldStartTime, 
			@RequestParam String startDate, @RequestParam String startTime, @RequestParam String endDate, @RequestParam String endTime) {
		Shift shift = null; 
		try {
			shift =shiftService.modifyShift(librarianId, Date.valueOf(oldStartDate), Time.valueOf(oldStartTime), 
					Date.valueOf(startDate), Time.valueOf(startTime), Date.valueOf(endDate), Time.valueOf(endTime)); 
			}		
			catch(IllegalArgumentException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity<>(convertToDto(shift), HttpStatus.CREATED);
	}
	
	/** Method converts a shift object into a shift DTO
	 * @author Arman 
	 * @param shift 
	 * @return shiftDTO
	 */
	private ShiftDto convertToDto(Shift shift){
		if (shift == null) throw new IllegalArgumentException("This shift does not exist");
		ShiftDto shiftDto = new ShiftDto(shift.getLibrarianId(), shift.getStartDate(), shift.getStartTime(), shift.getEndDate(), shift.getStartTime()); 
		return shiftDto;
	}
}
