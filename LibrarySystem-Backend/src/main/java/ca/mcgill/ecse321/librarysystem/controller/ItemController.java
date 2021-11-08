package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.LibrarySystemApplication;
import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class ItemController {

  @Autowired
  private ItemService itemService;

  //GET to get all items
  @GetMapping(value = { "/items/", "/items" })
  public List<ItemDto> getAllItems() {
    System.out.println("Flag Get");
    return itemService
      .getAll()
      .stream()
      .map(lib -> convertToDto(lib))
      .collect(Collectors.toList());
  }

  @GetMapping(value = { "/items/{itemNumber}", "/items/{itemNumber}/" })
  public ItemDto getItem(@PathVariable("itemNumber") String itemNumber) {
    System.out.println("Flag Get" + itemNumber);
    Item item = itemService.getItem(itemNumber);

    return convertToDto(item);
  }

  @PostMapping(value = { "/items/createBook/{", "/items/createBook/" })
  public BookDto createBook(
    @RequestParam String itemTitle,
    @RequestParam String description,
    @RequestParam String imageURL,
    @RequestParam String publisher,
    @RequestParam String author,
    @RequestParam String genre,
    @RequestParam String publishDate,
    @RequestParam boolean isReservable,
    @RequestParam String idNum
  ) {
    System.out.println("Flag Post");
    Book book = itemService.createBook(
      idNum,
      itemTitle,
      description,
      imageURL,
      genre,
      Date.valueOf(LocalDate.parse(publishDate)),
      isReservable,
      author,
      publisher
    );
    System.out.println(book.getAuthor());
    return convertToBookDto(book);
  }

  @PostMapping(value = { "/items/createMovie", "/items/createMovie/" })
  public MovieDto createMovie(
    @RequestParam String itemTitle,
    @RequestParam String description,
    @RequestParam String imageURL,
    @RequestParam String publisher,
    @RequestParam String author,
    @RequestParam String genre,
    @RequestParam String publishDate,
    @RequestParam boolean isReservable,
    @RequestParam String movieCast,
    @RequestParam String productionCompany,
    @RequestParam String director,
    @RequestParam String producer,
    @RequestParam String idNum
  ) {
    System.out.println("Flag Post");
    Movie movie = itemService.createMovie(
      idNum,
      itemTitle,
      description,
      imageURL,
      genre,
      Date.valueOf(LocalDate.parse(publishDate)),
      isReservable,
      productionCompany,
      movieCast,
      director,
      producer
    );
    return convertToMovieDto(movie);
  }

  @PostMapping(
    value = { "/items/createNewEmptyBook/", "/items/createNewEmptyBook" }
  )
  public BookDto createEmptyBook(@RequestParam String idNum) {
    System.out.println("Flag Post");
    Book book = itemService.createBook(
      idNum,
      "title",
      "description",
      "imageURL",
      "genre",
      Date.valueOf(LocalDate.now()),
      true,
      "author",
      "publisher"
    );
    System.out.println(book.getAuthor());
    return convertToBookDto(book);
  }

  @PostMapping(
    value = { "/items/createEmptyArchive/", "/items/createEmptyArchive" }
  )
  public ArchiveDto createEmptyArchive(@RequestParam String idNum) {
    System.out.println("Flag Post");
    Archive archive = itemService.createArchive(
      idNum,
      "title",
      "description",
      "imageURL",
      "genre",
      Date.valueOf(LocalDate.now()),
      true
    );

    return convertToArchiveDto(archive);
  }

  @PostMapping(
    value = { "/items/createEmptyMovie/", "/items/createEmptyMovie" }
  )
  public MovieDto createEmptyMovie(@RequestParam String idNum) {
    System.out.println("Flag Post");
    Movie movie = itemService.createMovie(
      idNum,
      "title",
      "description",
      "imageURL",
      "genre",
      Date.valueOf(LocalDate.now()),
      true,
      "production company",
      "movieCast",
      "director",
      "producer"
    );
    return convertToMovieDto(movie);
  }

  @PostMapping(
    value = { "/items/createEmptyMusicAlbum/", "/items/createEmptyMusicAlbum" }
  )
  public MusicAlbumDto createEmptyMusicAlbum(@RequestParam String idNum) {
    System.out.println("Flag Post");
    MusicAlbum musicAlbum = itemService.createMusicAlbum(
      idNum,
      "title",
      "description",
      "imageURL",
      "genre",
      Date.valueOf(LocalDate.now()),
      true,
      "artist",
      "recording label"
    );
    return convertToMusicAlbumDto(musicAlbum);
  }

  //POST to delete item
  @PostMapping(
    value = { "/items/delete/{itemNumber}", "/items/delete/{itemNumber}/" }
  )
  public String deleteItem(@PathVariable("itemNumber") String itemNumber) {
    Item item = itemService.getItem(itemNumber);
    switch (item.getType().toString()) {
      case "Book":
        itemService.deleteBook(itemNumber);
        String deletedBook =
          "Deleted item of type " +
          item.getType().toString() +
          " with itemNumber " +
          itemNumber;
        return deletedBook;
      case "Movie":
        itemService.deleteMovie(itemNumber);
        String deletedMovie =
          "Deleted item of type " +
          item.getType().toString() +
          " with itemNumber " +
          itemNumber;
        return deletedMovie;
      case "MusicAlbum":
        itemService.deleteMusicAlbum(itemNumber);
        String deletedMusicAlbum =
          "Deleted item of type " +
          item.getType().toString() +
          " with itemNumber " +
          itemNumber;
        return deletedMusicAlbum;
      case "Archive":
        itemService.deleteArchive(itemNumber);
        String deletedArchive =
          "Deleted item of type " +
          item.getType().toString() +
          " with itemNumber " +
          itemNumber;
        return deletedArchive;
      case "PrintedMedia":
        itemService.deletePrintedMedia(itemNumber);
        String deletedPrintedMedia =
          "Deleted item of type " +
          item.getType().toString() +
          " with itemNumber " +
          itemNumber;
        return deletedPrintedMedia;
      default:
        return "Item not found";
    }
  }

  private ItemDto convertToDto(Item item) {
    if (item.getType().equals(Item.Type.Book)) {
      return convertToBookDto((Book) item);
    } else if (item.getType().equals(Item.Type.Archive)) {
      return convertToArchiveDto((Archive) item);
    } else if (item.getType().equals(Item.Type.Movie)) {
      return convertToMovieDto((Movie) item);
    } else if (item.getType().equals(Item.Type.PrintedMedia)) {
      return convertToPrintedMediaDto((PrintedMedia) item);
    } else {
      return convertToMusicAlbumDto((MusicAlbum) item);
    }
  }

  private BookDto convertToBookDto(Book book) {
    BookDto bookDto = new BookDto(
      book.getItemTitle(),
      book.getDescription(),
      book.getImageUrl(),
      book.getPublisher(),
      book.getAuthor(),
      book.getGenre(),
      book.getPublishDate(),
      book.getIsReservable(),
      book.getCurrentReservationId(),
      book.getItemNumber()
    );
    return bookDto;
  }

  private ArchiveDto convertToArchiveDto(Archive archive) {
    ArchiveDto archiveDto = new ArchiveDto(
      archive.getItemTitle(),
      archive.getDescription(),
      archive.getImageUrl(),
      archive.getGenre(),
      archive.getPublishDate(),
      archive.getIsReservable(),
      archive.getCurrentReservationId(),
      archive.getItemNumber()
    );
    return archiveDto;
  }

  private MovieDto convertToMovieDto(Movie movie) {
    MovieDto movieDto = new MovieDto(
      movie.getItemTitle(),
      movie.getDescription(),
      movie.getImageUrl(),
      movie.getGenre(),
      movie.getPublishDate(),
      movie.getIsReservable(),
      movie.getCurrentReservationId(),
      movie.getItemNumber(),
      movie.getProductionCompany(),
      movie.getMovieCast(),
      movie.getDirector(),
      movie.getProducer()
    );
    return movieDto;
  }

  private PrintedMediaDto convertToPrintedMediaDto(PrintedMedia printedMedia) {
    PrintedMediaDto printedMediaDto = new PrintedMediaDto(
      printedMedia.getItemTitle(),
      printedMedia.getDescription(),
      printedMedia.getImageUrl(),
      printedMedia.getGenre(),
      printedMedia.getPublishDate(),
      printedMedia.getIsReservable(),
      printedMedia.getCurrentReservationId(),
      printedMedia.getItemNumber(),
      printedMedia.getIssueNumber()
    );
    return printedMediaDto;
  }

  private MusicAlbumDto convertToMusicAlbumDto(MusicAlbum musicAlbum) {
    MusicAlbumDto musicAlbumDto = new MusicAlbumDto(
      musicAlbum.getItemTitle(),
      musicAlbum.getDescription(),
      musicAlbum.getImageUrl(),
      musicAlbum.getGenre(),
      musicAlbum.getPublishDate(),
      musicAlbum.getIsReservable(),
      musicAlbum.getCurrentReservationId(),
      musicAlbum.getItemNumber(),
      musicAlbum.getArtist(),
      musicAlbum.getRecordingLabel()
    );
    return musicAlbumDto;
  }
}
