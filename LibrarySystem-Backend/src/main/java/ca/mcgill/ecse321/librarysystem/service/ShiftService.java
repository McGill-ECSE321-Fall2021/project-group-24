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

@Service 
public class ShiftService {
	ShiftRepository shiftRepo;
	LibrarianRepository librarianRepo; 
	LibrarySystemRepository librarySystemRepo; 

	/* @author Arman
	 * @param librarianId, startDate, endDate, startTime, endTime
	 * @return shift
	 * Note that there is a start and an end date to account for overnight shifts
	 */
	@Transactional 
	public Shift createShift(String librarianId, Date startDate, Time startTime, Date endDate, Time endTime ) {
		User user = LibrarySystemApplication.getCurrentUser();

		if (!(user instanceof HeadLibrarian)) throw new IllegalArgumentException("Only the Head Librarian can modify librarian shifts");

		if (librarianId==null || startDate==null || endDate==null || startTime==null || endTime ==null) {
			throw new IllegalArgumentException("Fields cannot be blank"); 
		}
		if (startDate.after(endDate)) throw new IllegalArgumentException("Shift end date cannot be before start date"); 
		if (startTime.after(endTime) && startDate.equals(endDate)) throw new IllegalArgumentException("Shift end time cannot be before its start time"); 
		
		if(shiftRepo.findShiftByLibrarianIdAndStartDateAndStartTime(librarianId, startDate, startTime)!=null) throw new IllegalArgumentException("Shift already exists"); 
		if(librarianRepo.findUserByIdNum(librarianId)==null) throw new IllegalArgumentException("No Librarian with this ID exists"); 
		
		Shift shift = new Shift(); 
		shift.setStartDate(startDate);
		shift.setStartTime(startTime);
		shift.setEndDate(endDate);
		shift.setEndTime(endTime);
		shiftRepo.save(shift); 
		return shift;
	}
	/* @author Arman
	 * @param librarianId, oldStartDate, oldStartTime, startDate, endDate, startTime, endTime
	 * @return shift
	 */
	
	@Transactional 
	public Shift modifyShift(String librarianId, Date oldStartDate, Time oldStartTime, Date startDate, Time startTime, Date endDate, Time endTime) {
		User user = LibrarySystemApplication.getCurrentUser();

		if (!(user instanceof HeadLibrarian)) throw new IllegalArgumentException("Only the Head Librarian can modify librarian shifts");

		if (librarianId==null || oldStartDate==null || oldStartTime==null || startDate==null || endDate==null || startTime==null || endTime ==null) {
			throw new IllegalArgumentException("Fields cannot be blank"); 
		}
		if (startDate.after(endDate)) throw new IllegalArgumentException("Shift end date cannot be before start date"); 
		if (startTime.after(endTime) && startDate.equals(endDate)) throw new IllegalArgumentException("Shift end time cannot be before its start time"); 
		
		if(shiftRepo.findShiftByLibrarianIdAndStartDateAndStartTime(librarianId, oldStartDate, oldStartTime)==null) throw new IllegalArgumentException("Shift does not exist so cannot modify."); 
		if(librarianRepo.findUserByIdNum(librarianId)==null) throw new IllegalArgumentException("No Librarian with this ID exists"); 
		
		Shift shift = shiftRepo.findShiftByLibrarianIdAndStartDateAndStartTime(librarianId, oldStartDate, oldStartTime);
		shift.setStartDate(startDate);
		shift.setStartTime(startTime);
		shift.setEndDate(endDate);
		shift.setEndTime(endTime);
		
		shiftRepo.save(shift); 
		return shift; 
	}
	
	// deletes shift if it can be found, returns true if shift is deleted
	@Transactional 
	public boolean deleteShift(String librarianId, Date startDate, Time startTime) {
		User user = LibrarySystemApplication.getCurrentUser();

		if (!(user instanceof HeadLibrarian)) throw new IllegalArgumentException("Only the Head Librarian can modify librarian shifts");
		Shift shift = shiftRepo.findShiftByLibrarianIdAndStartDateAndStartTime(librarianId, startDate, startTime); 
		if (shift==null) throw new IllegalArgumentException("Shift cannot be found"); 
		shiftRepo.delete(shiftRepo.findShiftByLibrarianIdAndStartDateAndStartTime(librarianId, startDate, startTime));
		return true; 
	}
	
}
