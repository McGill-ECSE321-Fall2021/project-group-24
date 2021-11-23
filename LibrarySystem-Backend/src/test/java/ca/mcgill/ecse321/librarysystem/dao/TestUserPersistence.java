package ca.mcgill.ecse321.librarysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Date;
import java.sql.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestUserPersistence {

  @Autowired
  private HeadLibrarianRepository headLibrarianRepository;

  @Autowired
  private LibrarianRepository librarianRepository;

  @Autowired
  private PatronRepository patronRepository;

  @BeforeEach
  public void clearDatabase() {
    // First, we clear registrations to avoid exceptions due to inconsistencies
    //      librarySystemRepository.deleteAll();
    // Then we can clear the other tables

    headLibrarianRepository.deleteAll();
    librarianRepository.deleteAll();

    patronRepository.deleteAll();
  }

  //------------------TESTING HEAD LIBRARIAN------------------------//
  @Test
  public void testPersistAndLoadHeadLibrarian() {
    // First example for object save/load
    HeadLibrarian headLibrarian = new HeadLibrarian();
    // First example for attribute save/load
    String username = "Head Librarian Username";
    String password = "Head Librarian Password";
    String email = "Head Librarian Email";
    String idNum = "Head Librarian IDNum";
    String firstName = "Head Librarian First Name";
    String lastName = "Head Librarian Last Name";

    headLibrarian.setUsername(username);
    headLibrarian.setPassword(password);
    headLibrarian.setEmail(email);
    headLibrarian.setIdNum(idNum);
    headLibrarian.setFirstName(firstName);
    headLibrarian.setLastName(lastName);

    headLibrarianRepository.save(headLibrarian);

    headLibrarian = null;

    headLibrarian = headLibrarianRepository.findUserByIdNum(idNum);

    assertNotNull(headLibrarian);

    assertEquals(username, headLibrarian.getUsername());
    assertEquals(password, headLibrarian.getPassword());
    assertEquals(email, headLibrarian.getEmail());
    assertEquals(idNum, headLibrarian.getIdNum());
    assertEquals(firstName, headLibrarian.getFirstName());
    assertEquals(lastName, headLibrarian.getLastName());
  }

  //------------------TESTING LIBRARIAN------------------------//
  @Test
  public void testPersistAndLoadLibrarian() {
    // First example for object save/load
    Librarian librarian = new Librarian();
    // First example for attribute save/load
    String username = "Librarian Username";
    String password = "Librarian Password";
    String email = "Librarian Email";
    String idNum = "Librarian IDNum";
    String firstName = "Librarian First Name";
    String lastName = "Librarian Last Name";

    librarian.setUsername(username);
    librarian.setPassword(password);
    librarian.setEmail(email);
    librarian.setIdNum(idNum);
    librarian.setFirstName(firstName);
    librarian.setLastName(lastName);

    librarianRepository.save(librarian);

    librarian = null;

    librarian = librarianRepository.findUserByIdNum(idNum);

    assertNotNull(librarian);

    assertEquals(username, librarian.getUsername());
    assertEquals(password, librarian.getPassword());
    assertEquals(email, librarian.getEmail());
    assertEquals(idNum, librarian.getIdNum());
    assertEquals(firstName, librarian.getFirstName());
    assertEquals(lastName, librarian.getLastName());
  }

  //------------------TESTING PATRON------------------------//
  @Test
  public void testPersistAndLoadPatron() {
    // First example for object save/load
    Patron patron = new Patron();
    // First example for attribute save/load
    String address = "Patron Address";
    boolean isVerified = true;
    boolean isResident = true;
    boolean isRegisteredOnline = true;
    String username = "Patron Username";
    String password = "Patron Password";
    String email = "Patron Email";
    String idNum = "Patron IDNum";
    String firstName = "Patron First Name";
    String lastName = "Patron Last Name";

    patron.setAddress(address);
    patron.setIsVerified(isVerified);
    patron.setIsResident(isResident);
    patron.setIsRegisteredOnline(isRegisteredOnline);
    patron.setUsername(username);
    patron.setPassword(password);
    patron.setEmail(email);
    patron.setIdNum(idNum);
    patron.setFirstName(firstName);
    patron.setLastName(lastName);

    patronRepository.save(patron);

    patron = null;

    patron = patronRepository.findPatronByIdNum(idNum);

    assertNotNull(patron);

    assertEquals(address, patron.getAddress());
    assertEquals(isVerified, patron.getIsVerified());
    assertEquals(isResident, patron.getIsResident());
    assertEquals(isRegisteredOnline, patron.getIsRegisteredOnline());
    assertEquals(username, patron.getUsername());
    assertEquals(password, patron.getPassword());
    assertEquals(email, patron.getEmail());
    assertEquals(idNum, patron.getIdNum());
    assertEquals(firstName, patron.getFirstName());
    assertEquals(lastName, patron.getLastName());
  }
}
