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
import ca.mcgill.ecse321.librarysystem.model.LibraryHour.DayOfWeek;

public class LibraryHourService {
	LibraryHourRepository libraryHourRepo; 
	
	@Transactional 
	public LibraryHour createLibraryHour(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		if (dayOfWeek==null || startTime ==null || endTime ==null) {
			throw new IllegalArgumentException ("Fields cannot be blank"); 
		}
		if(startTime.after(endTime)) throw new IllegalArgumentException ("Start time cannot be after end time");
		
		if (libraryHourRepo.findHourByDayOfWeek(dayOfWeek)!=null) throw new IllegalArgumentException("There's already a library hour for that day"); 
		
		LibraryHour libraryHour = new LibraryHour(); 
		libraryHour.setDayOfWeek(dayOfWeek);
		libraryHour.setStartTime(startTime);
		libraryHour.setEndTime(endTime);
		
		libraryHourRepo.save(libraryHour); 
		return libraryHour; 
	}
}
