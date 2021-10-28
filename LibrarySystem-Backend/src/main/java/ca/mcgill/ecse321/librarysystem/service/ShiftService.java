package ca.mcgill.ecse321.librarysystem.service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;

@Service 
public class ShiftService {
	ShiftRepository shiftRepo;
	LibrarySystemRepository librarySystemRepo; 
	// Creates a shift with an associated librarian
	@Transactional 
	public Shift createShift(String timeSlotId, Date startDate, Date endDate, Time startTime, Time endTime ) {

		if (timeSlotId==null || startDate==null || endDate==null || startTime==null || endTime ==null) {
			throw new IllegalArgumentException("Fields cannot be blank"); 
		}
		if (startDate.after(endDate)) throw new IllegalArgumentException("Shift end date cannot be before start date"); 
		if (startTime.after(endTime) && startDate.equals(endDate)) throw new IllegalArgumentException("Shift end time cannot be before its start time"); 
		
		Shift shift = new Shift(); 
		shift.setTimeSlotId(timeSlotId);
		shift.setStartDate(startDate);
		shift.setStartTime(startTime);
		shift.setEndDate(endDate);
		shift.setEndTime(endTime);
		
		shiftRepo.save(shift); 
		return shift;
	}
	
	// deletes shift if it can be found
	@Transactional 
	public boolean deleteShift(String timeSlotId) {
		Shift shift = shiftRepo.findShiftByTimeSlotId(timeSlotId); 
		if (shift==null) throw new IllegalArgumentException("Shift cannot be found"); 
		shiftRepo.delete(shiftRepo.findShiftByTimeSlotId(timeSlotId));
		return true; 
	}
	
}
