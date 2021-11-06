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
	
	// Creates a shift with an associated librarian
	@Transactional 
	public Shift createShift(String timeSlotId, TimeSlot.DayOfWeek dayOfWeek, Time startTime, Time endTime ) {
		Shift shift = new Shift(); 
		shift.setTimeSlotId(timeSlotId);
		shift.setStartTime(startTime);
		shift.setDayOfWeek(dayOfWeek);
		shift.setEndTime(endTime);
		
		shiftRepo.save(shift); 
		return shift;
	} 
	
}
