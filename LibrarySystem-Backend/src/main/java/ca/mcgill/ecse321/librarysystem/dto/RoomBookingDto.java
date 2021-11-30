package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.model.TimeSlot;
import java.sql.Date;
import java.sql.Time;

public class RoomBookingDto {

  private String roomNum;
  private String idNum;
  private String timeSlotId;
  private Date date;
  private Time startTime;
  private TimeSlot.DayOfWeek dayOfWeek;
  private Time endTime;

  public RoomBookingDto() {}

  public RoomBookingDto(
    String timeSlotId,
    Date date,
    Time startTime,
    Time endTime,
    String idNum,
    String roomNum
  ) {
    this.timeSlotId = timeSlotId;
    this.date = date;
    this.startTime = startTime;
    this.dayOfWeek =
      TimeSlot.DayOfWeek.valueOf(date.toLocalDate().getDayOfWeek().toString());
    this.endTime = endTime;
    this.idNum = idNum;
    this.roomNum = roomNum;
  }

  public String getTimeSlotId() {
    return this.timeSlotId;
  }

  public String getRoomNum() {
    return this.roomNum;
  }

  public String getidNum() {
    return this.idNum;
  }

  public Time getStartTime() {
    return startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public Date getDate() {
    return date;
  }

  public TimeSlot.DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }
}
