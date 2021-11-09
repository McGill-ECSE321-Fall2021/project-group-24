package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class LibrarianController {

  @Autowired
  private LibrarianService librarianService;

  //GET to get all librarians
  @GetMapping(value = { "/librarians", "/librarians/" })
  public List<LibrarianDto> getAllLibrarians() {
    return librarianService
      .getAllLibrarians()
      .stream()
      .map(lib -> convertToDto(lib))
      .collect(Collectors.toList());
  }

  //POST to add a librarian
  // @PostMapping(value = { "/librarians/{idNum}", "/librarians/{idNum}/" })
  // public LibrarianDto createLibrarian(@PathVariable("idNum") String idNum) {
  //   Librarian librarian = librarianService.createLibrarian(idNum);
  //   return convertToDto(librarian);
  // }

  //POST to delete/fire a librarian
  @PostMapping(
    value = { "/librarians/delete/{idNum}", "/librarians/delete/{idNum}/" }
  )
  public LibrarianDto deleteLibrarian(@PathVariable("idNum") String idNum) {
    Librarian librarian = librarianService.deleteLibrarian(idNum);
    return convertToDto(librarian);
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
