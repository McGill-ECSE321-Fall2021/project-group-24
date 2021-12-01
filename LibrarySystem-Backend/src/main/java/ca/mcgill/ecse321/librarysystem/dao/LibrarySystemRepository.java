package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//import ca.mcgill.ecse321.eventregistration.model.Person;
//import ca.mcgill.ecse321.eventregistration.model.Event;

@Repository
public class LibrarySystemRepository {

  @Autowired
  EntityManager entityManager;

  // by Nafis, Saggar, Selena
  //create a patron in person. isVerified == true as they will show up with their proof of address, or payment.
  @Transactional
  public Patron createPatronIRL(
    String idNum,
    String firstName,
    String lastName,
    boolean isResident,
    String address,
    String username,
    String email
  ) {
    Patron p = new Patron();
    p.setFirstName(firstName);
    p.setLastName(lastName);
    p.setIsResident(isResident);
    p.setIsRegisteredOnline(false);
    p.setEmail(email);
    p.setAddress(address);
    p.setIsVerified(true);
    p.setPassword(null);
    p.setUsername(username);

    entityManager.persist(p);
    return p;
  }

  //this lets a new patron create an account online. isVerified == false as they have not paid or showed their proof of address.
  //in order to take a book out of the library, they will have to get verified by a librarian.
  @Transactional
  public Patron createPatronOnline(
    String idNum,
    String firstName,
    String lastName,
    boolean isResident,
    String address,
    String email,
    String username,
    String password
  ) {
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

  //getPatron returns the patron for the idNum that was passed as a parameter
  @Transactional
  public Patron getPatron(String idNum) {
    Patron p = entityManager.find(Patron.class, idNum);
    return p;
  }

  // by Saagar
  //create a new librarian
  @Transactional
  public Librarian createLibrarian(
    String idNum,
    String firstName,
    String lastName,
    String address,
    String email,
    String username,
    String password
  ) {
    Librarian l = new Librarian();
    l.setUsername(username);
    l.setPassword(password);
    l.setFirstName(firstName);
    l.setLastName(lastName);
    l.setEmail(email);
    l.setIdNum(idNum);
    l.setAddress(address);

    entityManager.persist(l);
    return l;
  }

  //getLibrarian returns the patron for the idNum that was passed as a parameter
  @Transactional
  public Librarian getLibrarian(String idNum) {
    Librarian l = entityManager.find(Librarian.class, idNum);
    return l;
  }

  @Transactional
  //create a head librarian
  public HeadLibrarian createHeadLibrarian(
    String idNum,
    String firstName,
    String lastName,
    boolean isResident,
    String address,
    String email,
    String username,
    String password
  ) {
    HeadLibrarian h = new HeadLibrarian();
    h.setUsername(username);
    h.setPassword(password);
    h.setFirstName(firstName);
    h.setLastName(lastName);
    h.setEmail(email);
    h.setIdNum(idNum);
    h.setAddress(address);

    entityManager.persist(h);
    return h;
  }

  @Transactional
  //getHeadLibrarian returns the head librarian for the idNum that was passed as a parameter
  public HeadLibrarian getHeadLibrarian(String idNum) {
    HeadLibrarian h = entityManager.find(HeadLibrarian.class, idNum);
    return h;
  }

  @Transactional
  //create a room with a unique number, and a  capacity
  public Room createRoom(String roomNum, int capacity) {
    Room r = new Room();
    r.setCapacity(capacity);
    r.setRoomNum(roomNum);

    entityManager.persist(r);
    return r;
  }

  @Transactional
  //getRoom returns the room for the roomNum that was passed as a parameter
  public Room getRoom(String roomNum) {
    Room r = entityManager.find(Room.class, roomNum);
    return r;
  }

  // by Selena
  //create a new book
  @Transactional
  public Book createBook(
    String itemTitle,
    String description,
    String imageURL,
    String publisher,
    String author,
    boolean isCheckedOut,
    String genre,
    Date publishDate,
    boolean isReservable,
    String itemNumber,
    String currentReservationId
  ) {
    Book b = new Book();
    b.setItemTitle(itemTitle);
    b.setDescription(description);
    b.setAuthor(author);
    b.setGenre(genre);
    b.setCurrentReservationId(currentReservationId);
    b.setPublishDate(publishDate);
    b.setItemNumber(itemNumber);
    b.setPublisher(publisher);
    b.setIsReservable(isReservable); // should this be true?
    entityManager.persist(b);
    return b;
  }

  @Transactional
  //getBook returns the book for the itemNumber that was passed as a parameter
  public Book getBook(String itemNumber) {
    Book book = entityManager.find(Book.class, itemNumber);
    return book;
  }

  @Transactional
  //create a new music album
  public MusicAlbum createMusicAlbum(
    String description,
    String genre,
    String imageUrl,
    String itemTitle,
    Date publishDate,
    String currentReservationId,
    String recordingLabel,
    String artist,
    String itemNumber
  ) {
    MusicAlbum m = new MusicAlbum();
    m.setDescription(description);
    m.setGenre(genre);
    m.setImageUrl(imageUrl);
    m.setCurrentReservationId(currentReservationId);
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
  //return a music album with its itemNumber
  public MusicAlbum getMusicAlbum(String itemNumber) {
    MusicAlbum musicAlbum = entityManager.find(MusicAlbum.class, itemNumber);
    return musicAlbum;
  }

  @Transactional
  //create a new movie
  public Movie createMovie(
    String description,
    String genre,
    String imageUrl,
    String itemTitle,
    Date publishDate,
    String productionCompany,
    String movieCast,
    String director,
    String currentReservationId,
    String itemNumber,
    String producer
  ) {
    Movie m = new Movie();
    m.setDescription(description);
    m.setGenre(genre);
    m.setImageUrl(imageUrl);
    m.setCurrentReservationId(currentReservationId);
    m.setIsReservable(true);
    m.setItemNumber(itemNumber);
    m.setItemTitle(itemTitle);
    m.setPublishDate(publishDate);
    m.setProductionCompany(productionCompany);
    m.setMovieCast(movieCast);
    m.setDirector(director);
    m.setProducer(producer);
    entityManager.persist(m);
    return m;
  }

  @Transactional
  //return a movie with its itemNumber
  public Movie getMovie(String itemNumber) {
    Movie movie = entityManager.find(Movie.class, itemNumber);
    return movie;
  }

  @Transactional
  //create a new printed media
  public PrintedMedia createPrintedMedia(
    String description,
    String genre,
    String imageUrl,
    String itemTitle,
    Date publishDate,
    String issueNumber,
    String currentReservationId,
    String itemNumber
  ) {
    PrintedMedia pm = new PrintedMedia();
    pm.setDescription(description);
    pm.setGenre(genre);
    pm.setImageUrl(imageUrl);
    pm.setCurrentReservationId(currentReservationId);
    pm.setIsReservable(true);
    pm.setItemNumber(itemNumber);
    pm.setItemTitle(itemTitle);
    pm.setPublishDate(publishDate);
    pm.setIssueNumber(issueNumber);
    entityManager.persist(pm);
    return pm;
  }

  @Transactional
  //return a printed media with its itemNumber
  public PrintedMedia getPrintedMedia(String itemNumber) {
    PrintedMedia item = entityManager.find(PrintedMedia.class, itemNumber);
    return item;
  }

  @Transactional
  //create a new archive
  public Archive createArchive(
    String description,
    String genre,
    String imageUrl,
    String itemTitle,
    Date publishDate,
    String itemNumber,
    String currentReservationId
  ) {
    Archive a = new Archive();
    a.setDescription(description);
    a.setGenre(genre);
    a.setImageUrl(imageUrl);
    a.setCurrentReservationId(currentReservationId);
    a.setIsReservable(false);
    a.setItemNumber(itemNumber);
    a.setItemTitle(itemTitle);
    a.setPublishDate(publishDate);
    entityManager.persist(a);
    return a;
  }

  @Transactional
  //return an archive with its itemNumber
  public Archive getArchive(String itemNumber) {
    Archive archive = entityManager.find(Archive.class, itemNumber);
    return archive;
  }

  //by Nafis
  @Transactional
  //create a new room booking
  public RoomBooking createRoomBooking(
    TimeSlot.DayOfWeek dayOfWeek,
    Time startTime,
    Time endTime,
    String bookingID,
    String roomNum,
    String idNum
  ) {
    RoomBooking roombooking = new RoomBooking();
    roombooking.setDayOfWeek(dayOfWeek);
    roombooking.setStartTime(startTime);
    roombooking.setEndTime(endTime);
    roombooking.setTimeSlotId(bookingID);
    roombooking.setRoomNum(roomNum);
    entityManager.persist(roombooking);
    return roombooking;
  }

  @Transactional
  //return a room booking with its timeslot id
  public RoomBooking getRoomBooking(String bookingID) {
    RoomBooking roombooking = entityManager.find(RoomBooking.class, bookingID);
    return roombooking;
  }

  @Transactional
  //create a new item reservation with a patron's idNum
  public ItemReservation createItemReservation(
    Date startDate,
    Date endDate,
    Time startTime,
    Time endTime,
    String itemReservationId,
    String itemNumber,
    String idNum
  ) {
    ItemReservation reservation = new ItemReservation();
    reservation.setStartDate(startDate);
    reservation.setEndDate(endDate);
    reservation.setItemReservationId(itemReservationId);
    reservation.setNumOfRenewalsLeft(2);
    reservation.setIdNum(idNum);
    reservation.setItemNumber(itemNumber);
    entityManager.persist(reservation);
    return reservation;
  }

  @Transactional
  //return an item reservation with its timeslot id
  public ItemReservation getItemReservation(String timeSlotId) {
    ItemReservation itemReservation = entityManager.find(
      ItemReservation.class,
      timeSlotId
    );
    return itemReservation;
  }

  @Transactional
  //create a new shift for a librarian
  public Shift createShift(
    TimeSlot.DayOfWeek dayOfWeek,
    Time startTime,
    Time endTime,
    String timeSlotId
  ) {
    Shift shift = new Shift();
    shift.setDayOfWeek(dayOfWeek);
    shift.setStartTime(startTime);
    shift.setEndTime(endTime);
    shift.setTimeSlotId(timeSlotId);
    entityManager.persist(shift);
    return shift;
  }

  @Transactional
  //return a library hour with its timeslot id
  public Shift getShift(String timeSlotId) {
    Shift shift = entityManager.find(Shift.class, timeSlotId);
    return shift;
  }

  @Transactional
  //create a new library hour
  public LibraryHour createLibraryHour(
    TimeSlot.DayOfWeek dayOfWeek,
    Time startTime,
    Time endTime,
    String timeSlotId
  ) {
    //add timeSlotID after Arman pushes changes to model
    LibraryHour libHour = new LibraryHour();
    libHour.setDayOfWeek(dayOfWeek);
    libHour.setStartTime(startTime);
    libHour.setEndTime(endTime);
    libHour.setTimeSlotId(timeSlotId);
    entityManager.persist(libHour);
    return libHour;
  }

  @Transactional
  //return a library hour with its timeslot id
  public LibraryHour getLibraryHour(String timeSlotId) {
    LibraryHour hour = entityManager.find(LibraryHour.class, timeSlotId);
    return hour;
  }
}
