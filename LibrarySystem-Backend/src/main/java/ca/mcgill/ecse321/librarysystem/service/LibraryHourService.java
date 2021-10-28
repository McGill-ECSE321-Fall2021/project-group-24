package ca.mcgill.ecse321.librarysystem.service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.LibrarySystemApplication;
import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.model.LibraryHour.DayOfWeek;

public class LibraryHourService {
	LibraryHourRepository libraryHourRepo; 
	
	/*
	 * @author Arman
	 * creates a new operating hour (called "libraryHour") for the library 
	 * @param dayOfWeek, startTime, endTime
	 * @return the new libraryHour
	 */
	@Transactional 
	public LibraryHour createLibraryHour(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		User user = LibrarySystemApplication.getCurrentUser();
		
		if (!(user instanceof HeadLibrarian)) throw new IllegalArgumentException("Only the Head Librarian can modify library hours"); 
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
	
	/*
	 * @author Arman
	 * modifies an operating hour of the library 
	 * @param dayOfWeek, new startTime, new endTime
	 * @return the new library hour
	 */
	public LibraryHour modifyLibraryHour (DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		User user = LibrarySystemApplication.getCurrentUser();
		
		if (!(user instanceof HeadLibrarian)) throw new IllegalArgumentException("Only the Head Librarian can modify library hours"); 
		if (dayOfWeek==null || startTime ==null || endTime ==null) {
			throw new IllegalArgumentException ("Fields cannot be blank"); 
		}
		if(startTime.after(endTime)) throw new IllegalArgumentException ("Start time cannot be after end time");
		
		if (libraryHourRepo.findHourByDayOfWeek(dayOfWeek)==null) throw new IllegalArgumentException("There's no library hour for that day to modify. Create one instead."); 
		LibraryHour libraryHour = libraryHourRepo.findHourByDayOfWeek(dayOfWeek); 
		libraryHour.setDayOfWeek(dayOfWeek);
		libraryHour.setStartTime(startTime);
		libraryHour.setEndTime(endTime);
		
		libraryHourRepo.save(libraryHour); 
		return libraryHour; 
		
	}
	/*
	 * @author Arman
	 * deletes an operating hour of the library 
	 * @param dayOfWeek
	 * @return true if the library hour is deleted
	 */
	@Transactional 
	public boolean removeLibraryHour(DayOfWeek dayOfWeek) {
		User user = LibrarySystemApplication.getCurrentUser();
		
		if (!(user instanceof HeadLibrarian)) throw new IllegalArgumentException("Only the Head Librarian can modify library hours"); 
		if (dayOfWeek==null) throw new IllegalArgumentException ("Field cannot be blank");
		
		if (libraryHourRepo.findHourByDayOfWeek(dayOfWeek)==null) throw new IllegalArgumentException("There's no library hour for that day to delete"); 
				
		libraryHourRepo.delete(libraryHourRepo.findHourByDayOfWeek(dayOfWeek)); 
		return true; 
	}
	
}
