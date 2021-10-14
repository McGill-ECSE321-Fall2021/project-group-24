package ca.mcgill.ecse321.librarysystem.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.model.*;

//import ca.mcgill.ecse321.eventregistration.model.Person;
//import ca.mcgill.ecse321.eventregistration.model.Event;

@Repository
public class LibrarySystemRepository {

  @Autowired
  EntityManager entityManager;
  //GOT TO 2.2.2


	@Transactional
	public Patron createPatronIRL(String idNum, String firstName, String lastName, boolean isResident, String address, String email) {
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
		
		entityManager.persist(p);
		return p;
	}
	
	
	public Patron createPatronOnline(String idNum, String firstName, String lastName, boolean isResident, String address, String email, String username, String password) {
		Patron p = new Patron();
		p.setUsername(username);
		p.setPassword(password);
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.setEmail(email);
		p.setIdNum(idNum);
		p.setIsResident(isResident);
		p.setAddress(address);
		p.setIsVerified(false);
		p.setIsRegisteredOnline(true);

		entityManager.persist(p);
		return p;		
	}
	
	
	public Patron getPatron(String idNum) {
		Patron p = entityManager.find(Patron.class, idNum);
		return p;
	}
	
	
	
  
}
