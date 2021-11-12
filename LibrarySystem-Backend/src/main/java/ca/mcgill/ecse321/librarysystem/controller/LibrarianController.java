package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.*;
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
@RequestMapping("/api/librarians")
public class LibrarianController {

  @Autowired
  private LibrarianService librarianService;

  //GET to get all librarians
  @GetMapping(value = { "/all", "/all/" })
  public ResponseEntity<?> getAllLibrarians(
    @RequestParam String currentUserId
  ) {
    try {
      return new ResponseEntity<Object>(
        librarianService
          .getAllLibrarians(currentUserId)
          .stream()
          .map(lib -> convertToDto(lib))
          .collect(Collectors.toList()),
        HttpStatus.OK
      );
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  //create a librarian
  @PostMapping(value = { "/create/", "/create" })
  public ResponseEntity<?> createLibrarian(
    @RequestParam String currentUserId,
    @RequestParam String firstName,
    @RequestParam String lastName,
    @RequestParam String address,
    @RequestParam String email,
    @RequestParam String username,
    @RequestParam String password
  ) {
    try {
      Librarian librarian = librarianService.createLibrarian(
        currentUserId,
        firstName,
        lastName,
        address,
        email,
        username,
        password
      );
      return new ResponseEntity<Object>(convertToDto(librarian), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  //delete to delete/fire a librarian
  @DeleteMapping(value = { "/delete/{idNum}", "/delete/{idNum}/" })
  public ResponseEntity<?> deleteLibrarian(
    @PathVariable("idNum") String idNum,
    @RequestParam String currentUserId
  ) {
    try {
      Librarian librarian = librarianService.deleteLibrarian(
        currentUserId,
        idNum
      );
      return new ResponseEntity<Object>(convertToDto(librarian), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  private LibrarianDto convertToDto(Librarian librarian) {
    if (librarian == null) throw new IllegalArgumentException(
      "This user does not exist"
    );

    LibrarianDto librarianDto = new LibrarianDto(
      librarian.getIdNum(),
      librarian.getFirstName(),
      librarian.getLastName(),
      librarian.getAddress(),
      librarian.getEmail(),
      librarian.getUsername(),
      librarian.getPassword()
    );

    return librarianDto;
  }
}
