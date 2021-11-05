package ca.mcgill.ecse321.librarysystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;

@Service
public class LibrarianService {
	
	@Autowired 
	LibrarianRepository librarianRepo; 
	@Autowired
	ShiftRepository shiftRepo; 
		
	/** 
	 * @author Arman
	 * @param idNum, firstName, lastName, address, email, username, password
	 * @return the librarian if it is successfully created 
	 */
	@Transactional 
	public Librarian createLibrarian(
			String idNum,
		    String firstName,
		    String lastName,
		    String address,
		    String email,
		    String username,
		    String password) 
	{
		if (idNum == null || firstName == null || lastName == null || address == null || email == null || username == null || password==null) {
			throw new IllegalArgumentException("Fields cannot be blank"); 
		}
		Librarian librarian = new Librarian();
	    librarian.setUsername(username);
	    librarian.setPassword(password);
	    librarian.setFirstName(firstName);
	    librarian.setLastName(lastName);
	    librarian.setEmail(email);
	    librarian.setIdNum(idNum);
	    librarian.setAddress(address);
	    librarianRepo.save(librarian);
	    return librarian;		
	}
	
	/** 
	 * @author Arman
	 * @param idNum
	 * @return the librarian if it is successfully created 
	 */
	public Librarian createLibrarian(String idNum) {
		if (idNum == null) throw new IllegalArgumentException("Librarian ID cannot be blank"); 

		Librarian librarian = new Librarian(); 
		librarian.setIdNum(idNum);
	    librarian.setUsername("UN");
	    librarian.setPassword("PS");
	    librarian.setFirstName("FN");
	    librarian.setLastName("LN");
	    librarian.setEmail("EM");
	    librarian.setAddress("AD");
	    
		librarianRepo.save(librarian); 
		return librarian;
	}
	
	/** 
	 * @author Arman
	 * @param idNum
	 * @return librarian if found, null otherwise. 
	 */
	@Transactional 
	public Librarian getLibrarian(String idNum) {
		if (idNum == null) throw new IllegalArgumentException("Librarian ID cannot be blank"); 
		Librarian librarian = librarianRepo.findUserByIdNum(idNum); 
		if (librarian == null) throw new IllegalArgumentException("Cannot find librarian"); 
		return librarian;
	}
	
	/** 
	 * @author Arman
	 * @return list of all librarians
	 */
	
	@Transactional 
	public List<Librarian> getAllLibrarians() {
		return toList(librarianRepo.findAll()); 
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
