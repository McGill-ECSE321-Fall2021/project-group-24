package ca.mcgill.ecse321.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
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
public class TestMusicAlbumService {

	@Mock
	private ItemRepository musicAlbumDao;

	@Mock
	private LibrarianRepository librarianDao;

	@Mock
	private PatronRepository patronDao;

	@Mock
	private HeadLibrarianRepository headLibrarianDao;

	@InjectMocks
	private ItemService musicAlbumService;

	private static final String correctString = "test";
	private static final Date correctDate = Date.valueOf("2021-11-10");
	private static final String emptyString = "";

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
			.when(musicAlbumDao.findItemByItemNumber(anyString()))
			.thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals(correctString)) {
					MusicAlbum musicAlbum = new MusicAlbum();
					musicAlbum.setItemTitle(correctString);
					musicAlbum.setDescription(correctString);
					musicAlbum.setImageUrl(correctString);
					musicAlbum.setItemNumber(correctString);
					musicAlbum.setGenre(correctString);
					musicAlbum.setPublishDate(correctDate);
					musicAlbum.setIsReservable(true);
					musicAlbum.setArtist(correctString);
					musicAlbum.setRecordingLabel(correctString);
					musicAlbum.setType(Item.Type.MusicAlbum);
					return musicAlbum;
				} else {
					return null;
				}
			});
		lenient()
			.when(musicAlbumDao.findAll())
			.thenAnswer((InvocationOnMock invocation) -> {
				MusicAlbum musicAlbum1 = new MusicAlbum();
				musicAlbum1.setItemTitle(correctString);
				musicAlbum1.setDescription(correctString);
				musicAlbum1.setImageUrl(correctString);
				musicAlbum1.setItemNumber(correctString);
				musicAlbum1.setGenre(correctString);
				musicAlbum1.setPublishDate(correctDate);
				musicAlbum1.setIsReservable(true);
				musicAlbum1.setArtist(correctString);
				musicAlbum1.setRecordingLabel(correctString);
				musicAlbum1.setType(Item.Type.MusicAlbum);

				MusicAlbum musicAlbum2 = new MusicAlbum();
				musicAlbum2.setItemTitle(correctString + "2");
				musicAlbum2.setDescription(correctString + "2");
				musicAlbum2.setImageUrl(correctString + "2");
				musicAlbum2.setItemNumber(correctString + "2");
				musicAlbum2.setGenre(correctString + "2");
				musicAlbum2.setPublishDate(correctDate);
				musicAlbum2.setIsReservable(false);
				musicAlbum2.setArtist(correctString);
				musicAlbum2.setRecordingLabel(correctString);
				musicAlbum2.setType(Item.Type.MusicAlbum);
				List<Item> list = new ArrayList<Item>();
				list.add(musicAlbum1);
				list.add(musicAlbum2);

				return list;
			});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient()
			.when(musicAlbumDao.save(any(MusicAlbum.class)))
			.thenAnswer(returnParameterAsAnswer);
		lenient().when(librarianDao.save(any(Librarian.class))).thenAnswer(returnParameterAsAnswer);
		lenient()
			.when(headLibrarianDao.save(any(HeadLibrarian.class)))
			.thenAnswer(returnParameterAsAnswer);
	}

	@Test
	//Create a music album
	public void testCreateMusicAlbum() {
		MusicAlbum musicAlbum = null;
		try {
			musicAlbum =
				musicAlbumService.createMusicAlbum(
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
		assertNotNull(musicAlbum);
		assertEquals(correctString, musicAlbum.getItemTitle());
		assertEquals(correctString, musicAlbum.getDescription());
		assertEquals(correctString, musicAlbum.getImageUrl());
		assertEquals(correctString, musicAlbum.getGenre());
		assertEquals(correctDate, musicAlbum.getPublishDate());
		assertEquals(true, musicAlbum.getIsReservable());
		System.out.println(musicAlbum.getItemNumber());
		System.out.println("Music album created!");
	}

	@Test
	//Create a music album as a patron
	public void testCreateMusicAlbumAsPatron() {
		String error = null;
		MusicAlbum musicAlbum = null;
		try {
			musicAlbum =
				musicAlbumService.createMusicAlbum(
					"patron",
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
			error = e.getMessage();
		}
		assertEquals("You do not have permission to create an item reservation", error);
		System.out.println(error);
	}

	@Test
	//Create a music album with no title
	public void testCreateMusicAlbumWithNoItemTitle() {
		String error = null;
		MusicAlbum musicAlbum = null;
		try {
			musicAlbum =
				musicAlbumService.createMusicAlbum(
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
	//Create a music album with no description
	public void testCreateMusicAlbumWithNoItemDescription() {
		String error = null;
		MusicAlbum musicAlbum = null;
		try {
			musicAlbum =
				musicAlbumService.createMusicAlbum(
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
		assertNotNull(musicAlbum);
		assertEquals(correctString, musicAlbum.getItemTitle());
		assertEquals(emptyString, musicAlbum.getDescription());
		assertEquals(correctString, musicAlbum.getImageUrl());
		assertEquals(correctString, musicAlbum.getGenre());
		assertEquals(correctDate, musicAlbum.getPublishDate());
		assertEquals(true, musicAlbum.getIsReservable());
		assertEquals(correctString, musicAlbum.getArtist());
		assertEquals(correctString, musicAlbum.getRecordingLabel());
		System.out.println(musicAlbum.getItemNumber());
		System.out.println("Music album created!");
	}
}
