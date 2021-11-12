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
import java.sql.Date;
import java.sql.Time;
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
public class TestBookService {

	@Mock
	private ItemRepository bookDao;

	@Mock
	private LibrarianRepository librarianDao;

	@Mock
	private PatronRepository patronDao;

	@Mock
	private HeadLibrarianRepository headLibrarianDao;

	@InjectMocks
	private ItemService bookService;

	private static final String correctString = "test";
	private static final String wrongString = "wrong";
	private static final Date correctDate = Date.valueOf("2021-11-10");
	private static final Date wrongDate = Date.valueOf("2019-01-10");
	private static final String emptyString = "";
	private static final int testID = 1;
	private static final int wrongID = 0;

	@BeforeEach
	public void setMockOutput() {
		lenient()
			.when(librarianDao.findUserByIdNum(anyString()))
			.thenAnswer((InvocationOnMock invocation) -> {
				Librarian librarian = new Librarian();
				librarian.setIdNum("librarian");
				librarian.setIsLoggedIn(true);
				System.out.print("Librarian logged in");
				return librarian;
			});
		lenient()
			.when(headLibrarianDao.findUserByIdNum(anyString()))
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
			.when(bookDao.findItemByItemNumber(anyString()))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals(correctString)) {
					Book book = new Book();
					book.setItemTitle(correctString);
					book.setDescription(correctString);
					book.setImageUrl(correctString);
					book.setItemNumber(correctString);
					book.setGenre(correctString);
					book.setPublishDate(correctDate);
					book.setIsReservable(true);
					book.setAuthor(correctString);
					book.setPublisher(correctString);
					book.setType(Item.Type.Book);
					return book;
				} else {
					return null;
				}
			});
		lenient()
			.when(bookDao.findAll())
			.thenAnswer((InvocationOnMock invocation) -> {
				Book book1 = new Book();
				book1.setItemTitle(correctString);
				book1.setDescription(correctString);
				book1.setImageUrl(correctString);
				book1.setItemNumber(correctString);
				book1.setGenre(correctString);
				book1.setPublishDate(correctDate);
				book1.setIsReservable(true);
				book1.setAuthor(correctString);
				book1.setPublisher(correctString);
				book1.setType(Item.Type.Book);
				List<Item> list = new ArrayList<Item>();
				list.add(book1);
				for (Item book : list) {
					System.out.println("BOOKS HERE" + book.getItemTitle());
				}

				// Book book2 = new Book();
				// book2.setItemTitle(correctString + "2");
				// book2.setDescription(correctString + "2");
				// book2.setImageUrl(correctString + "2");
				// book2.setItemNumber(correctString + "2");
				// book2.setGenre(correctString + "2");
				// book2.setPublishDate(correctDate);
				// book2.setIsReservable(false);
				// book2.setAuthor(correctString + "2");
				// book2.setPublisher(correctString + "2");
				// book2.setType(Item.Type.Book);
				// list.add(book2);

				return list;
			});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(bookDao.save(any(Book.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(librarianDao.save(any(Librarian.class))).thenAnswer(returnParameterAsAnswer);
		lenient()
			.when(headLibrarianDao.save(any(HeadLibrarian.class)))
			.thenAnswer(returnParameterAsAnswer);
	}

	@Test
	//Create a book
	public void testCreateBook() {
		Book book = null;
		try {
			book =
				bookService.createBook(
					"librarian",
					correctString,
					correctString,
					correctString,
					correctString,
					correctDate,
					true,
					correctString,
					correctString
				);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(book);
		assertEquals(correctString, book.getItemTitle());
		assertEquals(correctString, book.getDescription());
		assertEquals(correctString, book.getImageUrl());
		assertEquals(correctString, book.getGenre());
		assertEquals(correctDate, book.getPublishDate());
		assertEquals(true, book.getIsReservable());
		assertEquals(correctString, book.getAuthor());
		assertEquals(correctString, book.getPublisher());
		System.out.println(book.getItemNumber());
		System.out.println("Book created!");
	}

	@Test
	//Create a book with no title
	public void testCreateBookWithNoItemTitle() {
		String error = null;
		Book book = null;
		try {
			book =
				bookService.createBook(
					"librarian",
					emptyString,
					correctString,
					correctString,
					correctString,
					correctDate,
					true,
					correctString,
					correctString
				);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Item must have a title", error);
		System.out.println(error);
	}

	@Test
	//Create a book with no description
	public void testCreateBookWithNoItemDescription() {
		String error = null;
		Book book = null;
		try {
			book =
				bookService.createBook(
					"librarian",
					correctString,
					emptyString,
					correctString,
					correctString,
					correctDate,
					true,
					correctString,
					correctString
				);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(book);
		assertEquals(correctString, book.getItemTitle());
		assertEquals(emptyString, book.getDescription());
		assertEquals(correctString, book.getImageUrl());
		assertEquals(correctString, book.getGenre());
		assertEquals(correctDate, book.getPublishDate());
		assertEquals(true, book.getIsReservable());
		assertEquals(correctString, book.getAuthor());
		assertEquals(correctString, book.getPublisher());
		System.out.println(book.getItemNumber());
		System.out.println("Book created!");
	}
}
