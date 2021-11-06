package ca.mcgill.ecse321.librarysystem.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;

@Service
public class PatronService {
	
	@Autowired 
	PatronRepository patronRepo; 
	ItemReservationRepository reservationRepo;
		
	// creates patron, returns it so we know it's not null 
	@Transactional 
	public Patron createPatron(
			String idNum,
		    String firstName,
		    String lastName,
		    String address,
		    String email,
		    String username,
		    String password,
		     boolean isVerified,
			   boolean isResident,
			   boolean isRegisteredOnline) 
	{
		Patron patron = new Patron();
	    patron.setUsername(username);
	    patron.setPassword(password);
	    patron.setFirstName(firstName);
	    patron.setLastName(lastName);
	    patron.setEmail(email);
	    patron.setIdNum(idNum);
	    patron.setAddress(address);
	    patron.setIsVerified(isVerified);
	    patron.setIsRegisteredOnline(isRegisteredOnline);
	    patron.setIsResident(isResident);

	    patronRepo.save(patron);
	    return patron;		
	}
	public Patron createPatron(String idNum) {
		Patron patron = new Patron();
	    patron.setUsername("user name");
	    patron.setPassword("password");
	    patron.setFirstName("first name");
	    patron.setLastName("last name");
	    patron.setEmail("email");
	    patron.setIdNum(idNum);
	    patron.setAddress("address");
	    patron.setIsVerified(false);
	    patron.setIsRegisteredOnline(false);
	    patron.setIsResident(true);
	    
	    patronRepo.save(patron); 
		return patron;
	}
	
	// looks for a patron with the given ID number, returns them if found
	@Transactional 
	public Patron getPatron(String idNum) {
		Patron patron = patronRepo.findUserByIdNum(idNum); 
		return patron;
	}
	
	@Transactional 
	public List<Patron> getAllPatrons() {
		return toList(patronRepo.findAll()); 
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	@Transactional
	public List<ItemReservation> getItemReservationsByPatron(String idNum) {
	    if (getPatron(idNum) == null ) {
	        throw new IllegalArgumentException("Patron cannot be null!");
	    }
	    List<ItemReservation> reservationsByPatron = new ArrayList<>();
	    for (ItemReservation r : reservationRepo.findItemReservationsByIdNum(idNum)) {
	        reservationsByPatron.add(r);
	    }
	    return reservationsByPatron;
	}
}
