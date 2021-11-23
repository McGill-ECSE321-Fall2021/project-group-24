package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShiftService {

  @Autowired
  ShiftRepository shiftRepo;

  @Autowired
  LibrarianRepository librarianRepo;

  @Autowired
  HeadLibrarianRepository headLibrarianRepo;

  /**Method creates a new shift (must be performed by head librarian)
   * Allows multiple shifts per day (as long as they do not overlap)
   * @author Arman
   * @param currentUserId, librarianId, dayOfWeek, startTime, endTime
   * @return shift
   * Note that there is a start and an end date to account for overnight shifts
   */
  @Transactional
  public Shift createShift(
    String currentUserId,
    String librarianId,
    TimeSlot.DayOfWeek dayOfWeek,
    Time startTime,
    Time endTime
  ) {
    User user = headLibrarianRepo.findUserByIdNum(currentUserId);
    Librarian librarian = librarianRepo.findUserByIdNum(librarianId);
    //checks if the shift is for a regular librarian or the head librarian
    if (librarian == null) {
      librarian = headLibrarianRepo.findUserByIdNum(librarianId);
    }
    if (
      !(user instanceof HeadLibrarian) || !(user.getIsLoggedIn())
    ) throw new IllegalArgumentException(
      "Only the Head Librarian can create librarian shifts"
    );
    if (librarian == null) throw new IllegalArgumentException(
      "No librarian with this ID exists"
    );
    if (startTime.after(endTime)) throw new IllegalArgumentException(
      "Shift end time cannot be before its start time"
    );
    Shift oldShift = shiftRepo.findShiftByLibrarianIdAndDayOfWeek(
      librarianId,
      dayOfWeek
    );
    // checks that shifts do not overlap
    if (oldShift != null) {
      if (
        (
          oldShift.getEndTime().after(startTime) &&
          oldShift.getEndTime().before(endTime)
        ) ||
        (
          oldShift.getStartTime().before(endTime) &&
          oldShift.getStartTime().after(startTime)
        )
      ) {
        throw new IllegalArgumentException(
          "Librarian cannot have overlapping shifts"
        );
      }
    }
    Shift shift = new Shift();
    String timeSlotId =
      dayOfWeek.toString() +
      librarianId +
      startTime.toString() +
      endTime.toString() +
      shiftRepo.count();
    shift.setTimeSlotId(timeSlotId);
    shift.setStartTime(startTime);
    shift.setLibrarianId(librarianId);
    shift.setDayOfWeek(dayOfWeek);
    shift.setEndTime(endTime);
    shiftRepo.save(shift);
    return shift;
  }

  /**Method modifies a shift (must be performed by head librarian)
   * @author Arman
   * @param currentUserId, timeSlotId, librarianId, dayOfWeek, startTime, endTime
   * @return shift
   */

  @Transactional
  public Shift modifyShift(
    String currentUserId,
    String timeSlotId,
    String librarianId,
    TimeSlot.DayOfWeek newDayOfWeek,
    Time startTime,
    Time endTime
  ) {
    User user = headLibrarianRepo.findUserByIdNum(currentUserId);
    Librarian librarian = librarianRepo.findUserByIdNum(librarianId);
    //checks if the shift is for a regular librarian or the head librarian
    if (librarian == null) {
      librarian = headLibrarianRepo.findUserByIdNum(librarianId);
    }
    if (
      !(user instanceof HeadLibrarian) || !(user.getIsLoggedIn())
    ) throw new IllegalArgumentException(
      "Only the Head Librarian can modify librarian shifts"
    );
    if (librarian == null) throw new IllegalArgumentException(
      "No librarian with this ID exists"
    );
    if (
      librarianId == null ||
      timeSlotId == null ||
      librarianId == null ||
      newDayOfWeek == null ||
      startTime == null ||
      endTime == null
    ) {
      throw new IllegalArgumentException("Fields cannot be blank");
    }
    if (startTime.after(endTime)) throw new IllegalArgumentException(
      "Shift end time cannot be before its start time"
    );
    Shift oldShift = shiftRepo.findShiftByLibrarianIdAndDayOfWeek(
      librarianId,
      newDayOfWeek
    );
    // checks that shifts do not overlap
    if (oldShift != null) {
      if (
        (
          oldShift.getEndTime().after(startTime) &&
          oldShift.getEndTime().before(endTime)
        ) ||
        (
          oldShift.getStartTime().before(endTime) &&
          oldShift.getStartTime().after(startTime)
        )
      ) {
        throw new IllegalArgumentException(
          "Librarian cannot have overlapping shifts"
        );
      }
    }
    if (
      shiftRepo.findShiftByTimeSlotId(timeSlotId) == null
    ) throw new IllegalArgumentException(
      "Old shift does not exist so cannot modify"
    );
    if (
      librarianRepo.findUserByIdNum(librarianId) == null
    ) throw new IllegalArgumentException("No Librarian with this ID exists");

    Shift shift = shiftRepo.findShiftByTimeSlotId(timeSlotId);
    shift.setDayOfWeek(newDayOfWeek);
    shift.setStartTime(startTime);
    shift.setEndTime(endTime);
    shiftRepo.save(shift);
    return shift;
  }

  /**Method removes a certain shift (must be performed by head librarian)
   * @author Arman
   * @param currentUserId, timeSlotId
   * @return true if the shift is deleted
   */
  @Transactional
  public boolean removeShift(String currentUserId, String timeSlotId) {
    User user = headLibrarianRepo.findUserByIdNum(currentUserId);
    if (
      !(user instanceof HeadLibrarian) || !(user.getIsLoggedIn())
    ) throw new IllegalArgumentException(
      "Only the Head Librarian can remove librarian shifts"
    );
    if (currentUserId == null || timeSlotId == null) {
      throw new IllegalArgumentException("Fields cannot be blank");
    }
    Shift shift = shiftRepo.findShiftByTimeSlotId(timeSlotId);
    if (shift == null) throw new IllegalArgumentException(
      "Shift cannot be found"
    );
    shiftRepo.delete(shiftRepo.findShiftByTimeSlotId(timeSlotId));
    return true;
  }

  /**Method removes all of a Librarian's shifts (must be performed by head librarian)
   * @author Arman
   * @param currentUserId, librarianId
   * @return true if all their shifts are deleted
   */
  @Transactional
  public boolean removeLibrarianShifts(
    String currentUserId,
    String librarianId
  ) {
    User user = headLibrarianRepo.findUserByIdNum(currentUserId);
    Librarian librarian = librarianRepo.findUserByIdNum(librarianId);
    if (
      !(user instanceof HeadLibrarian) || !(user.getIsLoggedIn())
    ) throw new IllegalArgumentException(
      "Only the Head Librarian can remove all of a librarian's shifts"
    );
    if (librarianId == null) throw new IllegalArgumentException(
      "librarian ID cannot be blank"
    );
    //checks if the shift is for a regular librarian or the head librarian
    if (librarian == null) {
      librarian = headLibrarianRepo.findUserByIdNum(librarianId);
    }
    if (librarian == null) throw new IllegalArgumentException(
      "No librarian with this ID exists"
    );

    // Searches through all the shifts, deleting the ones associated with a certain librarian
    List<Shift> allShifts = toList(shiftRepo.findAll());
    for (Shift shift : allShifts) {
      if (shift.getLibrarianId().equals(librarianId)) {
        shiftRepo.delete(shift);
      }
    }
    return true;
  }

  /**Method returns a certain shift (can be performed by librarian or head librarian)
   *@author Arman
   *@param currentUserId, timeSlotId
   *@return shift
   */
  @Transactional
  public Shift getShift(String currentUserId, String timeSlotId) {
    User user = headLibrarianRepo.findUserByIdNum(currentUserId);
    User user1 = librarianRepo.findUserByIdNum(currentUserId);
    if (
      (!(user instanceof HeadLibrarian) || !(user.getIsLoggedIn())) &&
      (!(user1 instanceof Librarian) || !(user1.getIsLoggedIn()))
    ) {
      throw new IllegalArgumentException("Only librarians can view shifts");
    }
    Shift shift = null;
    if (currentUserId == null || timeSlotId == null) {
      throw new IllegalArgumentException("Fields cannot be blank");
    }
    shift = shiftRepo.findShiftByTimeSlotId(timeSlotId);
    if (shift == null) throw new IllegalArgumentException(
      "Shift cannot be found"
    );
    return shift;
  }

  /**Method returns all of a librarian's shifts (can be performed by a librarian or head librarian)
   *@author Arman
   *@param currentUserId, librarianId
   *@return list of all the librarian's shifts
   */
  @Transactional
  public List<Shift> getAllShiftsForLibrarian(
    String currentUserId,
    String librarianId
  ) {
    User user = headLibrarianRepo.findUserByIdNum(currentUserId);
    User user1 = librarianRepo.findUserByIdNum(currentUserId);
    Librarian librarian = librarianRepo.findUserByIdNum(librarianId);
    if (
      (!(user instanceof HeadLibrarian) || !(user.getIsLoggedIn())) &&
      (!(user1 instanceof Librarian) || !(user1.getIsLoggedIn()))
    ) {
      throw new IllegalArgumentException("Only librarians can view shifts");
    }
    if (librarianId == null) throw new IllegalArgumentException(
      "Librarian ID cannot be blank"
    );
    if (librarian == null) {
      librarian = headLibrarianRepo.findUserByIdNum(librarianId);
    }
    if (librarian == null) throw new IllegalArgumentException(
      "No librarian with this ID exists"
    );
    // This searches through all shifts, add the ones with desired librarianId to a list then return the list.
    List<Shift> allShifts = toList(shiftRepo.findAll());
    ArrayList<Shift> desiredShifts = new ArrayList<Shift>();
    for (Shift shift : allShifts) {
      if (shift.getLibrarianId().equals(librarianId)) {
        desiredShifts.add(shift);
      }
    }
    return desiredShifts;
  }

  /**Method returns every librarian's shifts (can be performed by a librarian or head librarian)
   *@author Arman
   *@param currentUserId
   *@return list of every librarian's shifts
   */
  @Transactional
  public List<Shift> getAllShifts(String currentUserId) {
    User user = headLibrarianRepo.findUserByIdNum(currentUserId);
    User user1 = librarianRepo.findUserByIdNum(currentUserId);
    if (
      (!(user instanceof HeadLibrarian) || !(user.getIsLoggedIn())) &&
      (!(user1 instanceof Librarian) || !(user1.getIsLoggedIn()))
    ) {
      throw new IllegalArgumentException("Only librarians can view shifts");
    }
    return toList(shiftRepo.findAll());
  }

  private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }
}
