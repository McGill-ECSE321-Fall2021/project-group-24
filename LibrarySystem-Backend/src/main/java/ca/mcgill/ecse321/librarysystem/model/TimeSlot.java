package ca.mcgill.ecse321.librarysystem.model;

import java.sql.Time;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;



/* @Arman and Saagar (Del 2)
 * Del 2 edits: added DayOfWeek 
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "timeSlot")
public abstract class TimeSlot {

  @Id
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String timeSlotId;
  private Time startTime;
  private Time endTime;
  
	 public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }
	 private DayOfWeek dayOfWeek; 
	 
	 public DayOfWeek getDayOfWeek() {
		 return this.dayOfWeek; 
	 }
	 public void setDayOfWeek(DayOfWeek dayOfWeek) {
		 this.dayOfWeek = dayOfWeek; 
	 }
  
  public String getTimeSlotId() {
		return timeSlotId;
	}
	
	public void setTimeSlotId(String idNum) {
		this.timeSlotId = idNum;
	}
	public DayOfWeek stringToDayOfWeek(String dayOfWeek) {
		if (dayOfWeek.equals("Monday")) {
			 return DayOfWeek.Monday;
		} else if (dayOfWeek.equals("Tuesday")) {
			return DayOfWeek.Tuesday;
		} else if (dayOfWeek.equals("Wednesday")) {
			return DayOfWeek.Wednesday;
		} else if (dayOfWeek.equals("Thursday")) {
			return DayOfWeek.Thursday;
		} else if (dayOfWeek.equals("Friday")) {
			return DayOfWeek.Friday;
		} else if (dayOfWeek.equals("Saturday")) {
			return DayOfWeek.Saturday;
		} else if (dayOfWeek.equals("Sunday")){
			return DayOfWeek.Sunday;
		} else {
			return null;
		}
		
	}

  public Time getStartTime() {
    return this.startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Time getEndTime() {
    return this.endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }
}
