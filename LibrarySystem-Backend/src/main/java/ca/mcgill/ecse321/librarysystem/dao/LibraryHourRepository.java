package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.LibraryHour;
import ca.mcgill.ecse321.librarysystem.model.LibraryHour.DayOfWeek;

import org.springframework.data.repository.CrudRepository;

public interface LibraryHourRepository extends CrudRepository<LibraryHour, String> {
	LibraryHour findHourByTimeSlotId(String timeSlotId);
	LibraryHour findHourByDayOfWeek(DayOfWeek dayOfWeek); 
}
