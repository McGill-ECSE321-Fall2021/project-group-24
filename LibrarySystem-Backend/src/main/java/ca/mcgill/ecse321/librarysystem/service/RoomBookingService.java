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
import ca.mcgill.ecse321.librarysystem.model.Room;
import ca.mcgill.ecse321.librarysystem.model.RoomBooking;
import ca.mcgill.ecse321.librarysystem.model.TimeSlot;

@Service
public class RoomBookingService {
	@Autowired
	RoomService roomService;
	@Autowired
	LibraryHourService libraryHourservice;
	@Autowired 
	RoomBookingRepository roomBookingRepository; 
	
	@Transactional
	public RoomBooking createRoomBooking(
	String timeSlotId,
	Date date,
	Time startTime,
	Time endTime,
	String idNum,
	String roomNumber
	) {
		// check if the reservation has any conflicts
		TimeSlot.DayOfWeek dayOfWeek =TimeSlot.DayOfWeek.valueOf(date.toLocalDate().getDayOfWeek().toString());
		if (startTime.after(endTime) || startTime.equals(endTime)  ) {
			throw new IllegalArgumentException("Start time must be before end time");
		}
		else if (isBooked(roomNumber, date,startTime,endTime)) {
			throw new IllegalArgumentException("Room booking must not overlap with other bookings of the same room");
		}
		//TODO: Maybe for this method take dayOfWeek as an input instead?
//			}else if(outsideOfOpeningHours(startDate, startTime, endTime)) {
//			throw new IllegalArgumentException("Room booking must be within library opening hours");
//		}
		// if no conflict
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
	
	// check if the room is booked during the times given
	//TODO: @Selena this does not check the date at all
	public boolean isBooked(String roomNumber, Date date, Time startTime, Time endTime) {
		System.out.println(roomNumber);
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
	//TODO: Make this use TimeSlot.DayOfWeek as input instead of Date. The 
	//RoomBooking class now has access to what DayOfWeek it is so does not need to find it
	public boolean outsideOfOpeningHours(Date date, Time startTime, Time endTime) {
		// find day of the week from date and convert to DayOfweek
		String dayString = date.toLocalDate().getDayOfWeek().toString();
		String lowercase = dayString.substring(1).toLowerCase();
		String firstletter = dayString.substring(0,1);
		for (TimeSlot.DayOfWeek day : TimeSlot.DayOfWeek.values()) {
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
