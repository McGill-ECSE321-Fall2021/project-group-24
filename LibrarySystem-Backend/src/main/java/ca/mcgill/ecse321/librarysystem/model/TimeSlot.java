package ca.mcgill.ecse321.librarysystem.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public class TimeSlot {

  private Date startDate;
  private Time startTime;
  private Date endDate;
  private Time endTime;

  public Date getStartDate() {
    return this.startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Time getStartTime() {
    return this.startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Date getEndDate() {
    return this.endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Time getEndTime() {
    return this.endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }
}
