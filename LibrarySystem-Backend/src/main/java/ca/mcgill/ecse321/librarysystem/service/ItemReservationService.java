package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemReservationService {

  @Autowired
  ItemReservationRepository itemReservationRepository;

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  PatronRepository patronRepository;

  @Autowired
  LibrarianRepository librarianRepository;

  @Autowired
  HeadLibrarianRepository headLibrarianRepository;

  /***
   * @author saagararya
   * @param currentUserId
   * @param startDate
   * @param idNum
   * @param itemNumber
   * @param isCheckedOut
   * @return
   */
  @Transactional
  public ItemReservation createItemReservation(
    String currentUserId,
    Date startDate,
    String idNum,
    String itemNumber,
    boolean isCheckedOut
  ) {
    if (startDate == null) {
      startDate = findNextAvailabilityForItem(itemNumber);
      //if they are at the library rn and the book is available
      if (isCheckedOut && startDate.equals(Date.valueOf(LocalDate.now()))) {
        startDate = Date.valueOf(LocalDate.now());
        //if they are at the library rn but the book should not be available
      } else if (isCheckedOut) {
        throw new IllegalArgumentException("Book has a future reservation");
      }
    }
    String itemReservationId =
      itemNumber + idNum + itemReservationRepository.count();
    Date endDate = Date.valueOf(startDate.toLocalDate().plusWeeks(2));

    Librarian currentLibrarian = librarianRepository.findUserByIdNum(
      currentUserId
    );
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );
    Patron patron = patronRepository.findUserByIdNum(idNum);
    if (patron == null) {
      throw new IllegalArgumentException("Patron does not exist");
    }
    boolean hasPermission = false;
    if (
      currentUserId.equals(idNum) &&
      patron.getIsLoggedIn() &&
      !isCheckedOut ||
      (currentLibrarian != null && currentLibrarian.getIsLoggedIn()) ||
      currentHeadLibrarian != null &&
      currentHeadLibrarian.getIsLoggedIn()
    ) {
      hasPermission = true;
    }
    if (!hasPermission) {
      throw new IllegalArgumentException(
        "You do not have permission to create an item reservation for this patron at this time"
      );
    }

    if (itemRepository.findItemByItemNumber(itemNumber) == null) {
      throw new IllegalArgumentException("The item does not exist");
    }

    List<ItemReservation> currentItemReservations = itemReservationRepository.findItemReservationsByItemNumber(
      itemNumber
    );
    for (ItemReservation currentReservation : currentItemReservations) {
      //if the new reservation starts or ends within the current one
      if (
        startDate.after(currentReservation.getStartDate()) &&
        startDate.before(currentReservation.getEndDate()) ||
        endDate.after(currentReservation.getStartDate()) &&
        endDate.before(currentReservation.getEndDate()) ||
        startDate.equals(currentReservation.getStartDate()) ||
        endDate.equals(currentReservation.getEndDate())
      ) {
        throw new IllegalArgumentException(
          "Overlaps with previous reservation"
        );
      }
    }

    if (getCurrentReservationsByIdNum(currentUserId, idNum).size() > 9) {
      throw new IllegalArgumentException(
        "Patron cannot have more than 10 reservations at a time"
      );
    }

    ItemReservation reservation = new ItemReservation();
    reservation.setItemReservationId(itemReservationId);
    reservation.setStartDate(startDate);
    reservation.setEndDate(endDate);
    reservation.setIdNum(idNum);
    reservation.setItemNumber(itemNumber);
    reservation.setNumOfRenewalsLeft(4);
    reservation.setIsCheckedOut(isCheckedOut);
    System.out.println(idNum);
    itemReservationRepository.save(reservation);
    return reservation;
  }

  /***
   * @author saagararya
   * looks for a reservation with the given itemReservationId, returns them if found
   * @param currentUserId
   * @param itemReservationId
   * @return
   */
  @Transactional
  public ItemReservation getItemReservation(
    String currentUserId,
    String itemReservationId
  ) {
    ItemReservation reservation = itemReservationRepository.findItemReservationByItemReservationId(
      itemReservationId
    );
    String idNum = reservation.getIdNum();
    Librarian currentLibrarian = librarianRepository.findUserByIdNum(
      currentUserId
    );
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );
    Patron patron = patronRepository.findUserByIdNum(currentUserId);
    boolean hasPermission = false;
    if (
      currentUserId.equals(idNum) &&
      patron.getIsLoggedIn() ||
      currentLibrarian != null &&
      currentLibrarian.getIsLoggedIn() ||
      currentHeadLibrarian != null &&
      currentHeadLibrarian.getIsLoggedIn()
    ) {
      hasPermission = true;
    }

    if (!hasPermission) {
      throw new IllegalArgumentException(
        "Only a librarian or the patron who's reservation it is can view a reservation"
      );
    }
    return reservation;
  }

  /***
   * @author saagararya
   * @param currentUserId
   * @return
   */
  @Transactional
  public List<ItemReservation> getAllItemReservations(String currentUserId) {
    if (
      librarianRepository.findUserByIdNum(currentUserId) == null &&
      headLibrarianRepository.findUserByIdNum(currentUserId) == null
    ) {
      throw new IllegalArgumentException(
        "Only a librarian can see all reservations"
      );
    }
    return toList(itemReservationRepository.findAll());
  }

  /***
   * @author saagararya
   * @param currentUserId
   * @param idNum
   * @return
   */
  @Transactional
  public List<ItemReservation> getItemReservationsByIdNum(
    String currentUserId,
    String idNum
  ) {
    Librarian currentLibrarian = librarianRepository.findUserByIdNum(
      currentUserId
    );
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );
    Patron patron = patronRepository.findUserByIdNum(currentUserId);
    boolean hasPermission = false;
    if (
      currentUserId.equals(idNum) &&
      patron.getIsLoggedIn() ||
      currentLibrarian != null &&
      currentLibrarian.getIsLoggedIn() ||
      currentHeadLibrarian != null &&
      currentHeadLibrarian.getIsLoggedIn()
    ) {
      hasPermission = true;
    }
    if (!hasPermission) {
      throw new IllegalArgumentException(
        "Only a librarian or the patron who's reservation it is can view a reservation"
      );
    }
    List<ItemReservation> reservationsByIdNum = new ArrayList<>();
    for (ItemReservation r : itemReservationRepository.findItemReservationsByIdNum(
      idNum
    )) {
      reservationsByIdNum.add(r);
    }
    return reservationsByIdNum;
  }

  /***
   * @author saagararya
   * @param currentUserId
   * @param itemNumber
   * @param idNum
   * @return
   */
  @Transactional
  public ItemReservation checkoutItem(
    String currentUserId,
    String itemNumber,
    String idNum
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
        "Only a librarian can check out a book for a patron"
      );
    }
    ItemReservation currentReservation = null; //find the reservation
    Date today = Date.valueOf(LocalDate.now());
    for (ItemReservation r : getItemReservationsByItemNumberAndIdNum(
      currentUserId,
      itemNumber,
      idNum
    )) {
      if (
        r.getStartDate().before(today) &&
        r.getEndDate().after(today) ||
        r.getStartDate().equals(today) &&
        r.getEndDate().after(today) ||
        r.getStartDate().before(today) &&
        r.getEndDate().equals(today)
      ) {
        currentReservation = r;
      }
    }
    Item item = itemRepository.findItemByItemNumber(itemNumber);
    if (item == null) {
      throw new IllegalArgumentException("Item does not exist");
    }
    if (currentReservation == null) {
      return createItemReservation(
        currentUserId,
        today,
        idNum,
        itemNumber,
        true
      );
    } else if (!currentReservation.getIdNum().equals(idNum)) {
      throw new IllegalArgumentException(
        "No reservation at this time for this patron"
      );
    } else if (!patronRepository.findPatronByIdNum(idNum).getIsVerified()) {
      throw new IllegalArgumentException(
        "Must verify patron before checking out books"
      );
    } else if (currentReservation.getIsCheckedOut()) {
      throw new IllegalArgumentException("Item is already checked out");
    }
    currentReservation.setEndDate(Date.valueOf(LocalDate.now().plusWeeks(2)));
    currentReservation.setIsCheckedOut(true);
    item.setCurrentReservationId(currentReservation.getItemReservationId());
    itemRepository.save(item);
    itemReservationRepository.save(currentReservation);
    return currentReservation;
  }

  /***
   * @author saagararya
   * @param currentUserId
   * @param itemReservationId
   * @return
   */
  @Transactional
  public boolean cancelItemReservation(
    String currentUserId,
    String itemReservationId
  ) {
    System.out.println(itemReservationId);
    ItemReservation reservation = itemReservationRepository.findItemReservationByItemReservationId(
      itemReservationId
    );
    System.out.println(reservation.getItemReservationId());
    String idNum = reservation.getIdNum();
    Librarian currentLibrarian = librarianRepository.findUserByIdNum(
      currentUserId
    );
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );
    Patron patron = patronRepository.findUserByIdNum(currentUserId);
    boolean hasPermission = false;
    if (
      currentUserId.equals(idNum) &&
      patron.getIsLoggedIn() ||
      currentLibrarian != null &&
      currentLibrarian.getIsLoggedIn() ||
      currentHeadLibrarian != null &&
      currentHeadLibrarian.getIsLoggedIn()
    ) {
      hasPermission = true;
    }
    if (!hasPermission) {
      throw new IllegalArgumentException(
        "Only a librarian or the patron who's reservation it is can delete it"
      );
    }
    if (reservation.getIsCheckedOut() == false) {
      itemReservationRepository.delete(reservation);
      return true;
    } else {
      throw new IllegalArgumentException(
        "Cannot cancel reservation for item that is already checked out"
      );
    }
  }

  /***
   * @author saagararya
   * @param currentUserId
   * @param itemNumber
   * @return
   */
  @Transactional
  public ItemReservation returnItemFromReservation(
    String currentUserId,
    String itemNumber
  ) {
    String itemReservationId = null;
    Item item = itemRepository.findItemByItemNumber(itemNumber);
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
        "Only a librarian can return an item to the library"
      );
    }
    itemReservationId = item.getCurrentReservationId();

    item.setCurrentReservationId(null);
    itemRepository.save(item);
    if (itemReservationId != null) {
      ItemReservation reservation = itemReservationRepository.findItemReservationByItemReservationId(
        itemReservationId
      );
      reservation.setIsCheckedOut(false);
      reservation.setEndDate(Date.valueOf(LocalDate.now()));

      itemReservationRepository.save(reservation);
      return reservation;
    } else {
      throw new IllegalArgumentException(
        "Item did not have a previous reservation"
      );
    }
  }

  /***
   author
  saagararya 
   @param currentUserId
   * @param itemNumber
   * @param idNum
   * @return
   */
  @Transactional
  public List<ItemReservation> getItemReservationsByItemNumberAndIdNum(
    String currentUserId,
    String itemNumber,
    String idNum
  ) {
    List<ItemReservation> reservations = new ArrayList<ItemReservation>();
    Librarian currentLibrarian = librarianRepository.findUserByIdNum(
      currentUserId
    );
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );
    Patron patron = patronRepository.findUserByIdNum(currentUserId);
    boolean hasPermission = false;
    if (
      currentUserId.equals(idNum) &&
      patron.getIsLoggedIn() ||
      currentLibrarian != null &&
      currentLibrarian.getIsLoggedIn() ||
      currentHeadLibrarian != null &&
      currentHeadLibrarian.getIsLoggedIn()
    ) {
      hasPermission = true;
    }
    if (!hasPermission) {
      throw new IllegalArgumentException(
        "Only a librarian or the patron who's reservation they are can see them"
      );
    }
    for (ItemReservation r : getCurrentReservationsByIdNum(
      currentUserId,
      idNum
    )) {
      if (r.getItemNumber().equals(itemNumber)) {
        reservations.add(r);
      }
    }

    return reservations;
  }

  /***
   * @author saagararya
   * @param currentUserId
   * @param itemNumber
   * @return
   */
  @Transactional
  public List<ItemReservation> getItemReservationsByItemNumber(
    String currentUserId,
    String itemNumber
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
        "Only a librarian or head librarian can get item reservations by itemNumber"
      );
    }
    return itemReservationRepository.findItemReservationsByItemNumber(
      itemNumber
    );
  }

  /***
   * @author saagararya
   * @param currentUserId
   * @param itemReservationId
   * @return
   */
  @Transactional
  public ItemReservation renewByItemReservationId(
    String currentUserId,
    String itemReservationId
  ) {
    ItemReservation reservation = getItemReservation(
      currentUserId,
      itemReservationId
    );
    String idNum = reservation.getIdNum();
    Librarian currentLibrarian = librarianRepository.findUserByIdNum(
      currentUserId
    );
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );
    Patron patron = patronRepository.findUserByIdNum(currentUserId);
    boolean hasPermission = false;
    if (
      currentUserId.equals(idNum) &&
      patron.getIsLoggedIn() ||
      currentLibrarian != null &&
      currentLibrarian.getIsLoggedIn() ||
      currentHeadLibrarian != null &&
      currentHeadLibrarian.getIsLoggedIn()
    ) {
      hasPermission = true;
    }
    if (!hasPermission) {
      throw new IllegalArgumentException(
        "Patrons cannot renew an item reservation that is not theirs"
      );
    }
    if (reservation.getNumOfRenewalsLeft() > 0) {
      Date nextAvailable = findNextAvailabilityForItem(
        reservation.getItemNumber()
      );
      //if this is the last reservation
      if (nextAvailable.equals(reservation.getEndDate())) {
        reservation.setNumOfRenewalsLeft(
          reservation.getNumOfRenewalsLeft() - 1
        );
        reservation.setEndDate(
          Date.valueOf(reservation.getEndDate().toLocalDate().plusWeeks(2))
        );
        itemReservationRepository.save(reservation);
        return reservation;
      } else {
        throw new IllegalArgumentException(
          "There is another reservation after yours"
        );
      }
    } else {
      throw new IllegalArgumentException("No more renewals left");
    }
  }

  /***
   * @author saagararya
   * @param currentUserId
   * @param idNum
   * @return
   */
  public List<ItemReservation> getCurrentReservationsByIdNum(
    String currentUserId,
    String idNum
  ) {
    Date today = Date.valueOf(LocalDate.now());
    List<ItemReservation> currentReservationsByPatron = new ArrayList<ItemReservation>();
    Librarian currentLibrarian = librarianRepository.findUserByIdNum(
      currentUserId
    );
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );
    Patron patron = patronRepository.findUserByIdNum(currentUserId);
    boolean hasPermission = false;
    if (
      currentUserId.equals(idNum) &&
      patron.getIsLoggedIn() ||
      currentLibrarian != null &&
      currentLibrarian.getIsLoggedIn() ||
      currentHeadLibrarian != null &&
      currentHeadLibrarian.getIsLoggedIn()
    ) {
      hasPermission = true;
    }

    if (!hasPermission) {
      throw new IllegalArgumentException(
        "Only a librarian or the patron who's reservation they are can see them"
      );
    }
    for (ItemReservation reservation : getItemReservationsByIdNum(
      currentUserId,
      idNum
    )) {
      if (
        reservation.getStartDate().after(today) ||
        reservation.getStartDate().equals(today) ||
        reservation.getEndDate().after(today) ||
        (
          reservation.getIsCheckedOut() == true &&
          reservation.getEndDate().equals(today)
        )
      ) {
        currentReservationsByPatron.add(reservation);
      }
    }
    return currentReservationsByPatron;
  }

  /***
   * find next availability
   * @author saagararya
   * @param itemNumber
   * @return
   */
  @Transactional
  public Date findNextAvailabilityForItem(String itemNumber) {
    List<ItemReservation> reservationsByItemNumber = itemReservationRepository.findItemReservationsByItemNumber(
      itemNumber
    );
    ItemReservation latestReservation = null;
    for (ItemReservation r : reservationsByItemNumber) {
      if (latestReservation == null) {
        latestReservation = r;
      } else if (
        latestReservation.getEndDate().before(r.getStartDate()) ||
        latestReservation.getEndDate().equals(r.getStartDate())
      ) {
        latestReservation = r;
      }
    }
    if (latestReservation == null) {
      return Date.valueOf(LocalDate.now());
    } else {
      return Date.valueOf(latestReservation.getEndDate().toLocalDate());
    }
  }

  private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }
}
