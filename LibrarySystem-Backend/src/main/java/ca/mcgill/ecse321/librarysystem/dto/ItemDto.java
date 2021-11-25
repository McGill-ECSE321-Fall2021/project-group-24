package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.model.Item;
import java.sql.Date;

public abstract class ItemDto {

  protected String itemTitle;
  protected String description;
  protected String imageUrl;
  protected String itemNumber;
  protected String genre;
  protected Date publishDate;
  protected boolean isReservable;
  protected String currentReservationId;
  protected Item.Type type;
  protected Date nextAvailableDate;

  public String getItemNumber() {
    return this.itemNumber;
  }
  
  public Date getNextAvailableDate() {
	  return this.nextAvailableDate;
  }

  public String getItemTitle() {
    return this.itemTitle;
  }

  public String getDescription() {
    return this.description;
  }

  public String getImageUrl() {
    return this.imageUrl;
  }

  public String getGenre() {
    return this.genre;
  }

  public Date getPublishDate() {
    return this.publishDate;
  }

  public boolean getIsReservable() {
    return this.isReservable;
  }

  public String getCurrentReservationId() {
    return this.currentReservationId;
  }

  public Item.Type getType() {
    return this.type;
  }
}
