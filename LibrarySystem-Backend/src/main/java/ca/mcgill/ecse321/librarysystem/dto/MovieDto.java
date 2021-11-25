package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.model.Item;
import java.sql.Date;

public class MovieDto extends ItemDto {

  private String productionCompany;
  private String movieCast;
  private String director;
  private String producer;

  public MovieDto(
    String itemTitle,
    String description,
    String imageURL,
    String genre,
    Date publishDate,
    boolean isReservable,
    String currentReservationId,
    String itemNumber,
    String productionCompany,
    String movieCast,
    String director,
    String producer, Date nextAvailableDate
  ) {
    this.itemTitle = itemTitle;
    this.description = description;
    this.imageUrl = imageURL;
    this.genre = genre;
    this.publishDate = publishDate;
    this.isReservable = isReservable;
    this.itemNumber = itemNumber;
    this.currentReservationId = currentReservationId;
    this.type = Item.Type.Movie;
    this.movieCast = movieCast;
    this.productionCompany = productionCompany;
    this.director = director;
    this.producer = producer;
    this.nextAvailableDate= nextAvailableDate;
  }

  public String getProducer() {
    return this.producer;
  }

  public String getProductionCompany() {
    return this.productionCompany;
  }

  public String getDirector() {
    return this.director;
  }

  public String getMovieCast() {
    return this.movieCast;
  }
}
