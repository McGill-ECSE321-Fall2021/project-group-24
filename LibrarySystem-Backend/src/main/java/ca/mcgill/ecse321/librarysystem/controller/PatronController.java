package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dao.PatronRepository;
import ca.mcgill.ecse321.librarysystem.dto.PatronDto;
import ca.mcgill.ecse321.librarysystem.model.Patron;
import ca.mcgill.ecse321.librarysystem.service.PatronService;
import ca.mcgill.ecse321.librarysystem.service.UserService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/patron")
public class PatronController {

  @Autowired
  private PatronService patronService;

  @Autowired
  PatronRepository patronRepository;

  @Autowired
  UserService userService;

  @GetMapping(value = { "/all", "/all/" })
  public List<PatronDto> getAllPatrons() {
    return patronService
      .getAllPatrons()
      .stream()
      .map(patron -> convertToDto(patron))
      .collect(Collectors.toList());
  }

  @GetMapping(value = { "/get_by_id/{idNum}", "/get_by_id/{idNum}/" })
  public ResponseEntity<?> getPatronById(@PathVariable String idNum) {
    try {
      Patron p = patronService.getPatronAccountByID(idNum);
      if (p == null) return new ResponseEntity<Object>(
        "Patron does not exist.",
        HttpStatus.NOT_FOUND
      );
      PatronDto output = convertToDto(p);
      return new ResponseEntity<Object>(output, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(
    value = { "/get_by_username/{username}", "/get_by_username/{username}/" }
  )
  public ResponseEntity<?> getPatronByUsername(
    @PathVariable("username") String username
  ) {
    try {
      Patron p = patronService.getPatronAccountByUsername(username);

      if (p == null) {
        return new ResponseEntity<>(
          "Patron does not exist.",
          HttpStatus.NOT_FOUND
        );
      }

      PatronDto output = convertToDto(p);
      return new ResponseEntity<>(output, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping(value = { "/create_patron_irl", "/create_patron_irl/" })
  public ResponseEntity<?> createPatronIRL(
    @RequestParam("first") String first,
    @RequestParam("last") String last,
    @RequestParam("isResident") boolean isResident,
    @RequestParam("address") String address
  ) throws IllegalArgumentException {
    Patron patron = null;
    try {
      patron = patronService.createPatronIRL(first, last, isResident, address);
      return new ResponseEntity<>(patron, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(
        e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }

  @PostMapping(value = { "/create_patron_online", "/create_patron_online/" })
  public ResponseEntity<?> createPatron(
    @RequestParam("username") String username,
    @RequestParam("password") String password,
    @RequestParam("first") String firstName,
    @RequestParam("last") String lastName,
    @RequestParam("isResident") boolean isResident,
    @RequestParam("address") String address,
    @RequestParam("email") String email
  ) throws IllegalArgumentException {
    try {
      Patron patron = patronService.createPatronOnline(
        username,
        password,
        firstName,
        lastName,
        isResident,
        address,
        email
      );
      return new ResponseEntity<>(convertToDto(patron), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping(
    value = { "/delete_patron/{idNum}", "/delete_patron/{idNum}/" }
  )
  public ResponseEntity<?> deletePatron(
    @PathVariable String idNum,
    @RequestParam String currentUserId
  ) throws IllegalArgumentException {
    try {
      return new ResponseEntity<>(
        patronService.deletePatron(idNum, currentUserId),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  private PatronDto convertToDto(Patron patron) {
    PatronDto dto = new PatronDto(
      patron.getIdNum(),
      patron.getFirstName(),
      patron.getLastName(),
      patron.getAddress(),
      patron.getEmail(),
      patron.getUsername(),
      patron.getPassword(),
      patron.getIsVerified(),
      patron.getIsResident(),
      patron.getIsRegisteredOnline()
    );
    return dto;
  }
}
