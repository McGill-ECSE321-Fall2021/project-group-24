package ca.mcgill.ecse321.librarysystem.model;

import java.sql.Date;

import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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
  private String currentReservationId;
  private Type type;
  
  public enum Type {Archive, Book, Movie, MusicAlbum, PrintedMedia }

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

  public String getCurrentReservationId() {
    return this.currentReservationId;
  }

  public void setCurrentReservationId(String currentReservationId) {
    this.currentReservationId = currentReservationId;
  }
  public Type getType() {
	  return this.type;
  }
  public void setType(Type type) {
	  this.type = type;
  }
}
