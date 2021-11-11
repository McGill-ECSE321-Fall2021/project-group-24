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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/librarian")
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

  //create a librarian
  @PostMapping(value = { "/librarians/create/", "/librarians/create" })
  public LibrarianDto createLibrarian(
    @RequestParam String firstName,
    @RequestParam String lastName,
    @RequestParam String address,
    @RequestParam String email,
    @RequestParam String username,
    @RequestParam String password
  ) {
    Librarian librarian = librarianService.createLibrarian(
      firstName,
      lastName,
      address,
      email,
      username,
      password
    );
    System.out.println("Successfully created " + librarian.getIdNum() + "!");
    return convertToDto(librarian);
  }

  //update librarian
  @PostMapping(
    value = {
      "/librarians/update/{idNumOfAccountToUpdate}/",
      "/librarians/update/{idNumOfAccountToUpdate}",
    }
  )
  public LibrarianDto updateLibrarian(
    @PathVariable("idNumOfAccountToUpdate") String idNumOfAccountToUpdate,
    @RequestParam String firstName,
    @RequestParam String lastName,
    @RequestParam String address,
    @RequestParam String email,
    @RequestParam String username,
    @RequestParam String password
  ) {
    Librarian updatedLibrarian = librarianService.updateLibrarian(
      idNumOfAccountToUpdate,
      firstName,
      lastName,
      address,
      email,
      username,
      password
    );
    System.out.println(
      "Successfully updated the librarian information for: " +
      updatedLibrarian.getIdNum() +
      "!"
    );
    return convertToDto(updatedLibrarian);
  }

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
