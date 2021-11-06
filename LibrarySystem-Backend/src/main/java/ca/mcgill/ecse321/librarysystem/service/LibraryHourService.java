package ca.mcgill.ecse321.librarysystem.service;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;

@Service
public class LibraryHourService {
	@Autowired
	LibraryHourRepository libraryHourRepo; 
	@Autowired
	HeadLibrarianRepository headLibrarianRepo; 
	
	/**Method creates a new operating hour (called "libraryHour") for the library 
	 * @author Arman
	 * @param currentUserId, dayOfWeek, startTime, endTime
	 * @return the new libraryHour
	 */
	@Transactional 
	public LibraryHour createLibraryHour(String currentUserId, TimeSlot.DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		if (headLibrarianRepo.findById(currentUserId)==null) throw new IllegalArgumentException("Only the Head Librarian can create library hours");

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
	
	/**Method modifies an operating hour of the library 
	 * @author Arman
	 * @param currentUserId, dayOfWeek, new startTime, new endTime
	 * @return the new library hour
	 */
	public LibraryHour modifyLibraryHour (String currentUserId, TimeSlot.DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		if (headLibrarianRepo.findById(currentUserId)==null) throw new IllegalArgumentException("Only the Head Librarian can modify library hours");
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
	
	/**Method removes a library operating hour
	 * @author Arman
	 * @param currentUserId, dayOfWeek
	 * @return true if the library hour is deleted
	 */
	@Transactional 
	public boolean removeLibraryHour(String currentUserId, TimeSlot.DayOfWeek dayOfWeek) {
		if (headLibrarianRepo.findById(currentUserId)==null) throw new IllegalArgumentException("Only the Head Librarian can remove library hours");
		if (dayOfWeek==null) throw new IllegalArgumentException ("Field cannot be blank");
		if (libraryHourRepo.findHourByDayOfWeek(dayOfWeek)==null) throw new IllegalArgumentException("There's no library hour for that day to delete"); 
				
		libraryHourRepo.delete(libraryHourRepo.findHourByDayOfWeek(dayOfWeek)); 
		return true; 
	}
	
	/**
	 * @author Arman
	 * @param dayOfWeek
	 * @return the library hour for the chosen dayOfWeek. Throws an error if there's no library hour for that day
	 */
	@Transactional
	public LibraryHour getLibraryHour(TimeSlot.DayOfWeek dayOfWeek) {
		if (dayOfWeek==null) throw new IllegalArgumentException ("Field cannot be blank");
		if (libraryHourRepo.findHourByDayOfWeek(dayOfWeek)==null) throw new IllegalArgumentException("There's no library hour for that day"); 
		
		LibraryHour libraryHour = libraryHourRepo.findHourByDayOfWeek(dayOfWeek); 
		return libraryHour; 
	}
	
	/**
	 * @author Arman
	 * @param none
	 * @return list of all libraryHours
	 */
	@Transactional 
	public List<LibraryHour> getAllLibraryHours() {
		if(libraryHourRepo.count()==0) throw new IllegalArgumentException("No library hours exist"); 
		return toList(libraryHourRepo.findAll()); 
	}
	
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
