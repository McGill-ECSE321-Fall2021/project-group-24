package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.LibraryHour;
import org.springframework.data.repository.CrudRepository;

public interface LibraryHourRepository extends CrudRepository<LibraryHour, String> {
	LibraryHour findHourByTimeSlotId(String timeSlotId);
}
