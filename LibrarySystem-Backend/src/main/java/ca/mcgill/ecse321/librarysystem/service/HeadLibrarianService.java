package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HeadLibrarianService {

  @Autowired
  HeadLibrarianRepository headLibrarianRepo;

  // creates head librarian, returns it so we know it's not null
  @Transactional
  public HeadLibrarian createHeadLibrarian(
    String firstName,
    String lastName,
    String address,
    String email,
    String username,
    String password
  ) {
    HeadLibrarian headLibrarian = new HeadLibrarian();
    String idNum = "admin";
    headLibrarian.setUsername(username);
    headLibrarian.setPassword(password);
    headLibrarian.setFirstName(firstName);
    headLibrarian.setLastName(lastName);
    headLibrarian.setEmail(email);
    headLibrarian.setIdNum(idNum);
    headLibrarian.setAddress(address);

    headLibrarianRepo.save(headLibrarian);
    return headLibrarian;
  }

  // looks for a head librarian with the given ID number, returns them if found
  @Transactional
  public HeadLibrarian getHeadLibrarian(String idNum) {
    HeadLibrarian headLibrarian = headLibrarianRepo.findUserByIdNum(idNum);
    return headLibrarian;
  }

  @Transactional
  public List<HeadLibrarian> getAllHeadLibrarians() {
    return toList(headLibrarianRepo.findAll());
  }
  
  

  public <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }
}
