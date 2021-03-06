package ca.mcgill.ecse321.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class TestLibrarianService {

  @Mock
  private ShiftRepository shiftDao;

  @Mock
  private PatronRepository patronDao;

  @Mock
  private LibrarianRepository librarianDao;

  @Mock
  private HeadLibrarianRepository headLibrarianDao;

  @InjectMocks
  private LibrarianService librarianService;

  private static final String nullString = null;
  private static final String emptyString = "";
  // private static final String testIdNum = "bobLibrarian-0";
  // private static final String wrongIdNum = "jeffLibrarian-0";
  private static final String testFirstName = "bobFirstName";

  private static final String testLastName = "bobLastName";

  private static final String testAddress = "bob st";

  private static final String testEmail = "bob@bob.ca";

  private static final String testUsername = "bobUsername";

  private static final String testPassword = "bobPassword";

  @BeforeEach
  public void setMockOutput() {
    lenient()
      .when(headLibrarianDao.findUserByIdNum("admin"))
      .thenAnswer((InvocationOnMock invocation) -> {
        HeadLibrarian headLibrarian = new HeadLibrarian();
        headLibrarian.setIdNum("admin");
        headLibrarian.setIsLoggedIn(true);
        return headLibrarian;
      });
    lenient()
      .when(patronDao.findUserByIdNum("patron"))
      .thenAnswer((InvocationOnMock invocation) -> {
        Patron patron = new Patron();
        patron.setIdNum("patron");
        patron.setIsLoggedIn(true);
        return patron;
      });
    lenient()
      .when(librarianDao.findUserByIdNum("bobFirstNameLibrarian-1"))
      .thenAnswer((InvocationOnMock invocation) -> {
        Librarian librarian = new Librarian();
        librarian.setIdNum("bobFirstNameLibrarian-1");
        librarian.setFirstName(testFirstName);
        librarian.setLastName(testLastName);
        librarian.setAddress(testAddress);
        librarian.setEmail(testEmail);
        librarian.setUsername(testUsername);
        librarian.setPassword(testPassword);
        return librarian;
      });
    lenient()
      .when(shiftDao.findShiftByLibrarianId("bobFirstNameLibrarian-1"))
      .thenAnswer((InvocationOnMock invocation) -> {
        Shift shift = new Shift();
        List<Shift> shiftList = new ArrayList<>();
        shift.setLibrarianId("bobFirstNameLibrarian-1");
        shiftList.add(shift);
        return shiftList;
      });
    lenient()
      .when(librarianDao.findUserByIdNum("bobFirstNameLibrarian-3"))
      .thenAnswer((InvocationOnMock invocation) -> {
        Librarian librarian = new Librarian();
        librarian.setIdNum("bobFirstNameLibrarian-3");
        librarian.setFirstName(testFirstName);
        librarian.setLastName(testLastName);
        librarian.setAddress(testAddress);
        librarian.setEmail(testEmail);
        librarian.setUsername(testUsername);
        librarian.setPassword(testPassword);
        return librarian;
      });
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
    lenient()
      .when(librarianDao.findAll())
      .thenAnswer((InvocationOnMock invocation) -> {
        Librarian librarian = new Librarian();
        librarian.setFirstName(testFirstName);
        librarian.setLastName(testLastName);
        librarian.setAddress(testAddress);
        librarian.setEmail(testEmail);
        librarian.setUsername(testUsername);
        librarian.setPassword(testPassword);
        Librarian librarian2 = new Librarian();
        librarian2.setFirstName(testFirstName + "2");
        librarian2.setLastName(testLastName + "2");
        librarian2.setAddress(testAddress + "2");
        librarian2.setEmail(testEmail + "2");
        librarian2.setUsername(testUsername + "2");
        librarian2.setPassword(testPassword + "2");
        ArrayList<Librarian> list = new ArrayList<Librarian>();
        list.add(librarian);
        list.add(librarian2);

        return list;
      });
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient()
      .when(librarianDao.save(any(Librarian.class)))
      .thenAnswer(returnParameterAsAnswer);

    lenient()
      .when(headLibrarianDao.save(any(HeadLibrarian.class)))
      .thenAnswer(returnParameterAsAnswer);

    lenient()
      .when(patronDao.save(any(Patron.class)))
      .thenAnswer(returnParameterAsAnswer);

    lenient()
      .when(shiftDao.save(any(Shift.class)))
      .thenAnswer(returnParameterAsAnswer);
  }

  @Test
  //Create a librarian
  public void testCreateLibrarian() {
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
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
    System.out.println(librarian.getIdNum());
    System.out.println("Librarian account created!");
  }

  @Test
  //Create a librarian with empty username (empty string)
  public void testCreateLibrarianWithEmptyUsername() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          testLastName,
          testAddress,
          testEmail,
          emptyString,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your username.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with empty username (null)
  public void testCreateLibrarianWithNullUsername() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          testLastName,
          testAddress,
          testEmail,
          nullString,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your username.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with space in username
  public void testCreateLibrarianWithUsernameSpace() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          testLastName,
          testAddress,
          testEmail,
          "User Name",
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter a username without spaces.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with empty password (empty string)
  public void testCreateLibrarianWithEmptyPassword() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          testLastName,
          testAddress,
          testEmail,
          testUsername,
          emptyString
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your password.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with empty password (null)
  public void testCreateLibrarianWithNullPassword() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          testLastName,
          testAddress,
          testEmail,
          testUsername,
          nullString
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your password.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with space in password
  public void testCreateLibrarianWithPasswordSpace() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          testLastName,
          testAddress,
          testEmail,
          testUsername,
          "Pass Word"
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter a password without spaces.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with empty first name (empty string)
  public void testCreateLibrarianWithEmptyFirstName() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          emptyString,
          testLastName,
          testAddress,
          testEmail,
          testUsername,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your first name.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with empty first name (null)
  public void testCreateLibrarianWithNullFirstName() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          nullString,
          testLastName,
          testAddress,
          testEmail,
          testUsername,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your first name.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with empty last name (empty string)
  public void testCreateLibrarianWithEmptyLastName() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          emptyString,
          testAddress,
          testEmail,
          testUsername,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your last name.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with empty last name (null)
  public void testCreateLibrarianWithNullLastName() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          nullString,
          testAddress,
          testEmail,
          testUsername,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your last name.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with empty email (empty string)
  public void testCreateLibrarianWithEmptyEmail() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          testLastName,
          testAddress,
          emptyString,
          testUsername,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your email.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with empty email (null)
  public void testCreateLibrarianWithNullEmail() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          testLastName,
          testAddress,
          nullString,
          testUsername,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your email.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with space in email
  public void testCreateLibrarianWithEmailSpace() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          testLastName,
          testAddress,
          "test@test .ca",
          testUsername,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter an email without spaces.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with empty address (empty string)
  public void testCreateLibrarianWithEmptyAddress() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          testLastName,
          emptyString,
          testEmail,
          testUsername,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your address.", error);
    System.out.println(error);
  }

  @Test
  //Create a librarian with empty address (null)
  public void testCreateLibrarianWithNullAddress() {
    String error = null;
    Librarian librarian = null;
    try {
      librarian =
        librarianService.createLibrarian(
          "admin",
          testFirstName,
          testLastName,
          nullString,
          testEmail,
          testUsername,
          testPassword
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Please enter your address.", error);
    System.out.println(error);
  }

  @Test
  //Get all librarians
  public void testGetAllLibrarians() {
    ArrayList<Librarian> librarians = null;

    try {
      librarians =
        new ArrayList<Librarian>(librarianService.getAllLibrarians("admin"));
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(librarians);
    assertEquals(2, librarians.size());
    assertEquals(testFirstName, librarians.get(0).getFirstName());
    assertEquals(testLastName, librarians.get(0).getLastName());
    assertEquals(testAddress, librarians.get(0).getAddress());
    assertEquals(testEmail, librarians.get(0).getEmail());
    assertEquals(testUsername, librarians.get(0).getUsername());
    assertEquals(testPassword, librarians.get(0).getPassword());
    assertEquals(testFirstName + "2", librarians.get(1).getFirstName());
    assertEquals(testLastName + "2", librarians.get(1).getLastName());
    assertEquals(testAddress + "2", librarians.get(1).getAddress());
    assertEquals(testEmail + "2", librarians.get(1).getEmail());
    assertEquals(testUsername + "2", librarians.get(1).getUsername());
    assertEquals(testPassword + "2", librarians.get(1).getPassword());

    for (Librarian librarian : librarians) {
      System.out.println(librarian.getFirstName());
    }
  }

  @Test
  //Get all librarians as a patron
  public void testGetAllLibrariansAsPatron() {
    String error = null;
    ArrayList<Librarian> librarians = null;

    try {
      librarians =
        new ArrayList<Librarian>(librarianService.getAllLibrarians("patron"));
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarians);
    assertEquals(
      "You are not authorized to do this. Only the Head Librarian can.",
      error
    );
  }

  @Test
  //GET librarian account successfully using idNum
  public void testGetLibrarian() {
    Librarian librarian = null;

    try {
      librarian =
        librarianService.getLibrarian("admin", "bobFirstNameLibrarian-1");
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(librarian);
    assertEquals("bobFirstNameLibrarian-1", librarian.getIdNum());
  }

  @Test
  //Attempt to get librarian account as a patron
  public void testGetLibrarianAsPatron() {
    String error = null;
    Librarian librarian = null;

    try {
      librarian =
        librarianService.getLibrarian("patron", "bobFirstNameLibrarian-1");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals(
      "You are not authorized to do this. Only the Head Librarian can.",
      error
    );
  }

  @Test
  //Get librarian account using an invalid IdNum
  public void testGetLibrarianUsingInvalidIdNum() {
    String error = null;
    Librarian librarian = null;

    try {
      librarian =
        librarianService.getLibrarian("admin", "bobFirstNameLibrarian-2");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(librarian);
    assertEquals("Librarian not found.", error);
  }

  @Test
  //Delete a librarian with a valid IdNum and shift as an admin
  public void testDeleteLibrarianWithShift() {
    String error = null;
    Librarian deletedLibrarian = null;

    try {
      deletedLibrarian =
        librarianService.deleteLibrarian("admin", "bobFirstNameLibrarian-1");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(deletedLibrarian);
    assertEquals(
      "Delete failed, this librarian has shifts assigned to them.",
      error
    );
  }

  @Test
  //Delete a librarian as not a head librarian
  public void testDeleteLibrarianAsNotHeadLibrarian() {
    String error = null;
    Librarian deletedLibrarian = null;

    try {
      deletedLibrarian =
        librarianService.deleteLibrarian("patron", "bobFirstNameLibrarian-3");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(deletedLibrarian);
    assertEquals(
      "You are not authorized to do this. Only the Head Librarian can.",
      error
    );
  }

  @Test
  //Delete a valid librarian with no shift as a head librarian
  public void testDeleteLibrarian() {
    Librarian deletedLibrarian = null;

    try {
      deletedLibrarian =
        librarianService.deleteLibrarian("admin", "bobFirstNameLibrarian-3");
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(deletedLibrarian);
    assertEquals("bobFirstNameLibrarian-3", deletedLibrarian.getIdNum());
  }
}
