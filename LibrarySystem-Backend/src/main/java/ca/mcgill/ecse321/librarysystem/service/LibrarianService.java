package ca.mcgill.ecse321.librarysystem.service;

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

  // creates librarian, returns it so we know it's not null
  @Transactional
  public Librarian createLibrarian(
    String idNum,
    String firstName,
    String lastName,
    String address,
    String email,
    String username,
    String password
  ) {
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
  }

  //fire librarian
  @Transactional
  public Librarian deleteLibrarian(Librarian librarian) {
    librarianRepo.delete(librarian);
    return librarian;
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

  private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }
}
