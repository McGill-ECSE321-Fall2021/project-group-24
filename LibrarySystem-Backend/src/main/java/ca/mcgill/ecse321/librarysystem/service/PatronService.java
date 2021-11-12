package ca.mcgill.ecse321.librarysystem.service;

import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.HeadLibrarianRepository;
import ca.mcgill.ecse321.librarysystem.dao.LibrarianRepository;
import ca.mcgill.ecse321.librarysystem.dao.PatronRepository;
import ca.mcgill.ecse321.librarysystem.model.HeadLibrarian;
import ca.mcgill.ecse321.librarysystem.model.ItemReservation;
import ca.mcgill.ecse321.librarysystem.model.Librarian;
import ca.mcgill.ecse321.librarysystem.model.Patron;
import ca.mcgill.ecse321.librarysystem.model.RoomBooking;


@Service
public class PatronService {
//written by Nafis
	
	
	@Autowired
	LibrarianRepository librarianRepository;
	@Autowired
	HeadLibrarianRepository headLibrarianRepository;
	@Autowired
	PatronRepository patronRepository;
	@Autowired
	ItemReservationService itemReservationService;
	@Autowired
	RoomBookingService roomBookingService;
	
	
	//for accounts created in person by a librarian
	@Transactional
	public Patron createPatronIRL(
		    String firstName,
		    String lastName,
		    boolean isResident,
		    String address
		  ) {
		
			String idNum = firstName+"Patron-"+toList(patronRepository.findAll()).size();
			
			addressIsValid(address);
			nameIsValid(firstName, lastName);
			
		    Patron p = new Patron();
		    p.setUsername(null);
		    p.setFirstName(firstName);
		    p.setLastName(lastName);
		    p.setIdNum(idNum);
		    p.setIsResident(isResident);
		    p.setIsRegisteredOnline(false);
		    p.setEmail(null);
		    p.setAddress(address);
		    p.setIsVerified(true);
		    p.setPassword(null);

		    patronRepository.save(p);
		    return p;
	}
	
	
	
	//for accounts created online
	@Transactional
	public Patron createPatronOnline(
			String username,
			String password,
		    String firstName,
		    String lastName,
		    boolean isResident,
		    String address,
		    String email){
		
		if(username==null||username=="")throw new IllegalArgumentException("Username cannot be empty.");
		if(password==null||password == "") throw new IllegalArgumentException("Password cannot be empty.");
		if(password.contains(" ")==true) throw new IllegalArgumentException("Password cannot have spaces in it.");
		if(username.contains(" ")==true) throw new IllegalArgumentException("Username cannot have spaces in it.");
		
		String idNum = firstName+"Patron-"+toList(patronRepository.findAll()).size();
		nameIsValid(firstName, lastName);
		usernameIsValid(username);
		idIsValid(idNum);
		passwordIsValid(password);
		emailIsValid(email);
		
		Patron p = new Patron();
		p.setUsername(username);
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.setIdNum(idNum);
		p.setAddress(address);
		p.setEmail(email);
		p.setPassword(password);
		p.setIsRegisteredOnline(true);
		p.setIsVerified(false);
		p.setIsResident(isResident);
		
		patronRepository.save(p);
		return p;
		
	}
	/***
	 * @author Saagar
	 * deletes a patron by idNum
	 * @param idNum
	 * @param currentUserId
	 * @return true if deleted
	 */
	@Transactional
	public boolean deletePatron(String idNum, String currentUserId) {
		Librarian currentLibrarian = librarianRepository.findUserByIdNum(currentUserId);
		HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(currentUserId);
		Patron patron = patronRepository.findPatronByIdNum(idNum);
		if (currentUserId.equals(idNum) && !patron.getIsLoggedIn()
				|| currentLibrarian != null && !currentLibrarian.getIsLoggedIn()
				|| currentHeadLibrarian != null && !currentHeadLibrarian.getIsLoggedIn()) {
			throw new IllegalArgumentException("Only a librarian or the patron themselves can delete a patron account");
		} 
		if (librarianRepository.findUserByIdNum(idNum) != null || headLibrarianRepository.findUserByIdNum(idNum) != null) {
			throw new IllegalArgumentException("Cannot delete a librarian this way");
		}
		List<ItemReservation> reservations = itemReservationService.getItemReservationsByIdNum(currentUserId, idNum);
		//check to see if there are any checked out items
		for (ItemReservation reservation : reservations) {
			if (reservation.getIsCheckedOut()) {
				throw new IllegalArgumentException("Cannot delete account until item" + reservation.getItemNumber() + "is returned");
			}
		}
		Date today = Date.valueOf(LocalDate.now());
		for (ItemReservation reservation : reservations) {
			if (reservation.getStartDate().after(today) || reservation.getStartDate().equals(today)) {
				itemReservationService.cancelItemReservation(currentUserId, reservation.getItemReservationId());
			}
		}
		for (RoomBooking booking : roomBookingService.getRoomBookingsByIdNum(currentUserId, idNum)) {
			if (booking.getDate().after(today) || booking.getDate().equals(today)) {
				roomBookingService.deleteRoomBooking(currentUserId, booking.getTimeSlotId());
			}
		}
		patronRepository.delete(patron);
		return true;
	}
	
	
	
	
	//does what method name says
	@Transactional
	public Patron getPatronAccountByUsername(String username) {
		if(username.length()==0) throw new IllegalArgumentException("Invalid username entered.");
		Patron p = patronRepository.findPatronByUsername(username);
		if(p == null) throw new IllegalArgumentException("User with this username does not exist.");
		return p;
	}
	
	
	//does what method name says
	@Transactional
	public Patron getPatronAccountByID(String ID) {
		if(ID.length()==0) throw new IllegalArgumentException("Invalid ID entered.");
		Patron p = patronRepository.findPatronByIdNum(ID);
		if(p == null) throw new IllegalArgumentException("User with this ID does not exist.");
		return p;
	}
	
	
	//returns a list of all patrons in the database
	@Transactional
	public List<Patron> getAllPatrons(){
		return toList(patronRepository.findAll());
	}

	
	
	

	
	
	
	
	//HELPER METHODS//
	
	
	
	

	public boolean nameIsValid(String firstName, String lastName) {
		if(firstName == null || firstName == "" || lastName == null || lastName == "") {
			throw new IllegalArgumentException("First name or last name cannot be blank.");
		}else {
			return true;
		}
		
	}
	
	
	
	public boolean usernameIsValid(String username) {
		Patron p = patronRepository.findPatronByUsername(username);
		if(patronRepository.findPatronByUsername(username)==null) {
			return true;
		}else {
			throw new IllegalArgumentException("Username is already taken.");
		}
	}
	
	public boolean idIsValid(String id) {
		if(id==null || id == "") throw new IllegalArgumentException("ID cannot be empty.");
		Patron p = patronRepository.findPatronByIdNum(id);
		if(p==null) {
			return true;
		}else {
			throw new IllegalArgumentException("Invalid ID.");
		}
	}
	
	public boolean passwordIsValid(String pass) {
		if(pass.length()<8) {
			throw new IllegalArgumentException("Password must be at least 8 characters long.");
		}else {
			return true;
		}
	}
	
	public boolean addressIsValid(String address) {
		if (address==null||address=="") {
			throw new IllegalArgumentException("Address cannot be empty.");
		}else {
			return true;
		}
	}
	
	
	public boolean emailIsValid(String email) {
		if (email == null||email == "" || !(email.contains("@")) || !(email.contains(".")) ) {
			throw new IllegalArgumentException("Email cannot be empty and it must include '@' and '.' symbols.");
		}else {
			return true;
		}
	}
	
	
	public <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for(T t: iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
