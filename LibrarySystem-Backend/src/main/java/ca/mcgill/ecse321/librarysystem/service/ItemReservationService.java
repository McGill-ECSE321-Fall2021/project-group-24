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
	@Autowired
	HeadLibrarianRepository headLibrarianRepository;
		
	@Transactional 
	public ItemReservation createItemReservation (String currentUserId,
			   Date startDate,
		 String idNum,
		String itemNumber,
		 boolean isCheckedOut)
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
		String itemReservationId = startDate.toLocalDate().toString() + itemNumber + idNum;
		Date endDate = Date.valueOf(startDate.toLocalDate().plusWeeks(2));
		if (isCheckedOut && librarianRepository.findUserByIdNum(currentUserId) == null && headLibrarianRepository.findUserByIdNum(currentUserId) == null) {
			throw new IllegalArgumentException("Only a librarian can check out a book for a patron");
		}
		if (patronRepository.findUserByIdNum(idNum) == null) {
			System.out.println("id num is" + idNum);
			throw new IllegalArgumentException("Only patrons can create item reservations");
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
			}
		}
		
		if (getCurrentReservationsByIdNum(currentUserId, idNum).size() > 9) {
			throw new IllegalArgumentException("Patron cannot have more than 10 reservations at a time");
		}
		
		
		ItemReservation reservation = new ItemReservation();
		reservation.setItemReservationId(itemReservationId);
		reservation.setStartDate(startDate);
		reservation.setEndDate(endDate);
		reservation.setIdNum(idNum);
		reservation.setItemNumber(itemNumber);
		reservation.setNumOfRenewalsLeft(4);
		reservation.setIsCheckedOut(isCheckedOut);

		itemReservationRepository.save(reservation);
	    return reservation;		
	}
	
	// looks for a reservation with the given itemReservationId, returns them if found
	@Transactional 
	public ItemReservation getItemReservation(String currentUserId,String itemReservationId) {
		
		ItemReservation reservation = itemReservationRepository.findItemReservationByItemReservationId(itemReservationId);
		if (!currentUserId.equals(reservation.getIdNum()) && librarianRepository.findUserByIdNum(currentUserId) == null && headLibrarianRepository.findUserByIdNum(currentUserId) == null) {
			throw new IllegalArgumentException("Only a librarian or the patron who's reservation it is can view a reservation");
		}
		return reservation;
	}
	
	@Transactional 
	public List<ItemReservation> getAllItemReservations(String currentUserId) {
		if (librarianRepository.findUserByIdNum(currentUserId) == null && headLibrarianRepository.findUserByIdNum(currentUserId) == null) {
			throw new IllegalArgumentException("Only a librarian can see all reservations");
		}
		return toList(itemReservationRepository.findAll()); 
	}

	@Transactional
	public List<ItemReservation> getItemReservationsByIdNum(String currentUserId,String idNum) {
		if (!currentUserId.equals(idNum) && librarianRepository.findUserByIdNum(currentUserId) == null && headLibrarianRepository.findUserByIdNum(currentUserId) == null) {
			throw new IllegalArgumentException("Only a librarian or the patron who's reservation it is can view a reservation");
		}
	    List<ItemReservation> reservationsByIdNum = new ArrayList<>();
	    for (ItemReservation r : itemReservationRepository.findItemReservationsByIdNum(idNum)) {
	        reservationsByIdNum.add(r);
	    }
	    return reservationsByIdNum;
	}
	
	@Transactional
	public ItemReservation checkoutItem(String currentUserId,String itemNumber, String idNum) {
		if (librarianRepository.findUserByIdNum(currentUserId) == null && headLibrarianRepository.findUserByIdNum(currentUserId) == null) {
			throw new IllegalArgumentException("Only a librarian can check out a book for a patron");
		}
		ItemReservation currentReservation = null; //find the reservation
		Date today = Date.valueOf(LocalDate.now());
		for (ItemReservation r : getItemReservationsByItemNumberAndIdNum(currentUserId, itemNumber, idNum)) {
			if (r.getStartDate().before(today) && r.getEndDate().after(today) || r.getStartDate().equals(today) && r.getEndDate().after(today) || r.getStartDate().before(today) && r.getEndDate().equals(today)) {
				currentReservation = r;
			}
		}
		Item item = itemRepository.findItemByItemNumber(itemNumber);
		if (item == null) {
			throw new IllegalArgumentException("Item does not exist");
		}
		if (currentReservation == null) {
			return createItemReservation(currentUserId, today, idNum, itemNumber, true);
		} else if (!currentReservation.getIdNum().equals(idNum)) {
			throw new IllegalArgumentException("No reservation at this time for this patron");
		} else if (patronRepository.findUserByIdNum(idNum).getIsVerified()) {
			throw new IllegalArgumentException("Must verify patron before checking out books");
		} else if (currentReservation.getIsCheckedOut()) {
			throw new IllegalArgumentException("Item is already checked out");
		}
		currentReservation.setEndDate(Date.valueOf(LocalDate.now().plusWeeks(2)));
		currentReservation.setIsCheckedOut(true);
		item.setCurrentReservationId(currentReservation.getItemReservationId());
		itemRepository.save(item);
		itemReservationRepository.save(currentReservation);
		return currentReservation;
	}
	
	@Transactional
	public boolean cancelItemReservation(String currentUserId,String itemReservationId) {
		ItemReservation reservation = itemReservationRepository.findItemReservationByItemReservationId(itemReservationId);
		if (!currentUserId.equals(reservation.getIdNum()) && librarianRepository.findUserByIdNum(currentUserId) == null && headLibrarianRepository.findUserByIdNum(currentUserId) == null) {
			throw new IllegalArgumentException("Only a librarian or the patron who's reservation it is can delete it");
		}
		if (reservation.getIsCheckedOut() == false) {
			itemReservationRepository.delete(reservation);
			return true;
		} else {
			throw new IllegalArgumentException("Cannot cancel reservation for item that is already checked out");
		}
	}
	
	@Transactional
	public ItemReservation returnItemFromReservation(String currentUserId,String itemNumber) {
		String itemReservationId = null;
		Item item = itemRepository.findItemByItemNumber(itemNumber);
		if (librarianRepository.findUserByIdNum(currentUserId) == null && headLibrarianRepository.findUserByIdNum(currentUserId) == null) {
			throw new IllegalArgumentException("Only a librarian can return an item to the library");
		}
		itemReservationId = item.getCurrentReservationId();
		
		item.setCurrentReservationId(null);
		itemRepository.save(item);
		if (itemReservationId != null ) {
			ItemReservation reservation = itemReservationRepository.findItemReservationByItemReservationId(itemReservationId);
			reservation.setIsCheckedOut(false);
			reservation.setEndDate(Date.valueOf(LocalDate.now()));
			
			itemReservationRepository.save(reservation);
			return reservation;
		} else {
			return null;
		}
		
	}
	
	@Transactional
	public List<ItemReservation> getItemReservationsByItemNumberAndIdNum(String currentUserId,String itemNumber, String idNum) {
		List<ItemReservation> reservations = new ArrayList<ItemReservation>();
		if (!currentUserId.equals(idNum) && librarianRepository.findUserByIdNum(currentUserId) == null && headLibrarianRepository.findUserByIdNum(currentUserId) == null) {
			throw new IllegalArgumentException("Only a librarian or the patron who's reservation they are can see them");
		}
		for (ItemReservation r : getCurrentReservationsByIdNum(currentUserId, idNum)) {
	        if (r.getItemNumber().equals(itemNumber)) {
	        	reservations.add(r);
	        }
	    }
		
		return reservations;

	}
	
	@Transactional
	public List<ItemReservation> getItemReservationsByItemNumber(String currentUserId,String itemNumber) {
		return itemReservationRepository.findItemReservationsByItemNumber(itemNumber);
	}
	
	@Transactional
	public ItemReservation renewByItemReservationId(String currentUserId,String itemReservationId) {
		ItemReservation reservation = getItemReservation(currentUserId, itemReservationId);
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
	
	public List<ItemReservation> getCurrentReservationsByIdNum(String currentUserId,String idNum) {
		Date today = Date.valueOf(LocalDate.now());
		List<ItemReservation> currentReservationsByPatron = new ArrayList<ItemReservation>();
		if (!currentUserId.equals(idNum) && librarianRepository.findUserByIdNum(currentUserId) == null && headLibrarianRepository.findUserByIdNum(currentUserId) == null) {
			throw new IllegalArgumentException("Only a librarian or the patron who's reservation they are can see them");
		}
		for (ItemReservation reservation : getItemReservationsByIdNum(currentUserId, idNum)) {
			if (reservation.getStartDate().after(today) || reservation.getStartDate().equals(today) || reservation.getEndDate().after(today) || (reservation.getIsCheckedOut() == true && reservation.getEndDate().equals(today))) {
				currentReservationsByPatron.add(reservation);
			}
		}
		return currentReservationsByPatron;
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
