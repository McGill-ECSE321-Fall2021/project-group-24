package ca.mcgill.ecse321.librarysystem.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.HeadLibrarianRepository;
import ca.mcgill.ecse321.librarysystem.dao.RoomBookingRepository;
import ca.mcgill.ecse321.librarysystem.model.HeadLibrarian;
import ca.mcgill.ecse321.librarysystem.model.Librarian;
import ca.mcgill.ecse321.librarysystem.model.LibraryHour;
import ca.mcgill.ecse321.librarysystem.model.Patron;
import ca.mcgill.ecse321.librarysystem.model.Room;
import ca.mcgill.ecse321.librarysystem.model.RoomBooking;
import ca.mcgill.ecse321.librarysystem.model.TimeSlot;
import ca.mcgill.ecse321.librarysystem.model.TimeSlot.DayOfWeek;

@Service
public class RoomBookingService {
	@Autowired
	RoomService roomService;
	@Autowired
	LibraryHourService libraryHourservice;
	@Autowired 
	RoomBookingRepository roomBookingRepository; 
	@Autowired 
	HeadLibrarianService headLibrarianService;
	@Autowired 
	LibrarianService librarianService;
	@Autowired 
	PatronService patronService;
	
	@Transactional
	public RoomBooking createRoomBooking(
		String currentUserId,
		String timeSlotId,
		Date date,
		Time startTime,
		Time endTime,
		String idNum,
		String roomNumber
	) {
		
		// check idNum
		if (!inputIsValid("idNum")) throw new IllegalArgumentException("IdNum cannot be null or empty");
		Patron p = patronService.getPatronAccountByID(idNum);
		if (p == null) throw new IllegalArgumentException("invalid IdNum");
		
		// check roomNumber
		if (!inputIsValid("roomNumber")) throw new IllegalArgumentException("roomNumber cannot be null or empty");
		Room r = roomService.getRoom(roomNumber);
		if (r == null) throw new IllegalArgumentException("invalid roomNumber");
		
		// current user can only create room bookings for themselves
		// check if user is a patron, patron can only delete their own room bookings
	    Patron currentPatron = patronService.getPatronAccountByID(currentUserId);
	    if (currentPatron != null) {
	    	if (!idNum.equals(currentUserId)) {
	    		throw new IllegalArgumentException("You can only create room bookings for yourself");
	    	}
	    }
		// (head) librarians are able to create room bookings for everyone - no need check 
		
		// check if date is in the past
	    if (date.before(Calendar.getInstance().getTime())) throw new IllegalArgumentException("date must be in the future");
		// check if the reservation has any conflicts
		TimeSlot.DayOfWeek dayOfWeek =TimeSlot.DayOfWeek.valueOf(date.toLocalDate().getDayOfWeek().toString());
		if (startTime.after(endTime) || startTime.equals(endTime)  ) {
			throw new IllegalArgumentException("Start time must be before end time");
		}
		else if (isBooked(roomNumber, date, startTime, endTime, null)) {
			throw new IllegalArgumentException("Room booking must not overlap with other bookings of the same room");
		}else if(outsideOfOpeningHours(dayOfWeek, startTime, endTime)) {
			throw new IllegalArgumentException("Room booking must be within library opening hours");
		}
		
		// if no conflict and the inputs are valid 
		RoomBooking roomBooking = new RoomBooking();
		roomBooking.setTimeSlotId(timeSlotId);
		roomBooking.setDate(date);
		roomBooking.setStartTime(startTime);
		roomBooking.setEndTime(endTime);
		roomBooking.setIdNum(idNum);
		roomBooking.setRoomNum(roomNumber);
		roomBooking.setDayOfWeek(dayOfWeek);
		roomBookingRepository.save(roomBooking);
		return roomBooking;
	}
	
	private boolean inputIsValid(String string) {
		if (string == null || string.length()==0) {
			return false;
		}
		return true;
		
	}

	// helper method
	// check if the room is booked during the times given
	public boolean isBooked(String roomNumber, Date date, Time startTime, Time endTime, String timeSlotIdToIgnore) {
		System.out.println(roomNumber);
		Room room = roomService.getRoom(roomNumber);
		for (RoomBooking rb : room.getRoomBookings()) {
			if (date.equals(rb.getDate())) {
				if(timeSlotIdToIgnore == null || !rb.getTimeSlotId().equals(timeSlotIdToIgnore)) {
					if (startTime.after(rb.getStartTime()) && startTime.before(rb.getEndTime())) {
						return true;
					}else if (endTime.after(rb.getStartTime()) && endTime.before(rb.getEndTime())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	// helper method
	// check if the times are during the times given
	public boolean outsideOfOpeningHours(TimeSlot.DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		// find day of the week from date and convert to DayOfweek
		LibraryHour lh = libraryHourservice.getLibraryHour(dayOfWeek);
		if (lh.getStartTime().before(startTime) && lh.getEndTime().after(endTime)) return false;
		return true;
	}
	
	@Transactional
	public RoomBooking updateRoomBooking(String currentUserId, String timeSlotId,
			Date newDate,
			Time newStartTime,
			Time newEndTime,
			String newRoomNumber) throws Exception {
		
		// check if room booking with timeSlotId exists
		RoomBooking rb = roomBookingRepository.findRoomBookingByTimeSlotId(timeSlotId);
		if (rb == null) throw new IllegalArgumentException("Room booking does not exist");

		// check if user is a patron, patron can only modify their own room bookings
	    Patron currentPatron = patronService.getPatronAccountByID(currentUserId);
	    if (currentPatron != null) {
	    	if (!rb.getIdNum().equals(currentUserId)) {
	    		throw new IllegalArgumentException("You do not have permission to modify this room booking");
	    	}
	    }

		// check if the new date and time has any conflicts
	 		TimeSlot.DayOfWeek dayOfWeek =TimeSlot.DayOfWeek.valueOf(newDate.toLocalDate().getDayOfWeek().toString());
	 		if (newStartTime.after(newEndTime) || newStartTime.equals(newEndTime)  ) {
	 			throw new IllegalArgumentException("Start time must be before end time");
	 		}
	 		else if (isBooked(newRoomNumber, newDate, newStartTime,newEndTime, rb.getTimeSlotId())) {
	 			throw new IllegalArgumentException("Room booking must not overlap with other bookings of the same room");
	 		}else if(outsideOfOpeningHours(dayOfWeek, newStartTime, newEndTime)) {
	 			throw new IllegalArgumentException("Room booking must be within library opening hours");
	 		}
	    
		// modify roombooking attributes
	    rb.setDate(newDate);
	    rb.setDayOfWeek(DayOfWeek.valueOf(newDate.toString()));
	    rb.setRoomNum(newRoomNumber);
	    rb.setStartTime(newStartTime);
	    rb.setEndTime(newEndTime);
	    roomBookingRepository.save(rb);
		return rb;
	}
	
	@Transactional
	public RoomBooking deleteRoomBooking(String currentUserId, String timeSlotId) {
		RoomBooking rb = roomBookingRepository.findRoomBookingByTimeSlotId(timeSlotId);
		
		if (rb == null) throw new IllegalArgumentException("Room booking does not exist");

		// check if user is a patron, patron can only delete their own room bookings
	    Patron currentPatron = patronService.getPatronAccountByID(currentUserId);
	    if (currentPatron != null) {
	    	if (!rb.getIdNum().equals(currentUserId)) {
	    		throw new IllegalArgumentException("You do not have permission to modify this room booking");
	    	}
	    }
	    roomBookingRepository.delete(rb);
		return rb;
	}
	
	// looks for a room with the given ID number, returns them if found
	@Transactional 
	public List<RoomBooking> getAllRoomBookings() {
		return toList(roomBookingRepository.findAll()); 

	}
	
	@Transactional 
	public List<RoomBooking> getRoomBookingsByRoomNum(String roomNum) {
		return roomBookingRepository.findRoomBookingByRoomNum(roomNum);
	}
	
	@Transactional 
	public List<RoomBooking> getRoomBookingsByIdNum(String idNum) {
		return roomBookingRepository.findRoomBookingByIdNum(idNum);
	}
	
	@Transactional 
	public RoomBooking getRoomBookingsByTimeSlotId(String timeSlotId) {
		return roomBookingRepository.findRoomBookingByTimeSlotId(timeSlotId);
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
