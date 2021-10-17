package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Shift;

public interface ShiftRepository extends CrudRepository<Shift, String> {
	Shift findShiftByShiftID(String shiftID);
}

