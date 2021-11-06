package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.*;

import java.sql.Date;
import java.sql.Time;

import org.springframework.data.repository.CrudRepository;

public interface ShiftRepository extends CrudRepository<Shift, String> {
	Shift findShiftByTimeSlotId(String timeSlotId);
	Shift findShiftByLibrarianIdAndDayOfWeekAndStartTime(String librarianId, TimeSlot.DayOfWeek dayOfWeek, Time StartTime); 
}
