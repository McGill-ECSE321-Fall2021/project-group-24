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
  LibrarianRepository librarianRepo;

  @Autowired
  ShiftRepository shiftRepo;

  // creates librarian, returns it so we know it's not null
  @Transactional
  public Librarian createLibrarian(
    // String currentUserId,
    String firstName,
    String lastName,
    String address,
    String email,
    String username,
    String password
  ) {
    validInput(firstName, lastName, address, email, username, password);
    // if (isHeadLibrarian(currentUserId)) {
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
    // } else {
    // throw new IllegalArgumentException(
    //   "You do not have permission to create a librarian."
    // );
    // }
  }

  //fire librarian
  @Transactional
  public Librarian deleteLibrarian(
    // String currentUserId,
    String idNum
  ) {
    if (shiftRepo.findShiftByLibrarianId(idNum).size() > 0) {
      throw new IllegalArgumentException(
        "Delete failed, this librarian has shifts assigned to them."
      );
    }
    // if (isHeadLibrarian(currentUserId)) {
    Librarian bye = librarianRepo.findUserByIdNum(idNum);
    librarianRepo.delete(bye);
    return bye;
    // } else {
    //   throw new IllegalArgumentException(
    //     "You do not have permission to update the librarian information."
    //   );
    // }
  }

  // updates librarian, returns it so we know it's not null
  //enter the fields you want to update. if you dont want to update, enter null
  @Transactional
  public Librarian updateLibrarian(
    // String currentUserId,
    String idNumOfAccountToUpdate,
    String firstName,
    String lastName,
    String address,
    String email,
    String username,
    String password
  ) {
    validUpdateInput(firstName, lastName, address, email, username, password);
    // if (isHeadLibrarian(currentUserId)) {
    Librarian librarianToUpdate = librarianRepo.findUserByIdNum(
      idNumOfAccountToUpdate
    );
    // System.out.println("NAME: " + librarianToUpdate.getFirstName());
    if (firstName != "none") {
      librarianToUpdate.setFirstName(firstName);
    } else {
      System.out.println("First Name will not change.");
    }
    if (lastName != "none") {
      librarianToUpdate.setLastName(lastName);
    } else {
      System.out.println("Last Name will not change.");
    }
    if (address != "none") {
      librarianToUpdate.setAddress(address);
    } else {
      System.out.println("Address will not change.");
    }
    if (email != "none") {
      librarianToUpdate.setEmail(email);
    } else {
      System.out.println("Email will not change.");
    }
    if (username != "none") {
      librarianToUpdate.setUsername(username);
    } else {
      System.out.println("Username will not change.");
    }
    if (password != "none") {
      librarianToUpdate.setPassword(password);
    } else {
      System.out.println("Password will not change.");
    }
    librarianRepo.save(librarianToUpdate);
    return librarianToUpdate;
    // } else {
    //   throw new IllegalArgumentException(
    //     "You do not have permission to update the librarian information."
    //   );
    // }
  }

  // looks for a librarian with the given ID number, returns them if found
  @Transactional
  public Librarian getLibrarian(String idNum) {
    Librarian librarian = librarianRepo.findUserByIdNum(idNum);
    return librarian;
  }

  @Transactional
  public List<Librarian> getAllLibrarians() {
    return toList(librarianRepo.findAll());
  }

  public static <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }
}
