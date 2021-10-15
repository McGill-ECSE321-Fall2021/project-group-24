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
	
	public Librarian createLibrarian(String idNum, String firstName, String lastName, boolean isResident, String address, String email, String username, String password) {
		Librarian l = new Librarian();
		l.setUsername(username);
		l.setPassword(password);
		l.setFirstName(firstName);
		l.setLastName(lastName);
		l.setEmail(email);
		l.setIdNum(idNum);

		entityManager.persist(l);
		return l;		
	}
	
	
	public Librarian getLibrarian(String idNum) {
		Librarian l = entityManager.find(Librarian.class, idNum);
		return l;
	}
	
	public HeadLibrarian createHeadLibrarian(String idNum, String firstName, String lastName, boolean isResident, String address, String email, String username, String password) {
		HeadLibrarian h = new HeadLibrarian();
		h.setUsername(username);
		h.setPassword(password);
		h.setFirstName(firstName);
		h.setLastName(lastName);
		h.setEmail(email);
		h.setIdNum(idNum);

		entityManager.persist(h);
		return h;		
	}
	
	
	public HeadLibrarian getHeadLibrarian(String idNum) {
		HeadLibrarian h = entityManager.find(HeadLibrarian.class, idNum);
		return h;
	}
	
	public Room createRoom(String roomNum, int capacity) {
		Room r = new Room();
		r.setCapacity(capacity);
		r.setRoomNum(roomNum);
		
		entityManager.persist(r);
		return r;
	}
	
	public Room getRoom(String roomNum) {
		Room r = entityManager.find(Room.class, roomNum);
		return r;
	}
  
}
