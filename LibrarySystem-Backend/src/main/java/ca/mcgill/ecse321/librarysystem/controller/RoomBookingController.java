package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/roombookings")
public class RoomBookingController {

  @Autowired
  private RoomBookingService roomBookingService;

  /** Method get all roombookings
   * @author Selena
   * @return all roombookings
   */
  @GetMapping(value = { "/view_roombookings", "/view_roombookings/" })
  public ResponseEntity<?> getRoomBooking(@RequestParam String currentUserId) {
    try {
      return new ResponseEntity<Object>(
        roomBookingService
          .getAllRoomBookings(currentUserId)
          .stream()
          .map(lib -> convertToDto(lib))
          .collect(Collectors.toList()),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Method get all roombookings of a patron provided idNum
   * @author Selena
   * @param idNum
   * @return all roombookings of a patron
   */
  @GetMapping(
    value = {
      "/view_roombookings/patron/{idNum}", "/view_roombookings/patron/{idNum}/",
    }
  )
  public ResponseEntity<?> getRoomBookingOfPatron(
    @PathVariable("idNum") String idNum,
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        getRoomBookingDtosForPatron(currentUserId, idNum),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Method get all roombookings of a room
   * @author Selena
   * @param roomNum
   * @return all roombookings of a room
   */
  @GetMapping(
    value = {
      "/view_roombookings/room/{roomNum}", "/view_roombookings/room/{roomNum}/",
    }
  )
  public ResponseEntity<?> getRoomBookingOfRoom(
    @PathVariable("roomNum") String roomNum,
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        getRoomBookingDtosForRoom(currentUserId, roomNum),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Method get all roombookings of a room
   * @author Saagar
   * @param roomNum
   * @return all roombookings of a room, without sensitive info
   */
  @GetMapping(
    value = {
      "/privateview_roombookings/room/{roomNum}",
      "/privateview_roombookings/room/{roomNum}/",
    }
  )
  public ResponseEntity<?> getRoomBookingOfRoomPrivate(
    @PathVariable("roomNum") String roomNum
  ) {
    try {
      return new ResponseEntity<Object>(
        getRoomBookingDtosForRoomPrivate(roomNum),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Method get the roombooking with timeSlotId
   * @author Selena
   * @param timeSlotId
   * @return roombooking with timeSlotId
   */
  @GetMapping(
    value = {
      "/view_roombooking/{timeSlotId}", "/view_roombooking/{timeSlotId}/",
    }
  )
  public ResponseEntity<?> getRoomBookingOfTimeSlot(
    @PathVariable("timeSlotId") String timeSlotId,
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        convertToDto(
          roomBookingService.getRoomBookingsByTimeSlotId(
            currentUserId,
            timeSlotId
          )
        ),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Method create roombooking, timeSlotId is automatically generated
   * @author Selena
   * @param currentUserId, date, startTime, endTime, idNum, roomNum
   * @return roombooking if successfully created
   *
   * librarian are allowed to create roombookings for a patron, patron can only create roombookings for themselves
   */
  @PostMapping(value = { "/add_roombooking", "/add_roombooking/" })
  public ResponseEntity<?> createRoomBooking(
    @RequestParam String currentUserId,
    @RequestParam String date,
    @RequestParam String startTime,
    @RequestParam String endTime,
    @RequestParam String idNum,
    @RequestParam String roomNum
  ) {
    try {
      return new ResponseEntity<Object>(
        convertToDto(
          roomBookingService.createRoomBooking(
            currentUserId,
            Date.valueOf(LocalDate.parse(date)),
            Time.valueOf(LocalTime.parse(startTime)),
            Time.valueOf(LocalTime.parse(endTime)),
            idNum,
            roomNum
          )
        ),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Method update roombooking using timeSlotId
   * @author Selena
   * @param currentUserId, date, startTime, endTime, idNum, roomNum
   * @return roombooking if successfully updated
   *
   * librarian are allowed to update any roombooking, patron can only update their own roombookings
   */
  @PutMapping(value = { "/update_roombooking", "/update_roombooking/" })
  public ResponseEntity<?> updateRoomBooking(
    @RequestParam String currentUserId,
    @RequestParam String timeSlotId,
    @RequestParam String newDate,
    @RequestParam String newStartTime,
    @RequestParam String newEndTime,
    @RequestParam String newRoomNum
  ) {
    System.out.println("Flag Put");
    RoomBooking booking = null;
    try {
      booking =
        roomBookingService.updateRoomBooking(
          currentUserId,
          timeSlotId,
          Date.valueOf(LocalDate.parse(newDate)),
          Time.valueOf(LocalTime.parse(newStartTime)),
          Time.valueOf(LocalTime.parse(newEndTime)),
          newRoomNum
        );
      return new ResponseEntity<Object>(convertToDto(booking), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  /** Method delete roombooking using timeSlotId
   * @author Selena
   * @param currentUserId, timeSlotId
   * @return roombooking if successfully deleted
   *
   * librarian are allowed to delete any roombooking, patron can only delete their own roombookings
   */
  @DeleteMapping(value = { "/delete_roombooking", "/delete_roombooking/" })
  public ResponseEntity<?> deleteRoomBooking(
    @RequestParam String currentUserId,
    @RequestParam String timeSlotId
  ) {
    RoomBooking booking = null;
    try {
      booking = roomBookingService.deleteRoomBooking(currentUserId, timeSlotId);
      return new ResponseEntity<Object>(convertToDto(booking), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  private List<RoomBookingDto> getRoomBookingDtosForPatron(
    String currentUserId,
    String idNum
  ) {
    List<RoomBooking> roomBookingForPatron = roomBookingService.getRoomBookingsByIdNum(
      currentUserId,
      idNum
    );
    List<RoomBookingDto> bookings = new ArrayList<>();
    for (RoomBooking booking : roomBookingForPatron) {
      bookings.add(convertToDto(booking));
    }
    return bookings;
  }

  private List<RoomBookingDto> getRoomBookingDtosForRoom(
    String currentUserId,
    String roomNum
  ) {
    List<RoomBooking> roomBookingsForRoomNum = roomBookingService.getRoomBookingsByRoomNum(
      currentUserId,
      roomNum
    );
    List<RoomBookingDto> roomBooking = new ArrayList<>();
    for (RoomBooking booking : roomBookingsForRoomNum) {
      roomBooking.add(convertToDto(booking));
    }
    return roomBooking;
  }

  private List<RoomBookingDto> getRoomBookingDtosForRoomPrivate(
    String roomNum
  ) {
    List<RoomBooking> roomBookingsForRoomNum = roomBookingService.getRoomBookingsByRoomNumPrivate(
      roomNum
    );
    List<RoomBookingDto> roomBooking = new ArrayList<>();
    for (RoomBooking booking : roomBookingsForRoomNum) {
      roomBooking.add(
        new RoomBookingDto(
          "private",
          booking.getDate(),
          booking.getStartTime(),
          booking.getEndTime(),
          "private",
          booking.getRoomNum()
        )
      );
    }
    return roomBooking;
  }

  private RoomBookingDto convertToDto(RoomBooking roomBooking) {
    System.out.println("roombooking:" + roomBooking);
    RoomBookingDto roomBookingDto = new RoomBookingDto(
      roomBooking.getTimeSlotId(),
      roomBooking.getDate(),
      roomBooking.getStartTime(),
      roomBooking.getEndTime(),
      roomBooking.getIdNum(),
      roomBooking.getRoomNum()
    );
    return roomBookingDto;
  }
}
