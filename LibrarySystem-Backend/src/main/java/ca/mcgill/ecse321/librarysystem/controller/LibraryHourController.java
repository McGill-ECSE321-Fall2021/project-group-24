package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/libraryhour")
public class LibraryHourController {

  @Autowired
  private LibraryHourService libraryHourService;

  /** Method adds a library hour
   * @author Arman
   * @param currentUserId, dayOfWeek, startTime, endTime
   * @return Response Entity
   */
  @PostMapping(value = { "/add_library_hour", "/add_library_hour/" })
  public ResponseEntity<?> addLibraryHour(
    @RequestParam String currentUserId,
    @RequestParam String dayOfWeek,
    @RequestParam String startTime,
    @RequestParam String endTime
  ) {
    LibraryHour libraryHour = null;
    try {
      libraryHour =
        libraryHourService.createLibraryHour(
          currentUserId,
          TimeSlot.DayOfWeek.valueOf(dayOfWeek),
          Time.valueOf(startTime + ":00"),
          Time.valueOf(endTime + ":00")
        );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(
        e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
    return new ResponseEntity<>(convertToDto(libraryHour), HttpStatus.CREATED);
  }

  /** Method modifies an existing library hour
   * @author Arman
   * @param currentUserId, dayOfWeek, startTime, endTime
   * @return Response Entity
   */
  @PutMapping(value = { "/modify_library_hour", "/modify_library_hour/" })
  public ResponseEntity<?> modifyLibraryHour(
    @RequestParam String currentUserId,
    @RequestParam TimeSlot.DayOfWeek dayOfWeek,
    @RequestParam String startTime,
    @RequestParam String endTime
  ) {
    LibraryHour libraryHour = null;
    try {
      libraryHour =
        libraryHourService.modifyLibraryHour(
          currentUserId,
          dayOfWeek,
          Time.valueOf(startTime + ":00"),
          Time.valueOf(endTime + ":00")
        );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(
        e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
    return new ResponseEntity<>(convertToDto(libraryHour), HttpStatus.CREATED);
  }

  /** Method removes an existing library hour
   * @author Arman
   * @param currentUserId, dayOfWeek
   * @return true if the hour is successfully deleted
   */
  @DeleteMapping(value = { "/remove_library_hour", "/remove_library_hour/" })
  public ResponseEntity<?> removeLibraryHour(
    @RequestParam String currentUserId,
    @RequestParam TimeSlot.DayOfWeek dayOfWeek
  ) {
    boolean isDeleted = false;
    try {
      isDeleted =
        libraryHourService.removeLibraryHour(currentUserId, dayOfWeek);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(isDeleted, HttpStatus.CREATED);
  }

  /** Method returns the library hour of a certain day
   * @author Arman
   * @param dayOfWeek
   * @return LibraryHourDto
   */
  @GetMapping(
    value = { "/view_library_hour_by_day", "/view_library_hour_by_day/" }
  )
  public ResponseEntity<?> viewLibraryHourByDay(
    @RequestParam TimeSlot.DayOfWeek dayOfWeek
  ) {
    LibraryHourDto libraryHourDto = null;
    try {
      libraryHourDto =
        convertToDto(libraryHourService.getLibraryHour(dayOfWeek));
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(libraryHourDto, HttpStatus.OK);
  }

  /** Method returns all the library hours
   * @author Arman
   * @param dayOfWeek
   * @return List of type LibraryHourDto
   */
  @GetMapping(value = { "/view_library_hours", "/view_library_hours" })
  public ResponseEntity<?> viewLibraryHours() {
    List<LibraryHourDto> libraryHours = new ArrayList<LibraryHourDto>();

    try {
      libraryHours =
        libraryHourService
          .getAllLibraryHours()
          .stream()
          .map(lh -> convertToDto(lh))
          .collect(Collectors.toList());
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(libraryHours, HttpStatus.OK);
  }

  /** Method converts a library hour object into a library hour DTO
   * @author Arman
   * @param libraryHour
   * @return libraryHourDto
   */
  private LibraryHourDto convertToDto(LibraryHour libraryHour) {
    if (libraryHour == null) throw new IllegalArgumentException(
      "This library hour does not exist"
    );
    LibraryHourDto libraryHourDto = new LibraryHourDto(
      libraryHour.getDayOfWeek(),
      libraryHour.getStartTime(),
      libraryHour.getEndTime()
    );
    return libraryHourDto;
  }
}
