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
	HeadLibrarianRepository headLibrarianRepository;

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
       "You do not have permission to create a librarian."
     );
     }
  }

  // @Transactional
  // public Librarian createLibrarian(String idNum) {
  //   Librarian librarian = new Librarian();

  //   librarian.setIdNum(idNum);

  //   librarianRepo.save(librarian);
  //   return librarian;
  // }

  //fire librarian
  @Transactional
  public Librarian deleteLibrarian(String idNum) {
    Librarian bye = librarianRepo.findUserByIdNum(idNum);
    librarianRepo.delete(bye);
    return bye;
  }

  // updates librarian, returns it so we know it's not null
  @Transactional
  public Librarian updateLibrarian(
    String currentUserId,
    String idNumOfAccountToUpdate,
    String firstName,
    String lastName,
    String address,
    String email,
    String username,
    String password
  ) {
    if (isHeadLibrarian(currentUserId)) {
      Librarian librarian = librarianRepo.findUserByIdNum(
        idNumOfAccountToUpdate
      );
      librarian.setUsername(username);
      librarian.setPassword(password);
      librarian.setFirstName(firstName);
      librarian.setLastName(lastName);
      librarian.setEmail(email);
      librarian.setAddress(address);

      librarianRepo.save(librarian);
      return librarian;
    } else {
      throw new IllegalArgumentException(
        "You do not have permission to update the librarian information."
      );
    }
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
  
  private boolean isHeadLibrarian(String currentUserId) {
	    System.out.println("Current User ID: " + currentUserId);
	    System.out.println("HERE");
	    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(currentUserId);
	    System.out.println("HERE2");
	    if (currentHeadLibrarian == null || !currentHeadLibrarian.getIsLoggedIn()) {
	      throw new IllegalArgumentException(
	        "You are not authorized to do this. Only the Head Librarian can."
	      );
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
