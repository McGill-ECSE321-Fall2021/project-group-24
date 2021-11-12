package ca.mcgill.ecse321.librarysystem.service;

import static ca.mcgill.ecse321.librarysystem.service.SystemServiceHelpers.*;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibrarianService {

  @Autowired
  PatronRepository patronRepo;

  @Autowired
  LibrarianRepository librarianRepo;

  @Autowired
  HeadLibrarianRepository headLibrarianRepository;

  @Autowired
  ShiftRepository shiftRepo;

  // creates librarian, returns it so we know it's not null
  @Transactional
  public Librarian createLibrarian(
    String currentUserId,
    String firstName,
    String lastName,
    String address,
    String email,
    String username,
    String password
  ) {
    validInput(firstName, lastName, address, email, username, password);
    System.out.print(headLibrarianRepository);

    if (isHeadLibrarian(currentUserId)) {
      String idNum =
        firstName + "Librarian-" + toList(librarianRepo.findAll()).size();

      Librarian librarian = new Librarian();
      librarian.setUsername(username);
      librarian.setPassword(password);
      librarian.setFirstName(firstName);
      librarian.setLastName(lastName);
      librarian.setEmail(email);
      librarian.setIdNum(idNum);
      librarian.setAddress(address);
      librarianRepo.save(librarian);

      return librarian;
    } else {
      throw new IllegalArgumentException(
        "You are not authorized to do this. Only the Head Librarian can."
      );
    }
  }

  //fire librarian
  @Transactional
  public Librarian deleteLibrarian(String currentUserId, String idNum) {
    if (shiftRepo.findShiftByLibrarianId(idNum).size() > 0) {
      throw new IllegalArgumentException(
        "Delete failed, this librarian has shifts assigned to them."
      );
    }
    if (isHeadLibrarian(currentUserId)) {
      Librarian bye = librarianRepo.findUserByIdNum(idNum);
      librarianRepo.delete(bye);
      return bye;
    } else {
      throw new IllegalArgumentException(
        "You are not authorized to do this. Only the Head Librarian can."
      );
    }
  }

  // looks for a librarian with the given ID number, returns them if found
  @Transactional
  public Librarian getLibrarian(
    String currentUserId,
    String idNumOfLibrarianToGet
  ) {
    if (isHeadLibrarian(currentUserId)) {
      Librarian librarian = librarianRepo.findUserByIdNum(
        idNumOfLibrarianToGet
      );
      if (librarian == null) {
        throw new IllegalArgumentException("Librarian not found.");
      } else {
        return librarian;
      }
    } else {
      throw new IllegalArgumentException(
        "You are not authorized to do this. Only the Head Librarian can."
      );
    }
  }

  @Transactional
  public List<Librarian> getAllLibrarians(String userId) {
    if (isHeadLibrarian(userId)) {
      if (toList(librarianRepo.findAll()).size() == 0) {
        throw new IllegalArgumentException("No librarians found.");
      } else {
        return toList(librarianRepo.findAll());
      }
    } else {
      throw new IllegalArgumentException(
        "You are not authorized to do this. Only the Head Librarian can."
      );
    }
  }

  //helper method to check if the user is a head librarian
  private boolean isHeadLibrarian(String currentUserId) {
    System.out.println("Current User ID: " + currentUserId);
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );

    if (currentHeadLibrarian == null || !currentHeadLibrarian.getIsLoggedIn()) {
      return false;
    }
    return true;
  }

  public static <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }
}
