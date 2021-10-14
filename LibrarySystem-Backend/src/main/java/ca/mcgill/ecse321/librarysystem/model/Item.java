package ca.mcgill.ecse321.librarysystem.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public abstract class Item {

  private String itemTitle;
  private String description;
  private String imageUrl;
  private String itemNumber;
  private String genre;
  private Date publishDate;
  private boolean isReservable;
  private boolean isCheckedOut;

  private Set<ItemReservation> itemReservations;

  @OneToMany(cascade = { CascadeType.ALL })
  public Set<ItemReservation> getItemReservation() {
    return this.itemReservations;
  }

  public void setItemReservation(Set<ItemReservation> theItemReservations) {
    this.itemReservations = theItemReservations;
  }

  public String getItemTitle() {
    return this.itemTitle;
  }

  public void setItemTitle(String itemTitle) {
    this.itemTitle = itemTitle;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return this.imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Id
  public String getItemNumber() {
    return this.itemNumber;
  }

  public void setItemNumber(String itemNumber) {
    this.itemNumber = itemNumber;
  }

  public String getGenre() {
    return this.genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public Date getPublishDate() {
    return this.publishDate;
  }

  public void setPublishDate(Date publishDate) {
    this.publishDate = publishDate;
  }

  public boolean isIsReservable() {
    return this.isReservable;
  }

  public boolean getIsReservable() {
    return this.isReservable;
  }

  public void setIsReservable(boolean isReservable) {
    this.isReservable = isReservable;
  }

  public boolean isIsCheckedOut() {
    return this.isCheckedOut;
  }

  public boolean getIsCheckedOut() {
    return this.isCheckedOut;
  }

  public void setIsCheckedOut(boolean isCheckedOut) {
    this.isCheckedOut = isCheckedOut;
  }
}
