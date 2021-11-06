package ca.mcgill.ecse321.librarysystem.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
		 String idNum,
		String itemNumber,
		int numOfRenewalsLeft, boolean isCheckedOut)
	{
		if (startDate == null) {
			startDate = findNextAvailabilityForItem(itemNumber);
			//if they are at the library rn and the book is available 
			if (isCheckedOut && startDate.equals(Date.valueOf(LocalDate.now().plusDays(1))) ) {
				startDate = Date.valueOf(LocalDate.now());
			//if they are at the library rn but the book should not be available
			} else if (isCheckedOut) {
				throw new IllegalArgumentException("Book has a future reservation");
			}
		}
		Date endDate = Date.valueOf(startDate.toLocalDate().plusWeeks(2));
		if (patronRepository.findUserByIdNum(idNum) == null && librarianRepository.findUserByIdNum(idNum) == null) {
			System.out.println("id num is" + idNum);
			throw new IllegalArgumentException("Invalid idNum");
		}
		
		if (itemRepository.findItemByItemNumber(itemNumber) == null) {
				throw new IllegalArgumentException("The item does not exist");
			}
		
		List<ItemReservation> currentItemReservations = itemReservationRepository.findItemReservationsByItemNumber(itemNumber);
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
		reservation.setStartTime(Time.valueOf(LocalTime.of(0, 0)));
		reservation.setEndDate(endDate);
		reservation.setEndTime(Time.valueOf(LocalTime.of(23, 59)));
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
	    for (ItemReservation r : itemReservationRepository.findItemReservationsByIdNum(idNum)) {
	        reservationsByIdNum.add(r);
	    }
	    return reservationsByIdNum;
	}
	
	@Transactional
	public ItemReservation checkoutItem(String itemNumber, String idNum) {
		ItemReservation currentReservation = null; //find the reservation
		Date today = Date.valueOf(LocalDate.now());
		for (ItemReservation r : getItemReservationsByItemNumberAndIdNum(itemNumber, idNum)) {
			if (r.getStartDate().before(today) && r.getEndDate().after(today) || r.getStartDate().equals(today) && r.getEndDate().after(today) || r.getStartDate().before(today) && r.getEndDate().equals(today)) {
				currentReservation = r;
			}
		}
		if (currentReservation == null) {
			//create reservation?
			return null;
		} else if (!currentReservation.getIdNum().equals(idNum)) {
			throw new IllegalArgumentException("No reservation at this time for this patron");
		} else if (patronRepository.findUserByIdNum(idNum).getIsVerified()) {
			throw new IllegalArgumentException("Must verify patron before checking out books");
		}
		currentReservation.setEndDate(Date.valueOf(LocalDate.now().plusWeeks(2)));
		currentReservation.setIsCheckedOut(true);
		Item item = itemRepository.findItemByItemNumber(currentReservation.getItemNumber());
		item.setCurrentReservationId(currentReservation.getTimeSlotId());
		itemRepository.save(item);
		itemReservationRepository.save(currentReservation);
		return currentReservation;
	}
	
	@Transactional
	public boolean cancelItemReservation(String timeSlotId) {
		ItemReservation reservation = itemReservationRepository.findItemReservationByTimeSlotId(timeSlotId);
		if (reservation.getIsCheckedOut() == false) {
			itemReservationRepository.delete(reservation);
			return true;
		} else {
			throw new IllegalArgumentException("Cannot cancel reservation for item that is already checked out");
		}
	}
	
	@Transactional
	public ItemReservation returnItemFromReservation(String itemNumber) {
		String timeSlotId = null;
		Item item = itemRepository.findItemByItemNumber(itemNumber);
		if (item != null) {
			timeSlotId = item.getCurrentReservationId();
		}
		item.setCurrentReservationId(null);
		itemRepository.save(item);
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
		return itemReservationRepository.findItemReservationsByItemNumber(itemNumber);
	}
	
	@Transactional
	public ItemReservation renewByTimeSlotId(String timeSlotId) {
		ItemReservation reservation = getItemReservation(timeSlotId);
		if (reservation.getNumOfRenewalsLeft() > 0) {
			Date nextAvailable = findNextAvailabilityForItem(reservation.getItemNumber());
			//if this is the last reservation
			if (Date.valueOf(nextAvailable.toLocalDate().minusDays(1)).equals(reservation.getEndDate())) {
				reservation.setNumOfRenewalsLeft(reservation.getNumOfRenewalsLeft() - 1);
				reservation.setEndDate(Date.valueOf(reservation.getEndDate().toLocalDate().plusWeeks(2)));
				itemReservationRepository.save(reservation);
				return reservation;
			} else {
				throw new IllegalArgumentException("There is another reservation after yours");
			}
		} else {
			throw new IllegalArgumentException("No more renewals left");
		}
	}
	
	@Transactional
	public Date findNextAvailabilityForItem(String itemNumber) {
		List<ItemReservation> reservationsByItemNumber = itemReservationRepository.findItemReservationsByItemNumber(itemNumber);
		ItemReservation latestReservation = null;
		for (ItemReservation r : reservationsByItemNumber) {
			if (latestReservation == null) {
				latestReservation = r;
			} else if (latestReservation.getEndDate().before(r.getStartDate())) {
				latestReservation = r;
			}
		}
		if (latestReservation == null) {
			return Date.valueOf(LocalDate.now().plusDays(1));
		} else {
			return Date.valueOf(latestReservation.getEndDate().toLocalDate().plusDays(1));
		}
	}

	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
