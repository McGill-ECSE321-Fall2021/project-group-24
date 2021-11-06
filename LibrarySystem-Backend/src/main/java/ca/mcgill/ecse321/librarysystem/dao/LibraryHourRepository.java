package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.*;


import org.springframework.data.repository.CrudRepository;

public interface LibraryHourRepository extends CrudRepository<LibraryHour, String> {
	LibraryHour findHourByTimeSlotId(String timeSlotId);
	LibraryHour findHourByDayOfWeek(TimeSlot.DayOfWeek dayOfWeek); 
}
