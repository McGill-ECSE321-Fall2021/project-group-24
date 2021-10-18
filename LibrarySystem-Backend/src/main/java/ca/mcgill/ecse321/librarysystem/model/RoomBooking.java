package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("roomBooking")
public class RoomBooking extends TimeSlot {
  private String roomNum;

  @ManyToOne(optional = false)
  public String getRoomNum() {
    return this.roomNum;
  }

  public void setRoomNum(String roomNum) {
    this.roomNum = roomNum;
  }
}
