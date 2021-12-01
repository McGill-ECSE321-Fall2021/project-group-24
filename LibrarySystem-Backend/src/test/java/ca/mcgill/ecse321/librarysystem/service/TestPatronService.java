package ca.mcgill.ecse321.librarysystem.service;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import ca.mcgill.ecse321.librarysystem.dao.HeadLibrarianRepository;
import ca.mcgill.ecse321.librarysystem.dao.ItemReservationRepository;
import ca.mcgill.ecse321.librarysystem.dao.LibrarianRepository;
import ca.mcgill.ecse321.librarysystem.dao.PatronRepository;
import ca.mcgill.ecse321.librarysystem.model.HeadLibrarian;
import ca.mcgill.ecse321.librarysystem.model.ItemReservation;
import ca.mcgill.ecse321.librarysystem.model.Librarian;
import ca.mcgill.ecse321.librarysystem.model.Patron;
import java.sql.Date;
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
public class TestPatronService {

  @Mock
  private PatronRepository patronRepo;

  @Mock
  private LibrarianRepository librarianRepo;

  @Mock
  private HeadLibrarianRepository headLibrarianRepo;

  @Mock
  private ItemReservationRepository itemReservationRepo;

  @InjectMocks
  private PatronService patronService;

  @InjectMocks
  ItemReservationService itemReservationService;

  private static final String username = "username";
  private static final String firstName = "first";
  private static final String lastName = "last";
  private static final String password = "BigStrongPassword";
  private static final String address = "Address";
  private static final String email = "firstlast@mail.com";
  private static final String id1 = "id";

  private static final String firstName2 = "first2";
  private static final String lastName2 = "last2";
  private static final String password2 = "BigStrongPassword2";
  private static final String address2 = "Address2";
  private static final String email2 = "first2last2@mail.com";
  private static final String username2 = "username2";
  private static final String id2 = "id2";
  private static final Date date = new Date(1, 1, 2025);

  @BeforeEach
  public void setMockOutput() {
    lenient()
      .when(patronRepo.findAll())
      .thenAnswer((InvocationOnMock invocation) -> {
        Patron patron = new Patron();
        patron.setFirstName(firstName);
        patron.setLastName(lastName);
        patron.setPassword(password);
        patron.setAddress(address);
        patron.setEmail(email);
        patron.setUsername(username);
        patron.setIsLoggedIn(true);
        patron.setIsResident(true);
        patron.setIsRegisteredOnline(true);
        patron.setIsVerified(false);

        Patron patron2 = new Patron();
        patron2.setFirstName(firstName2);
        patron2.setLastName(lastName2);
        patron2.setPassword(password2);
        patron2.setAddress(address2);
        patron2.setEmail(email2);
        patron2.setUsername(username2);
        patron2.setIsLoggedIn(true);
        patron2.setIsResident(true);
        patron2.setIsRegisteredOnline(false);
        patron2.setIsVerified(true);

        ArrayList<Patron> list = new ArrayList<Patron>();
        list.add(patron);
        list.add(patron2);

        return list;
      });

    lenient()
      .when(itemReservationRepo.findItemReservationsByIdNum(id2))
      .thenAnswer((InvocationOnMock invocation) -> {
        List<ItemReservation> list = new ArrayList<>();
        ItemReservation item = itemReservationService.createItemReservation(
          id2,
          date,
          id2,
          "one",
          true
        );
        list.add(item);
        return list;
      });

    lenient()
      .when(librarianRepo.findUserByIdNum(id1))
      .thenAnswer((InvocationOnMock invocation) -> {
        Librarian librarian = new Librarian();
        librarian.setIdNum(id1);
        librarian.setIsLoggedIn(true);
        return librarian;
      });

    lenient()
      .when(headLibrarianRepo.findUserByIdNum(id1))
      .thenAnswer((InvocationOnMock invocation) -> {
        HeadLibrarian librarian = new HeadLibrarian();
        librarian.setIdNum(id1);
        librarian.setIsLoggedIn(true);
        return librarian;
      });

    lenient()
      .when(patronRepo.findPatronByIdNum(id2))
      .thenAnswer((InvocationOnMock invocation) -> {
        Patron patron = new Patron();
        patron.setIdNum(id2);
        patron.setIsLoggedIn(true);
        return patron;
      });

    lenient()
      .when(patronRepo.findPatronByUsername(anyString()))
      .thenAnswer((InvocationOnMock invocation) -> {
        if (invocation.getArgument(0).equals(username2)) {
          Patron patron = new Patron();
          patron.setFirstName(firstName);
          patron.setLastName(lastName);
          patron.setAddress(address);
          patron.setEmail(email);
          patron.setUsername(username2);
          patron.setPassword(password);
          return patron;
        } else {
          return null;
        }
      });

    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient()
      .when(patronRepo.save(any(Patron.class)))
      .thenAnswer(returnParameterAsAnswer);
  }

  @Test
  public void testCreatePatronOnline() {
    Patron patron = null;

    try {
      patron =
        patronService.createPatronOnline(
          username,
          password,
          firstName,
          lastName,
          false,
          address,
          email
        );
    } catch (Exception e) {
      fail(e.getMessage());
    }

    assertNotNull(patron);
    assertEquals(firstName, patron.getFirstName());
    assertEquals(lastName, patron.getLastName());
    assertEquals(address, patron.getAddress());
    assertEquals(email, patron.getEmail());
    assertEquals(username, patron.getUsername());
    assertEquals(password, patron.getPassword());
    assertEquals(false, patron.getIsResident());
    assertEquals(false, patron.getIsVerified());
    assertEquals(true, patron.getIsRegisteredOnline());
    System.out.println(patron.getIdNum());
    System.out.println("Patron account created!");
  }

  @Test
  public void testCreatePatronOnlineWithEmptyUsername() {
    String error = null;
    try {
      patronService.createPatronOnline(
        null,
        password,
        firstName,
        lastName,
        false,
        address,
        email
      );
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertEquals(error, "Username cannot be empty.");
  }

  @Test
  public void testCreatePatronOnlineWithSpaceInUsername() {
    String error = null;
    try {
      patronService.createPatronOnline(
        "user name",
        password,
        firstName,
        lastName,
        false,
        address,
        email
      );
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertEquals(error, "Username cannot have spaces in it.");
  }

  @Test
  public void testNewOnlineAccountWithExistingUsername() {
    String error = null;
    try {
      patronService.createPatronOnline(
        username2,
        password2,
        firstName,
        lastName,
        false,
        address2,
        email
      );
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertEquals(error, "Username is already taken.");
  }

  @Test
  public void testCreatePatronOnlineWithPassword() {
    String error = null;
    try {
      patronService.createPatronOnline(
        username,
        null,
        firstName,
        lastName,
        false,
        address,
        email
      );
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertEquals(error, "Password cannot be empty.");
  }

  @Test
  public void testCreatePatronOnlineWithSpaceInPassword() {
    String error = null;
    try {
      patronService.createPatronOnline(
        username,
        "pass word",
        firstName,
        lastName,
        false,
        address,
        email
      );
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertEquals(error, "Password cannot have spaces in it.");
  }

  @Test
  public void TestCreatePatronOnlineWithEmptyFirstName() {
    String error = null;
    try {
      patronService.createPatronOnline(
        username,
        password,
        null,
        lastName,
        false,
        address,
        email
      );
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertEquals(error, "First name or last name cannot be blank.");
  }

  @Test
  public void TestCreatePatronOnlineWithEmptyLastName() {
    String error = null;
    try {
      patronService.createPatronOnline(
        username,
        password,
        firstName,
        null,
        false,
        address,
        email
      );
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertEquals(error, "First name or last name cannot be blank.");
  }

  @Test
  public void TestCreatePatronOnlineWithEmptyEmail() {
    String error = null;
    try {
      patronService.createPatronOnline(
        username,
        password,
        firstName,
        lastName,
        false,
        address,
        null
      );
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertEquals(
      error,
      "Email cannot be empty and it must include '@' and '.' symbols."
    );
  }

  @Test
  public void TestCreatePatronIRLSuccessfully() {
    Patron patron = null;

    try {
      patron =
        patronService.createPatronIRL(firstName, lastName, true, address);
    } catch (Exception e) {
      fail(e.getMessage());
    }

    assertNotNull(patron);
    assertEquals(firstName, patron.getFirstName());
    assertEquals(lastName, patron.getLastName());
    assertEquals(address, patron.getAddress());
    assertEquals(null, patron.getEmail());
    assertEquals(null, patron.getUsername());
    assertEquals(null, patron.getPassword());
    assertEquals(true, patron.getIsResident());
    assertEquals(true, patron.getIsVerified());
    assertEquals(false, patron.getIsRegisteredOnline());
    System.out.println(patron.getIdNum());
    System.out.println("Patron account created!");
  }

  @Test
  public void TestCreatePatronIRLWithEmptyFirstName() {
    String error = null;
    try {
      patronService.createPatronIRL(null, lastName, true, address);
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertEquals(error, "First name or last name cannot be blank.");
  }

  @Test
  public void TestCreatePatronIRLWithEmptyLastName() {
    String error = null;
    try {
      patronService.createPatronIRL(firstName, null, true, address);
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertEquals(error, "First name or last name cannot be blank.");
  }

  @Test
  public void TestCreatePatronIRLWithEmptyAddress() {
    String error = null;
    try {
      patronService.createPatronIRL(firstName, lastName, true, null);
    } catch (Exception e) {
      error = e.getMessage();
    }
    assertEquals(error, "Address cannot be empty.");
  }

  @Test
  public void TestGetPatronByIDNumSuccessfully() {
    Patron p = null;
    try {
      p = patronService.getPatronAccountByID(id2);
    } catch (Exception e) {
      fail(e.getMessage());
    }
    assertEquals(id2, p.getIdNum());
  }

  public void TestGetPatronByIDNumWithInvalidID() {
    try {
      patronService.getPatronAccountByID(id1);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "User with this ID does not exist.");
    }
  }

  @Test
  public void TestGetPatronByUsernameSuccessfully() {
    Patron p = null;
    try {
      p = patronService.getPatronAccountByUsername(username2);
    } catch (Exception e) {
      fail(e.getMessage());
    }
    assertEquals(username2, p.getUsername());
  }

  @Test
  public void TestGetPatronByInvalidUsername() {
    try {
      patronService.getPatronAccountByUsername(username2);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "User with this username does not exist.");
    }
  }

  @Test
  public void TestGetAllPatrons() {
    ArrayList<Patron> patrons = null;
    try {
      patrons = new ArrayList<>(patronService.getAllPatrons());
    } catch (Exception e) {
      fail(e.getMessage());
    }

    assertNotNull(patrons);
    assertEquals(2, patrons.size());
    assertEquals(username, patrons.get(0).getUsername());
    assertEquals(username2, patrons.get(1).getUsername());
  }
  // @Test // removes patron with correct parameters
  // public void testdeletePatron() {
  // 	boolean wasDeleted = false;
  // 	try {
  // 		wasDeleted = patronService.deletePatron(id2, id2);
  // 	} catch (Exception e) {
  // 		fail(e.getMessage());
  // 	}
  // }
}
