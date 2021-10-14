package ca.mcgill.ecse321.librarysystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class RoomBooking extends TimeSlot {

  private String bookingID;
  private Room room;
  private Patron patron;

  @ManyToOne(optional = false)
  public Patron getPatron() {
    return this.patron;
  }

  public void setPatron(Patron thePatron) {
    this.patron = thePatron;
  }

  @ManyToOne(optional = false)
  public Room getRoom() {
    return this.room;
  }

  public void setRoom(Room theRoom) {
    this.room = theRoom;
  }

  @Id
  public String getBookingID() {
    return this.bookingID;
  }

  public void setBookingID(String bookingID) {
    this.bookingID = bookingID;
  }
}
