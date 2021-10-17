package ca.mcgill.ecse321.librarysystem.dao;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.LibraryHour;

public interface LibraryHourRepository extends CrudRepository<LibraryHour, String> {
	LibraryHour findHourByHourID(String HourID);
}

