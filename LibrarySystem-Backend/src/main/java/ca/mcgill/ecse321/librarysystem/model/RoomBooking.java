package ca.mcgill.ecse321.librarysystem.model;

import java.sql.Date;

import javax.persistence.*;

@Entity
@DiscriminatorValue("roomBooking")
public class RoomBooking extends TimeSlot {
  private String roomNum;
  private Date date;
  
  @ManyToOne(optional = false)
  public String getRoomNum() {
    return this.roomNum;
  }

  public void setRoomNum(String roomNum) {
    this.roomNum = roomNum;
  }
  
  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
