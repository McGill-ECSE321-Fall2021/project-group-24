package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.Shift;

import java.sql.Date;
import java.sql.Time;

import org.springframework.data.repository.CrudRepository;

public interface ShiftRepository extends CrudRepository<Shift, String> {
	Shift findShiftByTimeSlotId(String timeSlotId);
	Shift findShiftByLibrarianIdAndStartDateAndStartTime(String librarianId, Date startDate, Time StartTime); 
}
