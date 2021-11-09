package ca.mcgill.ecse321.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.UserService;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class TestLibrarianService {

  @Mock
  private LibrarianRepository librarianDao;

  @InjectMocks
  // private UserService userService;
  private LibrarianService librarianService;

  private static final String emptyString = "";
  // private static final String testIdNum = "bobLibrarian-0";
  // private static final String wrongIdNum = "jeffLibrarian-0";
  private static final String testFirstName = "bobFirstName";
  private static final String wrongFirstName = "jeffFirstName";
  private static final String testLastName = "bobLastName";
  private static final String wrongLastName = "jeffLastName";
  private static final String testAddress = "bob st";
  private static final String wrongAddress = "jeff st";
  private static final String testEmail = "bob@bob.ca";
  private static final String wrongEmail = "jeff@jeff.ca";
  private static final String testUsername = "bobUsername";
  private static final String wrongUsername = "jeffUsername";
  private static final String testPassword = "bobPassword";
  private static final String wrongPassword = "bobPassword";

  @BeforeEach
  public void setMockOutput() {
    lenient()
      .when(librarianDao.findUserByUsername(anyString()))
      .thenAnswer((InvocationOnMock invocation) -> {
        if (invocation.getArgument(0).equals(testUsername)) {
          Librarian librarian = new Librarian();
          librarian.setFirstName(testFirstName);
          librarian.setLastName(testLastName);
          librarian.setAddress(testAddress);
          librarian.setEmail(testEmail);
          librarian.setUsername(testUsername);
          librarian.setPassword(testPassword);
          return librarian;
        } else {
          return null;
        }
      });
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient()
      .when(librarianDao.save(any(Librarian.class)))
      .thenAnswer(returnParameterAsAnswer);
  }

  @Test
  public void testCreateLibrarian() {
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          testFirstName,
          testLastName,
          testAddress,
          testEmail,
          testUsername,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(librarian);
    assertEquals(testFirstName, librarian.getFirstName());
    assertEquals(testLastName, librarian.getLastName());
    assertEquals(testAddress, librarian.getAddress());
    assertEquals(testEmail, librarian.getEmail());
    assertEquals(testUsername, librarian.getUsername());
    assertEquals(testPassword, librarian.getPassword());
  }

  @Test
  public void testGetAllLibrarians() {
    ArrayList<Librarian> librarians = null;
    try {
      librarians =
        new ArrayList<Librarian>(librarianService.getAllLibrarians());
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(librarians);
    assertEquals(1, librarians.size());
    assertEquals(testFirstName, librarians.get(0).getFirstName());
    assertEquals(testLastName, librarians.get(0).getLastName());
    assertEquals(testAddress, librarians.get(0).getAddress());
    assertEquals(testEmail, librarians.get(0).getEmail());
    assertEquals(testUsername, librarians.get(0).getUsername());
    assertEquals(testPassword, librarians.get(0).getPassword());
  }
}
