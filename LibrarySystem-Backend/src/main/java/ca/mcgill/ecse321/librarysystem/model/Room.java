package ca.mcgill.ecse321.librarysystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Room {

  @Id
  private String roomNum;

  private int capacity;

  @ElementCollection
  private Set<RoomBooking> roomBookings;

  @OneToMany(cascade = { CascadeType.ALL })
  public Set<RoomBooking> getRoomBookings() {
    return this.roomBookings;
  }

  public void setRoomBookings(Set<RoomBooking> theRoomBookings) {
    this.roomBookings = theRoomBookings;
  }

  public String getRoomNum() {
    return this.roomNum;
  }

  public void setRoomNum(String roomNum) {
    this.roomNum = roomNum;
  }

  public int getCapacity() {
    return this.capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }
}
