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
public class ItemReservationService {
	@Autowired 
	ItemReservationRepository itemReservationRepository;
		
	// creates archive, returns it so we know it's not null 
	@Transactional 
	public ItemReservation createItemReservation (
			String timeSlotId,
			   Date startDate,
			   Time startTime,
			   Date endDate,
			   Time endTime,
		 String idNum,
		String itemNumber,
		int numOfRenewalsLeft)
	{
		ItemReservation reservation = new ItemReservation();
		reservation.setTimeSlotId(timeSlotId);
		reservation.setStartDate(startDate);
		reservation.setStartTime(startTime);
		reservation.setEndDate(endDate);
		reservation.setEndTime(endTime);
		reservation.setIdNum(idNum);
		reservation.setItemNumber(itemNumber);
		reservation.setNumOfRenewalsLeft(numOfRenewalsLeft);

		itemReservationRepository.save(reservation);
	    return reservation;		
	}
	
	// looks for a reservation with the given item number, returns them if found
	@Transactional 
	public ItemReservation getItemReservation(String timeSlotId) {
		ItemReservation reservation = itemReservationRepository.findItemReservationByTimeSlotId(timeSlotId); 
		return reservation;
	}
	
	@Transactional 
	public List<ItemReservation> getAllItemReservations() {
		return toList(itemReservationRepository.findAll()); 
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
