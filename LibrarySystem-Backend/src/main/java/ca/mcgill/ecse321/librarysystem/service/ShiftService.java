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

@Service 
public class ShiftService {
	@Autowired
	ShiftRepository shiftRepo;
	@Autowired
	LibrarianRepository librarianRepo; 


	/**
	 * @author Arman
	 * @param librarianId, startDate, endDate, startTime, endTime
	 * @return shift 
	 * Note that there is a start and an end date to account for overnight shifts
	 */
	@Transactional 
	public Shift createShift(String librarianId, Date startDate, Time startTime, Date endDate, Time endTime ) {
		User user = null;

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
	/**
	 * @author Arman
	 * @param librarianId, oldStartDate, oldStartTime, startDate, endDate, startTime, endTime
	 * @return shift
	 */
	
	@Transactional 
	public Shift modifyShift(String librarianId, Date oldStartDate, Time oldStartTime, Date startDate, Time startTime, Date endDate, Time endTime) {
		User user = null;

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
	
	/**
	 * @author Arman
	 * @param librarianId, startTime, endTime
	 * @return true if the shift is deleted
	 */
	@Transactional 
	public boolean removeShift(String librarianId, Date startDate, Time startTime) {
		User user = null;

		if (!(user instanceof HeadLibrarian)) throw new IllegalArgumentException("Only the Head Librarian can remove librarian shifts");
		if (librarianId==null || startDate==null || startTime==null) {
			throw new IllegalArgumentException("Fields cannot be blank"); 
		}
		Shift shift = shiftRepo.findShiftByLibrarianIdAndStartDateAndStartTime(librarianId, startDate, startTime); 
		if (shift==null) throw new IllegalArgumentException("Shift cannot be found"); 
		shiftRepo.delete(shiftRepo.findShiftByLibrarianIdAndStartDateAndStartTime(librarianId, startDate, startTime));
		return true; 
	}
	
	/**
	 * @author Arman
	 * @param librarianId
	 * @return true if all their shifts are deleted
	 */
	@Transactional 
	public boolean removeLibrarianShifts(String librarianId) {
		// check that it's head librarian 
		if (librarianId == null) throw new IllegalArgumentException("librarian ID cannot be blank"); 
		// Searches through all the shifts, deleting the ones associated with a certain librarian
		List<Shift> allShifts = toList(shiftRepo.findAll()); 
		for (Shift shift: allShifts) {
			if (shift.getLibrarianId().equals(librarianId)) {
				shiftRepo.delete(shift);
			}
		}
		return true; 
	}
	
	/**
	 *@author Arman
	 *@param librarianId, startTime, endTime
	 *@return shift
	 *Note that librarians can view (but not modify) each other's shifts
	 */
	@Transactional 
	public Shift getShift(String librarianId, Date startDate, Time startTime) {
		// check if user is a librarian 
		
		if (librarianId==null || startDate==null || startTime==null) {
			throw new IllegalArgumentException("Fields cannot be blank"); 
		}
		
		Shift shift = shiftRepo.findShiftByLibrarianIdAndStartDateAndStartTime(librarianId, startDate, startTime); 
		if (shift==null) throw new IllegalArgumentException("Shift cannot be found"); 
		return shift; 
	}
	/**
	 *@author Arman
	 *@param librarianId
	 *@return list of all the librarian's shifts 
	 */
	@Transactional 
	public List<Shift> getAllShiftsForLibrarian(String librarianId) {
		// check if user is a librarian
		if (librarianId==null) throw new IllegalArgumentException("Librarian ID cannot be blank"); 
		
		// This searches through all shifts, add the ones with desired librarianId to a list then return the list. 
		List<Shift> allShifts = toList(shiftRepo.findAll()); 
		ArrayList<Shift> desiredShifts = new ArrayList<Shift>(); 
		for (Shift shift: allShifts) {
			if (shift.getLibrarianId().equals(librarianId)) {
				desiredShifts.add(shift); 
			}
		}
		return desiredShifts; 
	}
	
	/**
	 *@author Arman
	 *@param none
	 *@return list of everyone's shifts
	 */
	@Transactional 
	public List<Shift> getAllShifts() {
		// check if user is a librarian
		return toList(shiftRepo.findAll()); 
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
