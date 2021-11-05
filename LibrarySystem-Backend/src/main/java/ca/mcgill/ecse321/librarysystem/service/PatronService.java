package ca.mcgill.ecse321.librarysystem.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.PatronRepository;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import ca.mcgill.ecse321.librarysystem.model.Patron;
import ca.mcgill.ecse321.librarysystem.model.User;


@Service
public class PatronService {
//written by Nafis
	
	
	
	
	@Autowired
	PatronRepository patronRepository;
	
	
	//for accounts created in person by a librarian
	@Transactional
	public Patron createPatronIRL(
		    String idNum,
		    String firstName,
		    String lastName,
		    boolean isResident,
		    String address,
		    String email
		  ) {
			
		
		    Patron p = new Patron();
		    p.setFirstName(firstName);
		    p.setLastName(lastName);
		    p.setIdNum(idNum);
		    p.setIsResident(isResident);
		    p.setIsRegisteredOnline(false);
		    p.setEmail(email);
		    p.setAddress(address);
		    p.setIsVerified(true);
		    p.setPassword(null);
		    p.setUsername(null);

		    patronRepository.save(p);
		    return p;
	}
	
	
	
	//for accounts created online
	@Transactional
	public Patron createPatronOnline(
			String idNum,
		    String firstName,
		    String lastName,
		    boolean isResident,
		    String address,
		    String email,
		    String username,
		    String password){
		
		if(username==null||username=="")throw new IllegalArgumentException("Username cannot be empty.");
		if(password==null||password == "") throw new IllegalArgumentException("Password cannot be empty.");
		
		nameIsValid(firstName, lastName);
		usernameIsValid(username);
		idIsValid(idNum);
		passwordIsValid(password);
		
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
		
		patronRepository.save(p);
		return p;
		
	}
	
	
	//does what method name says
	@Transactional
	public Patron getPatronAccountByUsername(String username) {
		if(username.length()==0) throw new IllegalArgumentException("Invalid username entered.");
		Patron p = patronRepository.findPatronByUsername(username);
		return p;
	}
	
	
	//does what method name says
	@Transactional
	public Patron getPatronAccountByID(String ID) {
		if(ID.length()==0) throw new IllegalArgumentException("Invalid ID entered.");
		Patron p = patronRepository.findUserByIdNum(ID);
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
		if(patronRepository.findPatronByUsername(username)==null) {
			return true;
		}else {
			throw new IllegalArgumentException("Username is already taken.");
		}
	}
	
	public boolean idIsValid(String id) {
		if(id==null || id == "") throw new IllegalArgumentException("ID cannot be empty.");
		if(patronRepository.findById(id)==null) {
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
		if (email == null||email == "") {
			throw new IllegalArgumentException("Email cannot be empty.");
		}else {
			return true;
		}
	}
	
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for(T t: iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
