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
public class TestMovieService {

  @Mock
  private ItemRepository movieDao;

  @Mock
  private LibrarianRepository librarianDao;

  @Mock
  private PatronRepository patronDao;

  @Mock
  private HeadLibrarianRepository headLibrarianDao;

  @InjectMocks
  private ItemService movieService;

  
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
      .when(movieDao.findItemByItemNumber(anyString()))
      .thenAnswer((InvocationOnMock invocation) -> {
        if (invocation.getArgument(0).equals(correctString)) {
          Movie movie = new Movie();
          movie.setItemTitle(correctString);
          movie.setDescription(correctString);
          movie.setImageUrl(correctString);
          movie.setItemNumber(correctString);
          movie.setGenre(correctString);
          movie.setPublishDate(correctDate);
          movie.setIsReservable(true);
          movie.setProductionCompany(correctString);
          movie.setMovieCast(correctString);
          movie.setDirector(correctString);
          movie.setProducer(correctString);

          movie.setType(Item.Type.Movie);
          return movie;
        } else {
          return null;
        }
      });
    lenient()
      .when(movieDao.findAll())
      .thenAnswer((InvocationOnMock invocation) -> {
        Movie movie1 = new Movie();
        movie1.setItemTitle(correctString);
        movie1.setDescription(correctString);
        movie1.setImageUrl(correctString);
        movie1.setItemNumber(correctString);
        movie1.setGenre(correctString);
        movie1.setPublishDate(correctDate);
        movie1.setIsReservable(true);
        movie1.setProductionCompany(correctString);
        movie1.setMovieCast(correctString);
        movie1.setDirector(correctString);
        movie1.setProducer(correctString);
        movie1.setType(Item.Type.Archive);

        Movie movie2 = new Movie();
        movie2.setItemTitle(correctString + "2");
        movie2.setDescription(correctString + "2");
        movie2.setImageUrl(correctString + "2");
        movie2.setItemNumber(correctString + "2");
        movie2.setGenre(correctString + "2");
        movie2.setPublishDate(correctDate);
        movie2.setProductionCompany(correctString);
        movie2.setMovieCast(correctString);
        movie2.setDirector(correctString);
        movie2.setProducer(correctString);
        movie2.setIsReservable(false);
        movie2.setType(Item.Type.Archive);
        List<Item> list = new ArrayList<Item>();
        list.add(movie1);
        list.add(movie2);

        return list;
      });
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };
    lenient()
      .when(movieDao.save(any(Archive.class)))
      .thenAnswer(returnParameterAsAnswer);
    lenient()
      .when(librarianDao.save(any(Librarian.class)))
      .thenAnswer(returnParameterAsAnswer);
    lenient()
      .when(headLibrarianDao.save(any(HeadLibrarian.class)))
      .thenAnswer(returnParameterAsAnswer);
  }

  @Test
  //Create a movie
  public void testCreateMovie() {
    Movie movie = null;
    try {
      movie =
        movieService.createMovie(
          "librarian",
          correctString,
          correctString,
          correctString,
          correctString,
          correctDate,
          true,
          correctString,
          correctString,
          correctString,
          correctString
        );
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(movie);
    assertEquals(correctString, movie.getItemTitle());
    assertEquals(correctString, movie.getDescription());
    assertEquals(correctString, movie.getImageUrl());
    assertEquals(correctString, movie.getGenre());
    assertEquals(correctDate, movie.getPublishDate());
    assertEquals(true, movie.getIsReservable());
    assertEquals(correctString, movie.getProductionCompany());
    assertEquals(correctString, movie.getMovieCast());
    assertEquals(correctString, movie.getDirector());
    assertEquals(correctString, movie.getProducer());
    System.out.println(movie.getItemNumber());
    System.out.println("Movie created!");
  }

  @Test
  //Create a movie as a patron
  public void testCreateMovieAsPatron() {
    String error = null;
    Movie movie = null;
    try {
      movie =
      movieService.createMovie(
          "patron",
          correctString,
          correctString,
          correctString,
          correctString,
          correctDate,
          true,
          correctString,
          correctString,
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
  //Create a movie with no title
  public void testCreateMovieWithNoItemTitle() {
    String error = null;
    Movie movie = null;
    try {
      movie =
        movieService.createMovie(
          "librarian",
          emptyString,
          correctString,
          correctString,
          correctString,
          correctDate,
          true,
          correctString,
          correctString,
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
  //Create a movie with no description
  public void testCreateMovieWithNoItemDescription() {
    String error = null;
    Movie movie = null;
    try {
      movie =
        movieService.createMovie(
          "librarian",
          correctString,
          emptyString,
          correctString,
          correctString,
          correctDate,
          true,
          correctString,
          correctString,
          correctString,
          correctString
        );
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNotNull(movie);
    assertEquals(correctString, movie.getItemTitle());
    assertEquals(emptyString, movie.getDescription());
    assertEquals(correctString, movie.getImageUrl());
    assertEquals(correctString, movie.getGenre());
    assertEquals(correctDate, movie.getPublishDate());
    assertEquals(true, movie.getIsReservable());
    assertEquals(correctString, movie.getProductionCompany());
    assertEquals(correctString, movie.getMovieCast());
    assertEquals(correctString, movie.getDirector());
    assertEquals(correctString, movie.getProducer());
    System.out.println(movie.getItemNumber());
    System.out.println("Movie created!");
  }

  @Test
  //Get all movies
  public void testGetAllMovies() {
    ArrayList<Item> movies = null;

    try {
      movies = new ArrayList<Item>(movieService.getAllMovies());;
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(movies);
    assertEquals(2, movies.size());
    assertEquals(correctString, movies.get(0).getItemTitle());
    assertEquals(correctString, movies.get(0).getDescription());
    assertEquals(correctString, movies.get(0).getImageUrl());
    assertEquals(correctString, movies.get(0).getGenre());
    assertEquals(correctDate, movies.get(0).getPublishDate());
    assertEquals(true, movies.get(0).getIsReservable());
    assertEquals(correctString, ((Movie) movies.get(0)).getProductionCompany());
    assertEquals(correctString, ((Movie) movies.get(0)).getMovieCast());
    assertEquals(correctString, ((Movie) movies.get(0)).getDirector());
    assertEquals(correctDate, ((Movie) movies.get(0)).getProducer());

    assertEquals(correctString, movies.get(1).getItemTitle());
    assertEquals(correctString, movies.get(1).getDescription());
    assertEquals(correctString, movies.get(1).getImageUrl());
    assertEquals(correctString, movies.get(1).getGenre());
    assertEquals(correctDate, movies.get(1).getPublishDate());
    assertEquals(true, movies.get(1).getIsReservable());
    assertEquals(correctString, ((Movie) movies.get(1)).getProductionCompany());
    assertEquals(correctString, ((Movie) movies.get(1)).getMovieCast());
    assertEquals(correctString, ((Movie) movies.get(1)).getDirector());
    assertEquals(correctDate, ((Movie) movies.get(1)).getProducer());

    for (Item movie : movies) {
      System.out.println(movie.getItemTitle());
    }
  }
}
