package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/itemReservations")
public class ItemReservationController {

  @Autowired
  private ItemReservationService itemReservationService;

  @GetMapping(value = { "/all", "/all/" })
  public ResponseEntity<?> getAllItemReservations(
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        itemReservationService
          .getAllItemReservations(currentUserId)
          .stream()
          .map(lib -> convertToDto(lib))
          .collect(Collectors.toList()),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  //get all item reservations by a patron
  @GetMapping(value = { "/patron/{idNum}", "/patron/{idNum}/" })
  public ResponseEntity<?> getItemReservationsOfPatron(
    @PathVariable("idNum") String idNum,
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        getItemReservationDtosForPatron(currentUserId, idNum),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  //get all item reservations for an item
  @GetMapping(value = { "/item/{itemNumber}", "/item/{itemNumber}/" })
  public ResponseEntity<?> getItemReservationsOfItem(
    @PathVariable("itemNumber") String itemNumber,
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        getItemReservationDtosForItem(currentUserId, itemNumber),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(value = { "/{itemReservationId}", "/{itemReservationId}/" })
  public ResponseEntity<?> getItemReservation(
    @PathVariable("itemReservationId") String itemReservationId,
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        convertToDto(
          itemReservationService.getItemReservation(
            currentUserId,
            itemReservationId
          )
        ),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  //return an item
  @PutMapping(
    value = { "/return_item/{itemNumber}", "/return_item/{itemNumber}/" }
  )
  public ResponseEntity<?> returnItem(
    @PathVariable("itemNumber") String itemNumber,
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        convertToDto(
          itemReservationService.returnItemFromReservation(
            currentUserId,
            itemNumber
          )
        ),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  //checkout item
  @PutMapping(
    value = {
      "/checkout_item/{itemNumber}/byPatron/{idNum}",
      "/checkout_item/{itemNumber}/byPatron/{idNum}",
    }
  )
  public ResponseEntity<?> checkoutItem(
    @PathVariable("itemNumber") String itemNumber,
    @PathVariable("idNum") String idNum,
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        convertToDto(
          itemReservationService.checkoutItem(currentUserId, itemNumber, idNum)
        ),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  //createReservation
  @PostMapping(value = { "/create_reservation", "/create_reservation/" })
  public ResponseEntity<?> createItemReservation(
    @RequestParam String currentUserId,
    @RequestParam String idNum,
    @RequestParam String itemNumber,
    @RequestParam boolean isCheckedOut
  ) {
    try {
      ItemReservation reservation = itemReservationService.createItemReservation(
        currentUserId,
        null,
        idNum,
        itemNumber,
        isCheckedOut
      );
      return new ResponseEntity<Object>(
        convertToDto(reservation),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(
    value = {
      "/create_reservation/customDate", "/create_reservation/customDate/",
    }
  )
  public ResponseEntity<?> createItemReservationCustomDate(
    @RequestParam String currentUserId,
    @RequestParam Integer numOfRenewalsLeft,
    @RequestParam String idNum,
    @RequestParam String itemNumber,
    @RequestParam boolean isCheckedOut,
    @RequestParam String startDate
  ) {
    try {
      ItemReservation reservation = itemReservationService.createItemReservation(
        currentUserId,
        Date.valueOf(LocalDate.parse(startDate)),
        idNum,
        itemNumber,
        isCheckedOut
      );
      return new ResponseEntity<Object>(
        convertToDto(reservation),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping(
    value = { "/renew/{itemReservationId}", "/renew/{itemReservationId}/" }
  )
  public ResponseEntity<?> renewItemReservation(
    @PathVariable("itemReservationId") String itemReservationId,
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        convertToDto(
          itemReservationService.renewByItemReservationId(
            currentUserId,
            itemReservationId
          )
        ),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping(
    value = { "/cancel/{itemReservationId}", "/cancel/{itemReservationId}/" }
  )
  public ResponseEntity<?> cancelItemReservation(
    @PathVariable("itemReservationId") String itemReservationId,
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        itemReservationService.cancelItemReservation(
          currentUserId,
          itemReservationId
        ),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  private List<ItemReservationDto> getItemReservationDtosForPatron(
    String currentUserId,
    String idNum
  ) {
    List<ItemReservation> itemReservationsForPatron = itemReservationService.getItemReservationsByIdNum(
      currentUserId,
      idNum
    );
    List<ItemReservationDto> reservations = new ArrayList<>();
    for (ItemReservation reservation : itemReservationsForPatron) {
      reservations.add(convertToDto(reservation));
    }
    return reservations;
  }

  private List<ItemReservationDto> getItemReservationDtosForItem(
    String currentUserId,
    String itemNumber
  ) {
    List<ItemReservation> itemReservationsForItem = itemReservationService.getItemReservationsByItemNumber(
      currentUserId,
      itemNumber
    );
    List<ItemReservationDto> reservations = new ArrayList<>();
    for (ItemReservation reservation : itemReservationsForItem) {
      reservations.add(convertToDto(reservation));
    }
    return reservations;
  }

  private ItemReservationDto convertToDto(ItemReservation reservation) {
    ItemReservationDto reservationDto = new ItemReservationDto(
      reservation.getItemReservationId(),
      reservation.getStartDate(),
      reservation.getEndDate(),
      reservation.getNumOfRenewalsLeft(),
      reservation.getIdNum(),
      reservation.getItemNumber(),
      reservation.getIsCheckedOut()
    );

    return reservationDto;
  }
}
