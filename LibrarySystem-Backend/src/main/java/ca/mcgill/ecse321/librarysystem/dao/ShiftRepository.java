package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.Librarian;
import ca.mcgill.ecse321.librarysystem.model.Shift;
import org.springframework.data.repository.CrudRepository;

public interface ShiftRepository extends CrudRepository<Shift, String> {
	Shift findShiftByTimeSlotId(String timeSlotId);
}
