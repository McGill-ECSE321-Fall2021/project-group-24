package ca.mcgill.ecse321.librarysystem.model;

import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "timeSlot")
public abstract class TimeSlot {

  @Id
  private String timeSlotId;
  private Date startDate;
  private Time startTime;
  private Date endDate;
  private Time endTime;
  
  public String getTimeSlotId() {
		return timeSlotId;
	}
	
	public void setTimeSlotId(String idNum) {
		this.timeSlotId = idNum;
	}

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
