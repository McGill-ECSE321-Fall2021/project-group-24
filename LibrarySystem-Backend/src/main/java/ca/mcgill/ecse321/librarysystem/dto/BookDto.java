package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.model.Item;
import java.sql.Date;

public class BookDto extends ItemDto {

  private String author;
  private String publisher;

  public BookDto(
    String itemTitle,
    String description,
    String imageURL,
    String publisher,
    String author,
    String genre,
    Date publishDate,
    boolean isReservable,
    String currentReservationId,
    String itemNumber
  ) {
    this.itemTitle = itemTitle;
    this.description = description;
    this.imageUrl = imageURL;
    this.publisher = publisher;
    this.author = author;
    this.genre = genre;
    this.publishDate = publishDate;
    this.isReservable = isReservable;
    this.itemNumber = itemNumber;
    this.currentReservationId = currentReservationId;
    this.type = Item.Type.Book;
  }

  public String getAuthor() {
    return this.author;
  }

  public String getPublisher() {
    return this.publisher;
  }
}
