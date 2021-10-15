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
private String itemNumber;

  	// by Nafis, Saggar, Selena
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
	
	@Transactional
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
	
	@Transactional
	public Patron getPatron(String idNum) {
		Patron p = entityManager.find(Patron.class, idNum);
		return p;
	}
	
	// by Saagar	
	@Transactional
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
	
	
	@Transactional
	public Librarian getLibrarian(String idNum) {
		Librarian l = entityManager.find(Librarian.class, idNum);
		return l;
	}
	
	
	@Transactional
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
	
	@Transactional
	public HeadLibrarian getHeadLibrarian(String idNum) {
		HeadLibrarian h = entityManager.find(HeadLibrarian.class, idNum);
		return h;
	}

	
	@Transactional
	public Room createRoom(String roomNum, int capacity) {
		Room r = new Room();
		r.setCapacity(capacity);
		r.setRoomNum(roomNum);
		
		entityManager.persist(r);
		return r;
	}
	
	@Transactional
	public Room getRoom(String roomNum) {
		Room r = entityManager.find(Room.class, roomNum);
		return r;
	}
	
	
	// by Selena
	@Transactional
	public Book createBook(String itemTitle, String description, String imageURL,String publisher, String author, boolean isCheckedOut, String genre, Date publishDate, boolean isReservable) {
		Book b = new Book();
		b.setItemTitle(itemTitle);
		b.setDescription(description);
		b.setAuthor(author);
		b.setGenre(genre);
		b.setIsCheckedOut(isCheckedOut);
		b.setPublishDate(publishDate);
		b.setItemNumber(itemNumber);
		b.setPublisher(publisher);
		b.setIsReservable(isReservable); // should this be true?
		entityManager.persist(b);
		return b;
	}
	
	@Transactional
	public Book getBook(String itemNumber) {
		Book book = entityManager.find(Book.class, itemNumber);
		return book;
	}
	
	@Transactional
	public MusicAlbum createMusicAlbum(String description, String genre, String imageUrl, String itemTitle, Date publishDate, boolean isCheckedOut, String recordingLabel, String artist) {
		MusicAlbum m = new MusicAlbum();
		m.setDescription(description);
		m.setGenre(genre);
		m.setImageUrl(imageUrl);
		m.setIsCheckedOut(isCheckedOut);
		m.setIsReservable(true);
		m.setItemNumber(itemNumber);
		m.setItemTitle(itemTitle);
		m.setPublishDate(publishDate);
		m.setRecordingLabel(recordingLabel);
		m.setArtist(artist);
		entityManager.persist(m);
		return m;
	}
	
	@Transactional
	public MusicAlbum getMusicAlbum(String itemNumber) {
		MusicAlbum musicAlbum = entityManager.find(MusicAlbum.class, itemNumber);
		return musicAlbum;
	}

	@Transactional
	public Movie createMovie(String description, String genre, String imageUrl, String itemTitle, Date publishDate, String productionCompany, String cast, String director, boolean isCheckedOut) {
		Movie m = new Movie();
		m.setDescription(description);
		m.setGenre(genre);
		m.setImageUrl(imageUrl);
		m.setIsCheckedOut(isCheckedOut);
		m.setIsReservable(true);
		m.setItemNumber(itemNumber);
		m.setItemTitle(itemTitle);
		m.setPublishDate(publishDate);
		m.setProductionCompany(productionCompany);
		m.setCast(cast);
		m.setDirector(director);
		entityManager.persist(m);
		return m;
	}
	
	@Transactional
	public Movie getMovie(String itemNumber) {
		Movie movie = entityManager.find(Movie.class, itemNumber);
		return movie;
	}

	@Transactional
	public PrintedMedia createPrintedMedia(String description, String genre, String imageUrl, String itemTitle, Date publishDate, String issueNumber, boolean isCheckedOut) {
		PrintedMedia pm = new PrintedMedia();
		pm.setDescription(description);
		pm.setGenre(genre);
		pm.setImageUrl(imageUrl);
		pm.setIsCheckedOut(isCheckedOut);
		pm.setIsReservable(true);
		pm.setItemNumber(itemNumber);
		pm.setItemTitle(itemTitle);
		pm.setPublishDate(publishDate);
		pm.setIssueNumber(issueNumber);
		entityManager.persist(pm);
		return pm;
	}
	
	@Transactional
	public PrintedMedia getPrintedMedia(String itemNumber) {
		PrintedMedia item = entityManager.find(PrintedMedia.class, itemNumber);
		return item;
	}

	@Transactional
	public Archive createArchive(String description, String genre, String imageUrl, String itemTitle, Date publishDate) {
		Archive a = new Archive();
		a.setDescription(description);
		a.setGenre(genre);
		a.setImageUrl(imageUrl);
		a.setIsCheckedOut(false);
		a.setIsReservable(false);
		a.setItemNumber(itemNumber);
		a.setItemTitle(itemTitle);
		a.setPublishDate(publishDate);
		entityManager.persist(a);
		return a;
		
	}
	
	@Transactional
	public Archive getArchive(String itemNumber) {
		Archive archive = entityManager.find(Archive.class, itemNumber);
		return archive;
	}
	
	//by Nafis
	@Transactional
	public RoomBooking createRoomBooking(String bookingID, Room room, Patron patron) {
		RoomBooking roombooking = new RoomBooking();
		roombooking.setBookingID(bookingID);
		roombooking.setRoom(room);
		roombooking.setPatron(patron);
		return roombooking;
	}
	
	@Transactional
	public RoomBooking getRoomBooking(String bookingID) {
		RoomBooking roombooking = entityManager.find(RoomBooking.class, bookingID);
		return roombooking;
	}
	
	@Transactional
	public ItemReservation createItemReservation(Date startDate, Date endDate, Time startTime, Time endTime, String reservationID, String itemNumber, Patron patron) {
		ItemReservation reservation = new ItemReservation();
		reservation.setStartDate(startDate);
		reservation.setEndDate(endDate);
		reservation.setStartTime(startTime);
		reservation.setEndTime(endTime);
		reservation.setReservationID(reservationID);
		reservation.setNumOfRenewalsLeft(2);
		reservation.setPatron(patron);
		reservation.setItemNumber(itemNumber);
		return reservation;
	}
	
	@Transactional
	public ItemReservation getItemReservation(String reservationID) {
		ItemReservation itemReservation = entityManager.find(ItemReservation.class, reservationID);
		return itemReservation;
	}
	
	
	@Transactional
	public Shift createShift(Date startDate, Date endDate, Time startTime, Time endTime) {
		//add TimeSlotID
		Shift shift = new Shift();
		shift.setStartDate(startDate);
		shift.setEndDate(endDate);
		shift.setStartTime(startTime);
		shift.setEndTime(endTime);
		return shift;
	}
	
//	@Transactional
//	public Shift getShift() {
//		
//	}
	
	@Transactional
	public LibraryHour createLibraryHour(Date startDate, Date endDate, Time startTime, Time endTime) {
		//add timeSlotID after Arman pushes changes to model
		LibraryHour libHour = new LibraryHour();
		libHour.setStartDate(startDate);
		libHour.setEndDate(endDate);
		libHour.setStartTime(startTime);
		libHour.setEndTime(endTime);
		return libHour;
	}
	
//	@Transactional
//	public LibraryHour getLibraryHour{
//		write after Arman pushes changes made to model
//	}
	
	
	
}
