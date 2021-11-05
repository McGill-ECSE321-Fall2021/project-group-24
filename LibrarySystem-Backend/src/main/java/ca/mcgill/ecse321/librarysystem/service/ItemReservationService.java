package ca.mcgill.ecse321.librarysystem.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
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
	@Autowired 
	ItemRepository itemRepository;
	@Autowired
	PatronRepository patronRepository;
	@Autowired
	LibrarianRepository librarianRepository;
		
	@Transactional 
	public ItemReservation createItemReservation (
			String timeSlotId,
			   Date startDate,
			   Time startTime,
			   Date endDate,
			   Time endTime,
		 String idNum,
		String itemNumber,
		int numOfRenewalsLeft, boolean isCheckedOut)
	{
		if (patronRepository.findUserByIdNum(idNum) == null && librarianRepository.findUserByIdNum(idNum) == null) {
			System.out.println("id num is" + idNum);
			throw new IllegalArgumentException("Invalid idNum");
		}
		
		if (itemRepository.findItemByItemNumber(itemNumber) == null) {
				throw new IllegalArgumentException("The item does not exist");
			}
		
		List<ItemReservation> currentItemReservations = getItemReservationsByItemNumber(itemNumber);
		for (ItemReservation currentReservation : currentItemReservations) {
			//if the new reservation starts or ends within the current one
			if (startDate.after(currentReservation.getStartDate()) && startDate.before(currentReservation.getEndDate()) ||
					endDate.after(currentReservation.getStartDate()) && endDate.before(currentReservation.getEndDate()) ||
					startDate.equals(currentReservation.getStartDate()) || endDate.equals(currentReservation.getEndDate())) {
				throw new IllegalArgumentException("Overlaps with previous reservation");
			} else if (startDate.after(endDate)) {
				throw new IllegalArgumentException("Start Date cannot be after end date");
			}
		}
		
		if (getItemReservationsByIdNum(idNum).size() > 9) {
			throw new IllegalArgumentException("Patron can have a maximum of 10 reservations or checked out books at a time");
		}
		ItemReservation reservation = new ItemReservation();
		reservation.setTimeSlotId(timeSlotId);
		reservation.setStartDate(startDate);
		reservation.setStartTime(startTime);
		reservation.setEndDate(endDate);
		reservation.setEndTime(endTime);
		reservation.setIdNum(idNum);
		reservation.setItemNumber(itemNumber);
		reservation.setNumOfRenewalsLeft(numOfRenewalsLeft);
		reservation.setIsCheckedOut(isCheckedOut);

		itemReservationRepository.save(reservation);
	    return reservation;		
	}
	
	// looks for a reservation with the given time slot id, returns them if found
	@Transactional 
	public ItemReservation getItemReservation(String timeSlotId) {
		ItemReservation reservation = itemReservationRepository.findItemReservationByTimeSlotId(timeSlotId); 
		return reservation;
	}
	
	@Transactional 
	public List<ItemReservation> getAllItemReservations() {
		return toList(itemReservationRepository.findAll()); 
	}

	@Transactional
	public List<ItemReservation> getItemReservationsByIdNum(String idNum) {
	    List<ItemReservation> reservationsByIdNum = new ArrayList<>();
	    for (ItemReservation r : itemReservationRepository.findItemReservationByIdNum(idNum)) {
	        reservationsByIdNum.add(r);
	    }
	    return reservationsByIdNum;
	}
	
	@Transactional
	public ItemReservation updateReservationCheckedOut(String itemNumber, String idNum) {
		ItemReservation latestReservation = null;
		for (ItemReservation r : getItemReservationsByItemNumberAndIdNum(itemNumber, idNum)) {
			if (latestReservation == null) {
				latestReservation = r;
			} else if (latestReservation.getEndDate().before(r.getStartDate())) {
				latestReservation = r;
			}
		}
		Date today = Date.valueOf(LocalDate.now());
		if (latestReservation.getStartDate().after(today) || latestReservation.getEndDate().before(today)) {
			throw new IllegalArgumentException("Reservation is not right now");
		}
		latestReservation.setIsCheckedOut(true);
		Item item = itemRepository.findItemByItemNumber(latestReservation.getItemNumber());
		item.setCurrentReservationId(latestReservation.getTimeSlotId());
		itemRepository.save(item);
		itemReservationRepository.save(latestReservation);
		return latestReservation;
	}
	
	@Transactional
	public ItemReservation returnItemFromReservation(String itemNumber) {
		String timeSlotId = null;
		if (itemRepository.findItemByItemNumber(itemNumber) != null) {
			timeSlotId = itemRepository.findItemByItemNumber(itemNumber).getCurrentReservationId();
		}

		ItemReservation reservation = itemReservationRepository.findItemReservationByTimeSlotId(timeSlotId);
		reservation.setIsCheckedOut(false);
		reservation.setEndDate(Date.valueOf(LocalDate.now()));
		
		itemReservationRepository.save(reservation);
		return reservation;
	}
	
	@Transactional
	public List<ItemReservation> getItemReservationsByItemNumberAndIdNum(String itemNumber, String idNum) {
		List<ItemReservation> reservations = new ArrayList<ItemReservation>();
		
		for (ItemReservation r : getItemReservationsByIdNum(idNum)) {
	        if (r.getItemNumber().equals(itemNumber)) {
	        	reservations.add(r);
	        }
	    }
		
		if (reservations.size() == 0) {
			throw new IllegalArgumentException("Reservation of that idNum and itemNumber does not exist");			
		} else {
			return reservations;
		}

	}
	
	@Transactional
	public List<ItemReservation> getItemReservationsByItemNumber(String itemNumber) {
	    List<ItemReservation> reservationsByItemNumber = new ArrayList<>();
	    for (ItemReservation r : itemReservationRepository.findItemReservationByItemNumber(itemNumber)) {
	        reservationsByItemNumber.add(r);
	    }
	    return reservationsByItemNumber;
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
