package ca.mcgill.ecse321.librarysystem.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.RoomBookingRepository;
import ca.mcgill.ecse321.librarysystem.model.LibraryHour;
import ca.mcgill.ecse321.librarysystem.model.LibraryHour.DayOfWeek;
import ca.mcgill.ecse321.librarysystem.model.Room;
import ca.mcgill.ecse321.librarysystem.model.RoomBooking;

@Service
public class RoomBookingService {
	RoomService roomService;
	LibraryHourService libraryHourservice;
	@Autowired 
	RoomBookingRepository roomBookingRepository; 
	
	@Transactional
	public RoomBooking createRoomBooking(
	String timeSlotId,
	Date startDate,
	Time startTime,
	Date endDate,
	Time endTime,
	String idNum,
	String roomNumber
	) {

		// check if the reservation has any conflicts
		if (!startDate.equals(endDate)) {
			throw new IllegalArgumentException("Room booking must be on the same day");
		}else if (startTime.after(endTime) || startTime.equals(endTime)  ) {
			throw new IllegalArgumentException("Start time must be before end time");
		}
		else if (isBooked(roomNumber, startDate,startTime,endDate,endTime)) {
			throw new IllegalArgumentException("Room booking must not overlap with other bookings of the same room");
		}else if(outsideOfOpeningHours(startDate, startTime, endTime)) {
			throw new IllegalArgumentException("Room booking must be within library opening hours");
		}
		
		// if no conflict
		RoomBooking roomBooking = new RoomBooking();
		roomBooking.setTimeSlotId(timeSlotId);
		roomBooking.setStartDate(startDate);
		roomBooking.setStartTime(startTime);
		roomBooking.setEndDate(endDate);
		roomBooking.setEndTime(endTime);
		roomBooking.setIdNum(idNum);
		roomBooking.setRoomNum(roomNumber);
		roomBookingRepository.save(roomBooking);
		return roomBooking;
	}
	
	// check if the room is booked during the times given
	public boolean isBooked(String roomNumber, Date startDate, Time startTime, Date endDate, Time endTime) {
		Room room = roomService.getRoom(roomNumber);
		for (RoomBooking rb : room.getRoomBookings()) {
			if (startTime.after(rb.getStartTime()) && startTime.before(rb.getEndTime())) {
				return true;
			}else if (endTime.after(rb.getStartTime()) && endTime.before(rb.getEndTime())) {
				return true;
			}
		}
		return false;
	}
	
	// check if the times are during the times given
	public boolean outsideOfOpeningHours(Date startDate, Time startTime, Time endTime) {
		// find day of the week from date and convert to DayOfweek
		String dayString = startDate.toLocalDate().getDayOfWeek().toString();
		String lowercase = dayString.substring(1).toLowerCase();
		String firstletter = dayString.substring(0,1);
		for (DayOfWeek day : DayOfWeek.values()) {
			if (day.toString().equals(firstletter+lowercase)) {
				LibraryHour libhour = libraryHourservice.getLibraryHour(day);
				if (libhour.getStartTime().before(startTime) && libhour.getEndTime().after(endTime)) return false;
			}
		}
		return true;
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
	public RoomBooking getRoomBookingsByTimeSLotId(String timeSlotId) {
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
