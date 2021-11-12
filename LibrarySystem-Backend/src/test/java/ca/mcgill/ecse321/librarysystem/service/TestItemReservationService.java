package ca.mcgill.ecse321.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import ca.mcgill.ecse321.librarysystem.controller.ItemReservationController;
import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hibernate.annotations.SourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class TestItemReservationService {

	@Mock
	private ItemReservationRepository itemReservationDao;

	@Mock
	private PatronRepository patronDao;

	@Mock
	private ItemRepository itemDao;

	@Mock
	private HeadLibrarianRepository headLibrarianDao;

	@Mock
	private LibrarianRepository librarianDao;

	@InjectMocks
	private ItemReservationService itemReservationService;

	private static final String correctString = "test";
	private static final String wrongString = "wrong";

	private static final String correctIRString = "correctItemReservation";
	private static final String wrongIRString = "wrongItemReservation";
	private static final Date correctStartDate = Date.valueOf(LocalDate.now().minusDays(1));
	private static final Date correctEndDate = Date.valueOf(LocalDate.now().plusDays(6));
	private static final Date wrongStartDate = Date.valueOf("2019-01-10");
	private static final Date wrongEndDate = Date.valueOf("2019-01-17");

	private static final Date correctDate = Date.valueOf(LocalDate.now().minusDays(1));
	private static final Date wrongDate = Date.valueOf("2019-01-10");

	private static final String emptyString = "";
	private static final int testID = 1;
	private static final int wrongID = 0;

	@BeforeEach
	public void setMockOutput() {
		lenient()
			.when(librarianDao.findUserByIdNum("librarian"))
			.thenAnswer((InvocationOnMock invocation) -> {
				Librarian librarian = new Librarian();
				librarian.setIdNum("librarian");
				librarian.setIsLoggedIn(true);
				System.out.print("Librarian logged in");
				return librarian;
			});
		lenient()
			.when(patronDao.findPatronByIdNum("patron"))
			.thenAnswer((InvocationOnMock invocation) -> {
				Patron patron = new Patron();
				patron.setIdNum("patron");
				patron.setIsLoggedIn(true);
				System.out.print("Patron logged in");
				return patron;
			});
		lenient()
			.when(headLibrarianDao.findUserByIdNum("admin"))
			.thenAnswer((InvocationOnMock invocation) -> {
				HeadLibrarian headLibrarian = new HeadLibrarian();
				headLibrarian.setIdNum("admin");
				headLibrarian.setIsLoggedIn(true);
				System.out.print("KKOOOL?");
				return headLibrarian;
			});
		lenient()
			.when(patronDao.findUserByIdNum("patron"))
			.thenAnswer((InvocationOnMock invocation) -> {
				Patron patron = new Patron();
				patron.setIdNum("patron");
				patron.setIsLoggedIn(true);
				System.out.print("Patron logged in");
				return patron;
			});
		lenient()
			.when(patronDao.findUserByIdNum("patron2"))
			.thenAnswer((InvocationOnMock invocation) -> {
				Patron patron = new Patron();
				patron.setIdNum("patron2");
				patron.setIsLoggedIn(true);
				System.out.print("Patron2 logged in");
				return patron;
			});
		lenient()
			.when(patronDao.findUserByIdNum("patron10"))
			.thenAnswer((InvocationOnMock invocation) -> {
				Patron patron = new Patron();
				patron.setIdNum("patron10");
				patron.setIsLoggedIn(true);
				System.out.print("Patron10 logged in");
				return patron;
			});
		lenient()
			.when(itemReservationDao.findItemReservationsByIdNum("patron10"))
			.thenAnswer((InvocationOnMock invocation) -> {
				Patron patron = new Patron();
				patron.setIdNum("patron10");
				patron.setIsLoggedIn(true);
				ItemReservation itemReservation1 = new ItemReservation();
				ItemReservation itemReservation2 = new ItemReservation();
				ItemReservation itemReservation3 = new ItemReservation();
				ItemReservation itemReservation4 = new ItemReservation();
				ItemReservation itemReservation5 = new ItemReservation();
				ItemReservation itemReservation6 = new ItemReservation();
				ItemReservation itemReservation7 = new ItemReservation();
				ItemReservation itemReservation8 = new ItemReservation();
				ItemReservation itemReservation9 = new ItemReservation();
				ItemReservation itemReservation10 = new ItemReservation();
				itemReservation1.setIdNum("patron10");

				itemReservation2.setIdNum("patron10");
				itemReservation3.setIdNum("patron10");
				itemReservation4.setIdNum("patron10");
				itemReservation5.setIdNum("patron10");
				itemReservation6.setIdNum("patron10");
				itemReservation7.setIdNum("patron10");
				itemReservation8.setIdNum("patron10");
				itemReservation9.setIdNum("patron10");
				itemReservation10.setIdNum("patron10");

				itemReservation1.setStartDate(Date.valueOf(LocalDate.now()));
				itemReservation1.setEndDate(Date.valueOf(LocalDate.now().plusDays(13)));
				itemReservation2.setStartDate(Date.valueOf(LocalDate.now()));
				itemReservation2.setEndDate(Date.valueOf(LocalDate.now().plusDays(13)));
				itemReservation3.setStartDate(Date.valueOf(LocalDate.now()));
				itemReservation3.setEndDate(Date.valueOf(LocalDate.now().plusDays(13)));
				itemReservation4.setStartDate(Date.valueOf(LocalDate.now()));
				itemReservation4.setEndDate(Date.valueOf(LocalDate.now().plusDays(13)));
				itemReservation5.setStartDate(Date.valueOf(LocalDate.now()));
				itemReservation5.setEndDate(Date.valueOf(LocalDate.now().plusDays(13)));
				itemReservation6.setStartDate(Date.valueOf(LocalDate.now()));
				itemReservation6.setEndDate(Date.valueOf(LocalDate.now().plusDays(13)));
				itemReservation7.setStartDate(Date.valueOf(LocalDate.now()));
				itemReservation7.setEndDate(Date.valueOf(LocalDate.now().plusDays(13)));
				itemReservation8.setStartDate(Date.valueOf(LocalDate.now()));
				itemReservation8.setEndDate(Date.valueOf(LocalDate.now().plusDays(13)));
				itemReservation9.setStartDate(Date.valueOf(LocalDate.now()));
				itemReservation9.setEndDate(Date.valueOf(LocalDate.now().plusDays(13)));
				itemReservation10.setStartDate(Date.valueOf(LocalDate.now()));
				itemReservation10.setEndDate(Date.valueOf(LocalDate.now().plusDays(13)));

				ArrayList<ItemReservation> itemReservations = new ArrayList<ItemReservation>();
				itemReservations.add(itemReservation1);
				itemReservations.add(itemReservation2);
				itemReservations.add(itemReservation3);
				itemReservations.add(itemReservation4);
				itemReservations.add(itemReservation5);
				itemReservations.add(itemReservation6);
				itemReservations.add(itemReservation7);
				itemReservations.add(itemReservation8);
				itemReservations.add(itemReservation9);
				itemReservations.add(itemReservation10);

				System.out.print("Patron10 logged in");
				return itemReservations;
			});
		lenient()
			.when(patronDao.findUserByIdNum("notLogged"))
			.thenAnswer((InvocationOnMock invocation) -> {
				Patron patron = new Patron();
				patron.setIdNum("notLogged");
				patron.setIsLoggedIn(false);
				return patron;
			});
		lenient()
			.when(itemReservationDao.findItemReservationByItemReservationId("bob"))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals("bob")) {
					ItemReservation itemReservation = new ItemReservation();
					itemReservation.setNumOfRenewalsLeft(4);
					itemReservation.setItemReservationId("bob");
					itemReservation.setIdNum("patron2");
					itemReservation.setItemNumber(correctString);
					itemReservation.setIsCheckedOut(true);
					itemReservation.setStartDate(correctStartDate);
					itemReservation.setEndDate(correctEndDate);
					return itemReservation;
				} else {
					return null;
				}
			});
		lenient()
			.when(itemReservationDao.findItemReservationByItemReservationId(correctIRString))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals(correctIRString)) {
					ItemReservation itemReservation = new ItemReservation();
					itemReservation.setNumOfRenewalsLeft(4);
					itemReservation.setItemReservationId(correctIRString);
					itemReservation.setIdNum("patron");
					itemReservation.setItemNumber(correctString);
					itemReservation.setIsCheckedOut(true);
					itemReservation.setStartDate(correctStartDate);
					itemReservation.setEndDate(correctEndDate);
					return itemReservation;
				} else {
					return null;
				}
			});
		lenient()
			.when(itemReservationDao.findItemReservationByItemReservationId(wrongIRString))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals(wrongIRString)) {
					ItemReservation itemReservation = new ItemReservation();
					itemReservation.setNumOfRenewalsLeft(0);
					itemReservation.setItemReservationId(wrongIRString);
					itemReservation.setIdNum("patron");
					itemReservation.setItemNumber(correctString);
					itemReservation.setIsCheckedOut(true);
					itemReservation.setStartDate(correctStartDate);
					itemReservation.setEndDate(correctEndDate);
					return itemReservation;
				} else {
					return null;
				}
			});
		lenient()
			.when(itemReservationDao.findItemReservationsByItemNumber("bbi2"))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals("bbi2")) {
					ArrayList<ItemReservation> reservations = new ArrayList<ItemReservation>();
					ItemReservation reservation = new ItemReservation();
					reservation.setStartDate(Date.valueOf(LocalDate.now()));
					reservation.setEndDate(Date.valueOf(LocalDate.now().plusDays(14)));
					reservation.setIdNum("patron");
					reservation.setItemNumber("bbi2");
					reservations.add(reservation);
					return reservations;
				} else {
					return null;
				}
			});
		lenient()
			.when(itemReservationDao.findItemReservationsByItemNumber("bbi1"))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals("bbi1")) {
					ArrayList<ItemReservation> reservations = new ArrayList<ItemReservation>();
					ItemReservation reservation = new ItemReservation();
					reservation.setStartDate(Date.valueOf(LocalDate.now()));
					reservation.setEndDate(Date.valueOf(LocalDate.now().plusDays(14)));
					reservation.setIdNum("patron");
					reservation.setItemNumber("bbi1");
					reservations.add(reservation);
					return reservations;
				} else {
					return null;
				}
			});

		lenient()
			.when(itemReservationDao.findItemReservationsByItemNumber(correctString))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals(correctString)) {
					ItemReservation itemReservation = new ItemReservation();
					itemReservation.setNumOfRenewalsLeft(4);
					itemReservation.setItemReservationId(correctIRString);
					itemReservation.setIdNum("patron");
					itemReservation.setItemNumber(correctString);
					itemReservation.setIsCheckedOut(true);
					itemReservation.setStartDate(correctStartDate);
					itemReservation.setEndDate(correctEndDate);
					ArrayList<ItemReservation> reservations = new ArrayList<ItemReservation>();
					reservations.add(itemReservation);
					return reservations;
				} else {
					return null;
				}
			});
		lenient()
			.when(itemDao.findItemByItemNumber("bbi2"))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals("bbi2")) {
					Archive archive = new Archive();
					archive.setItemTitle("bbi2");
					archive.setDescription("bbi2");
					archive.setImageUrl("bbi2");
					archive.setItemNumber("bbi2");
					archive.setGenre("bbi2");
					archive.setPublishDate(correctDate);
					archive.setIsReservable(true);
					archive.setType(Item.Type.Archive);
					return archive;
				} else {
					return null;
				}
			});
		lenient()
			.when(itemDao.findItemByItemNumber("JIGG"))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals("JIGG")) {
					Archive archive = new Archive();
					archive.setItemTitle("JIGG");
					archive.setDescription("JIGG");
					archive.setImageUrl("JIGG");
					archive.setItemNumber("JIGG");
					archive.setGenre("JIGG");
					archive.setPublishDate(correctDate);
					archive.setIsReservable(true);
					archive.setType(Item.Type.Archive);
					return archive;
				} else {
					return null;
				}
			});
		lenient()
			.when(itemDao.findItemByItemNumber("bbi1"))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals("bbi1")) {
					Archive archive = new Archive();
					archive.setItemTitle(correctString);
					archive.setDescription(correctString);
					archive.setImageUrl(correctString);
					archive.setItemNumber("bbi1");
					archive.setGenre(correctString);
					archive.setPublishDate(correctDate);
					archive.setIsReservable(true);
					archive.setType(Item.Type.Archive);
					return archive;
				} else {
					return null;
				}
			});
		lenient()
			.when(itemDao.findItemByItemNumber(correctString))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals(correctString)) {
					Archive archive = new Archive();
					archive.setItemTitle(correctString);
					archive.setDescription(correctString);
					archive.setImageUrl(correctString);
					archive.setItemNumber(correctString);
					archive.setGenre(correctString);
					archive.setPublishDate(correctDate);
					archive.setIsReservable(true);
					archive.setType(Item.Type.Archive);
					return archive;
				} else {
					return null;
				}
			});
		lenient()
			.when(itemDao.findAll())
			.thenAnswer((InvocationOnMock invocation) -> {
				Archive archive1 = new Archive();
				archive1.setItemTitle(correctString);
				archive1.setDescription(correctString);
				archive1.setImageUrl(correctString);
				archive1.setItemNumber(correctString);
				archive1.setGenre(correctString);
				archive1.setPublishDate(correctDate);
				archive1.setIsReservable(true);
				archive1.setType(Item.Type.Archive);

				Archive archive2 = new Archive();
				archive2.setItemTitle(correctString + "2");
				archive2.setDescription(correctString + "2");
				archive2.setImageUrl(correctString + "2");
				archive2.setItemNumber(correctString + "2");
				archive2.setGenre(correctString + "2");
				archive2.setPublishDate(correctDate);
				archive2.setIsReservable(false);
				archive2.setType(Item.Type.Archive);
				List<Item> list = new ArrayList<Item>();
				list.add(archive1);
				list.add(archive2);

				return list;
			});
		lenient()
			.when(itemReservationDao.findAll())
			.thenAnswer((InvocationOnMock invocation) -> {
				ItemReservation itemReservation1 = new ItemReservation();
				itemReservation1.setNumOfRenewalsLeft(4);
				itemReservation1.setItemReservationId(correctIRString);
				itemReservation1.setIdNum("patron");
				itemReservation1.setItemNumber(correctString);
				itemReservation1.setIsCheckedOut(true);
				itemReservation1.setStartDate(correctStartDate);
				itemReservation1.setEndDate(correctEndDate);

				ItemReservation itemReservation2 = new ItemReservation();
				itemReservation2.setNumOfRenewalsLeft(4);
				itemReservation2.setItemReservationId(correctIRString + "2");
				itemReservation2.setIdNum("patron" + "2");
				itemReservation2.setItemNumber(correctString + "2");
				itemReservation2.setIsCheckedOut(true);
				itemReservation2.setStartDate(correctStartDate);
				itemReservation2.setEndDate(correctEndDate);
				List<ItemReservation> list = new ArrayList<ItemReservation>();
				list.add(itemReservation1);
				list.add(itemReservation2);

				return list;
			});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient()
			.when(itemReservationDao.save(any(ItemReservation.class)))
			.thenAnswer(returnParameterAsAnswer);
	}

	@Test
	//Create item reservation for an item with no other reservations
	public void testItemReservationNoReservations() {
		String error = null;
		Item item = null;
		ItemReservation itemReservation = null;
		try {
			item = itemDao.findItemByItemNumber("JIGG");
			if (itemReservationDao.findItemReservationsByItemNumber("JIGG").size() == 0) {
				itemReservation =
					itemReservationService.createItemReservation(
						"patron",
						null,
						"patron",
						"JIGG",
						false
					);
			}
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		System.out.println(error);
		assertNotNull(itemReservation);
		assertEquals(Date.valueOf(LocalDate.now()), itemReservation.getStartDate());
		System.out.println("Item reservation created!");
	}

	@Test
	//Create two item reservations for an item
	public void testItemReservationTwoReservations() {
		String error = null;
		Item item = null;
		ItemReservation itemReservation = null;
		ItemReservation itemReservation2 = null;
		try {
			item = itemDao.findItemByItemNumber(correctString);
			itemReservation2 =
				itemReservationService.createItemReservation(
					"patron",
					null,
					"patron",
					"bbi2",
					false
				);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		System.out.println(error);
		assertEquals(Date.valueOf(LocalDate.now().plusDays(14)), itemReservation2.getStartDate());
		System.out.println("Item reservation created!");
	}

	@Test
	//Create two item reservations for an item
	public void testReservedItemRenewal() {
		String error = null;
		Item item = null;
		ItemReservation itemReservation2 = null;
		try {
			itemReservation2 =
				itemReservationService.createItemReservation(
					"patron",
					null,
					"patron",
					"bbi1",
					false
				);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		System.out.println(error);
		assertNotNull(itemReservation2);
		assertEquals(Date.valueOf(LocalDate.now().plusDays(14)), itemReservation2.getStartDate());
		System.out.println("Item reservation created!");
	}

	@Test
	//Renew an itemReservation after it's been created
	public void testItemRenewal() {
		String error = null;
		ItemReservation itemReservation = null;
		try {
			itemReservation = itemReservationService.renewByItemReservationId("patron2", "bob");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		System.out.println(error);
		assertNotNull(itemReservation);
		assertEquals(Date.valueOf(LocalDate.now().plusDays(20)), itemReservation.getEndDate());
		System.out.println("Item reservation created!");
	}

	@Test
	//Renew an itemReservation as another patron
	public void testItemRenewalOtherPatron() {
		String error = null;
		Item item = null;
		ItemReservation itemReservation = itemReservationDao.findItemReservationByItemReservationId(
			correctIRString
		);
		try {
			itemReservation =
				itemReservationService.renewByItemReservationId(
					"patron2",
					itemReservation.getItemReservationId()
				);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		System.out.println(error);
		assertEquals(
			"Only a librarian or the patron who's reservation it is can view a reservation",
			error
		);
	}

	@Test
	//Renew an item reservation when it has no renewals left
	public void testItemReservationNoRenewalsLeft() {
		String error = null;
		Item item = null;
		ItemReservation itemReservation = itemReservationDao.findItemReservationByItemReservationId(
			wrongIRString
		);
		try {
			itemReservation =
				itemReservationService.renewByItemReservationId(
					"patron",
					itemReservation.getItemReservationId()
				);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		System.out.println(error);
		assertEquals("No more renewals left", error);
	}

	@Test
	//Create an itemReservation for an item that does not exist
	public void testItemReservationNonexistentItem() {
		String error = null;
		Item item = null;
		ItemReservation itemReservation = itemReservationDao.findItemReservationByItemReservationId(
			wrongIRString
		);
		try {
			itemReservation =
				itemReservationService.createItemReservation(
					"patron",
					null,
					"patron",
					wrongString,
					false
				);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		System.out.println(error);
		assertEquals("The item does not exist", error);
	}

	@Test
	//Create item reservation for a user that isn't logged in
	public void testItemReservationNotLoggedIn() {
		String error = null;
		Item item = null;
		ItemReservation itemReservation = null;
		try {
			item = itemDao.findItemByItemNumber(correctString);
			itemReservation =
				itemReservationService.createItemReservation(
					"notLogged",
					null,
					"notLogged",
					correctString,
					false
				);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(
			"You do not have permission to create an item reservation for this patron at this time",
			error
		);
		System.out.println(
			"You do not have permission to create an item reservation for this patron at this time"
		);
	}

	@Test
	//Create item reservation for a patron with 10 reservations
	public void testItemReservationTenReservations() {
		String error = null;
		Item item = null;
		ItemReservation itemReservation = null;
		Patron patron = patronDao.findPatronByIdNum("patron10");

		List<ItemReservation> itemReservations = itemReservationDao.findItemReservationsByIdNum(
			"patron10"
		);
		try {
			itemReservation =
				itemReservationService.createItemReservation(
					"patron10",
					null,
					"patron10",
					correctString,
					false
				);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		System.out.println(error);
		assertEquals("Patron cannot have more than 10 reservations at a time", error);
	}

	@Test
	//Attempt to checkout an item which has a future reservation already
	public void testItemReservationFutureCheckout() {
		String error = null;
		Item item = null;
		ItemReservation itemReservation = itemReservationDao.findItemReservationByItemReservationId(
			correctIRString
		);
		try {
			itemReservation =
				itemReservationService.createItemReservation(
					"patron",
					null,
					"patron",
					wrongString,
					false
				);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		System.out.println(error);
		assertEquals("The item does not exist", error);
	}
}
