package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("roomBooking")
public class RoomBooking extends TimeSlot {
  private String roomNum;
  private String idNum;

  @ManyToOne(optional = false)
  public String getRoomNum() {
    return this.roomNum;
  }

  public void setRoomNum(String roomNum) {
    this.roomNum = roomNum;
  }
  
  @ManyToOne(optional = false)
  public String getIdNum() {
    return this.idNum;
  }

  public void setIdNum(String idNum) {
    this.idNum = idNum;
  }
  
}
