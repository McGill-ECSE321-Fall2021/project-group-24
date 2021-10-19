package ca.mcgill.ecse321.librarysystem.model;

import java.sql.Date;

import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "item") 
public abstract class Item {

  private String itemTitle;
  private String description;
  private String imageUrl;
  @Id 
  private String itemNumber;
  private String genre;
  private Date publishDate;
  private boolean isReservable;
  private boolean isCheckedOut;

  @ElementCollection
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

  public boolean getIsReservable() {
    return this.isReservable;
  }

  public void setIsReservable(boolean isReservable) {
    this.isReservable = isReservable;
  }

  public boolean getIsCheckedOut() {
    return this.isCheckedOut;
  }

  public void setIsCheckedOut(boolean isCheckedOut) {
    this.isCheckedOut = isCheckedOut;
  }
}
