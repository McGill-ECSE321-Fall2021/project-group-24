package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.HeadLibrarianRepository;
import ca.mcgill.ecse321.librarysystem.dao.LibrarianRepository;
import ca.mcgill.ecse321.librarysystem.dao.LibraryHourRepository;
import ca.mcgill.ecse321.librarysystem.dao.PatronRepository;
import ca.mcgill.ecse321.librarysystem.dao.RoomBookingRepository;
import ca.mcgill.ecse321.librarysystem.dao.RoomRepository;
import ca.mcgill.ecse321.librarysystem.model.HeadLibrarian;
import ca.mcgill.ecse321.librarysystem.model.Librarian;
import ca.mcgill.ecse321.librarysystem.model.LibraryHour;
import ca.mcgill.ecse321.librarysystem.model.Patron;
import ca.mcgill.ecse321.librarysystem.model.Room;
import ca.mcgill.ecse321.librarysystem.model.RoomBooking;
import ca.mcgill.ecse321.librarysystem.model.TimeSlot;
import ca.mcgill.ecse321.librarysystem.model.TimeSlot.DayOfWeek;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomBookingService {

  @Autowired
  RoomRepository roomRepo;

  @Autowired
  LibraryHourRepository libraryHourRepo;

  @Autowired
  RoomBookingRepository roomBookingRepository;

  @Autowired
  HeadLibrarianRepository headLibrarianRepository;

  @Autowired
  LibrarianRepository librarianRepository;

  @Autowired
  PatronRepository patronRepo;

  @Transactional
  public RoomBooking createRoomBooking(
    String currentUserId,
    Date date,
    Time startTime,
    Time endTime,
    String idNum,
    String roomNumber
  ) {
    String timeSlotId =
      "RoomBooking-" + roomBookingRepository.count() + startTime + roomNumber;
    // check idNum

    if (!inputIsValid(idNum)) throw new IllegalArgumentException(
      "IdNum cannot be null or empty"
    );
    Patron p = patronRepo.findUserByIdNum(idNum);
    if (p == null) throw new IllegalArgumentException("invalid IdNum");
    // check roomNumber
    if (!inputIsValid(roomNumber)) throw new IllegalArgumentException(
      "Room number cannot be null or empty"
    );
    Room r = roomRepo.findRoomByRoomNum(roomNumber);
    if (r == null) throw new IllegalArgumentException("invalid roomNumber");

    // current user can only create room bookings for themselves
    // check if user is a patron, patron can only delete their own room bookings
    Patron currentPatron = patronRepo.findUserByIdNum(currentUserId);
    if (currentPatron != null) {
      if (!idNum.equals(currentUserId)) {
        throw new IllegalArgumentException(
          "You can only create room bookings for yourself"
        );
      }
    }
    // (head) librarians are able to create room bookings for everyone - no need check

    // check if date is in the past
    if (
      date.before(Date.valueOf(LocalDate.now()))
    ) throw new IllegalArgumentException("date must be in the future");
    // check if the reservation has any conflicts
    TimeSlot.DayOfWeek dayOfWeek = TimeSlot.DayOfWeek.valueOf(
      date.toLocalDate().getDayOfWeek().toString()
    );
    if (startTime.after(endTime) || startTime.equals(endTime)) {
      throw new IllegalArgumentException("Start time must be before end time");
    } else if (isBooked(roomNumber, date, startTime, endTime, null)) {
      throw new IllegalArgumentException(
        "Room booking must not overlap with other bookings of the same room"
      );
    } else if (outsideOfOpeningHours(dayOfWeek, startTime, endTime)) {
      throw new IllegalArgumentException(
        "Room booking must be within library opening hours"
      );
    }

    // if no conflict and the inputs are valid
    RoomBooking roomBooking = new RoomBooking();
    roomBooking.setTimeSlotId(timeSlotId);
    roomBooking.setDate(date);
    roomBooking.setStartTime(startTime);
    roomBooking.setEndTime(endTime);
    roomBooking.setIdNum(idNum);
    roomBooking.setRoomNum(roomNumber);
    roomBooking.setDayOfWeek(dayOfWeek);
    roomBookingRepository.save(roomBooking);
    return roomBooking;
  }

  private boolean inputIsValid(String string) {
    if (string == null || string.length() == 0) {
      return false;
    }
    return true;
  }

  // helper method
  // check if the room is booked during the times given
  public boolean isBooked(
    String roomNumber,
    Date date,
    Time startTime,
    Time endTime,
    String timeSlotIdToIgnore
  ) {
    List<RoomBooking> bookings = roomBookingRepository.findRoomBookingByRoomNum(
      roomNumber
    );
    if (bookings == null || bookings.size() == 0) return false;
    for (RoomBooking rb : bookings) {
      if (date.equals(rb.getDate())) {
        if (
          timeSlotIdToIgnore == null ||
          !rb.getTimeSlotId().equals(timeSlotIdToIgnore)
        ) {
          if (
            (
              startTime.after(rb.getStartTime()) &&
              startTime.before(rb.getEndTime())
            ) ||
            startTime.equals(rb.getStartTime())
          ) {
            return true;
          } else if (
            endTime.after(rb.getStartTime()) &&
            endTime.before(rb.getEndTime()) ||
            endTime.equals(rb.getEndTime())
          ) {
            return true;
          }
        }
      }
    }
    return false;
  }

  // helper method
  // check if the times are during the times given
  public boolean outsideOfOpeningHours(
    TimeSlot.DayOfWeek dayOfWeek,
    Time startTime,
    Time endTime
  ) {
    // find day of the week from date and convert to DayOfweek
    LibraryHour lh = libraryHourRepo.findHourByDayOfWeek(dayOfWeek);
    if (
      lh != null &&
      ((lh.getStartTime().before(startTime) || lh.getStartTime().equals(startTime)) && (lh.getEndTime().after(endTime) || lh.getEndTime().equals(endTime)))
    ) {
      return false;
    } else {
      return true;
    }
  }

  @Transactional
  public RoomBooking updateRoomBooking(
    String currentUserId,
    String timeSlotId,
    Date newDate,
    Time newStartTime,
    Time newEndTime,
    String newRoomNumber
  ) throws Exception {
    // check if room booking with timeSlotId exists

    RoomBooking rb = roomBookingRepository.findRoomBookingByTimeSlotId(
      timeSlotId
    );
    if (rb == null) throw new IllegalArgumentException(
      "Room booking does not exist"
    );

    // check if user is a patron, patron can only modify their own room bookings
    Patron currentPatron = patronRepo.findUserByIdNum(currentUserId);
    if (currentPatron != null) {
      if (!rb.getIdNum().equals(currentUserId)) {
        throw new IllegalArgumentException(
          "You do not have permission to modify this room booking"
        );
      }
    }

    // check if the new date and time has any conflicts
    TimeSlot.DayOfWeek dayOfWeek = TimeSlot.DayOfWeek.valueOf(
      newDate.toLocalDate().getDayOfWeek().toString()
    );
    if (newStartTime.after(newEndTime) || newStartTime.equals(newEndTime)) {
      throw new IllegalArgumentException("Start time must be before end time");
    } else if (
      isBooked(
        newRoomNumber,
        newDate,
        newStartTime,
        newEndTime,
        rb.getTimeSlotId()
      )
    ) {
      throw new IllegalArgumentException(
        "Room booking must not overlap with other bookings of the same room"
      );
    } else if (outsideOfOpeningHours(dayOfWeek, newStartTime, newEndTime)) {
      throw new IllegalArgumentException(
        "Room booking must be within library opening hours"
      );
    }

    // modify roombooking attributes
    rb.setDate(newDate);
    rb.setDayOfWeek(
      DayOfWeek.valueOf(newDate.toLocalDate().getDayOfWeek().toString())
    );
    rb.setRoomNum(newRoomNumber);
    rb.setStartTime(newStartTime);
    rb.setEndTime(newEndTime);
    roomBookingRepository.save(rb);
    System.out.println("UPDATING BOOKING");
    System.out.println(newStartTime);
    return rb;
  }

  @Transactional
  public RoomBooking deleteRoomBooking(
    String currentUserId,
    String timeSlotId
  ) {
    RoomBooking rb = roomBookingRepository.findRoomBookingByTimeSlotId(
      timeSlotId
    );

    if (rb == null) throw new IllegalArgumentException(
      "Room booking does not exist"
    );

    // check if user is a patron, patron can only delete their own room bookings
    Patron currentPatron = patronRepo.findUserByIdNum(currentUserId);
    if (currentPatron != null) {
      if (!rb.getIdNum().equals(currentUserId)) {
        throw new IllegalArgumentException(
          "You do not have permission to delete this room booking"
        );
      }
    }
    roomBookingRepository.delete(rb);
    return rb;
  }

  // looks for a room with the given ID number, returns them if found
  @Transactional
  public List<RoomBooking> getAllRoomBookings(String currentUserId) {
    Librarian currentLibrarian = librarianRepository.findUserByIdNum(
      currentUserId
    );
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );
    boolean hasPermission = false;
    if (
      currentLibrarian != null &&
      currentLibrarian.getIsLoggedIn() ||
      currentHeadLibrarian != null &&
      currentHeadLibrarian.getIsLoggedIn()
    ) {
      hasPermission = true;
    }

    if (!hasPermission) {
      throw new IllegalArgumentException(
        "Only a librarian or head librarian can get all roombooking"
      );
    }
    return toList(roomBookingRepository.findAll());
  }

  @Transactional
  public List<RoomBooking> getRoomBookingsByRoomNum(
    String currentUserId,
    String roomNum
  ) {
    Librarian currentLibrarian = librarianRepository.findUserByIdNum(
      currentUserId
    );
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );
    boolean hasPermission = false;
    if (
      currentLibrarian != null &&
      currentLibrarian.getIsLoggedIn() ||
      currentHeadLibrarian != null &&
      currentHeadLibrarian.getIsLoggedIn()
    ) {
      hasPermission = true;
    }

    if (!hasPermission) {
      throw new IllegalArgumentException(
        "Only a librarian or head librarian can get roombooking by room number"
      );
    }
    return roomBookingRepository.findRoomBookingByRoomNum(roomNum);
  }

  @Transactional
  public List<RoomBooking> getRoomBookingsByRoomNumPrivate(String roomNum) {
    return roomBookingRepository.findRoomBookingByRoomNum(roomNum);
  }

  @Transactional
  public List<RoomBooking> getRoomBookingsByIdNum(
    String currentUserId,
    String idNum
  ) {
    Patron p = patronRepo.findUserByIdNum(currentUserId);
    if (
      p != null && !currentUserId.equals(idNum)
    ) throw new IllegalArgumentException(
      "Patrons are only allowed to view their own room bookings"
    );
    return roomBookingRepository.findRoomBookingsByIdNum(idNum);
  }

  @Transactional
  public RoomBooking getRoomBookingsByTimeSlotId(
    String currentUserId,
    String timeSlotId
  ) {
    Librarian currentLibrarian = librarianRepository.findUserByIdNum(
      currentUserId
    );
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );
    boolean hasPermission = false;
    if (
      currentLibrarian != null &&
      currentLibrarian.getIsLoggedIn() ||
      currentHeadLibrarian != null &&
      currentHeadLibrarian.getIsLoggedIn()
    ) {
      hasPermission = true;
    }
    if (!hasPermission) {
      throw new IllegalArgumentException(
        "Only a librarian or head librarian can get roombooking by time slot id"
      );
    }

    return roomBookingRepository.findRoomBookingByTimeSlotId(timeSlotId);
  }

  private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }
}
