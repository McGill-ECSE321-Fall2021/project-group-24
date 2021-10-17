package ca.mcgill.ecse321.librarysystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class LibraryCalendar {

  @ElementCollection
  private Set<TimeSlot> timeSlots;
  private String calendarNum;
  
  @Id 
  public String getCalendarNum() {
	  return this.calendarNum;
  }
  
  public void setCalendarNum(String calendarNum) {
	  this.calendarNum = calendarNum;
  }
  
//  @OneToMany(cascade = { CascadeType.ALL })
//  public Set<TimeSlot> getTimeSlot() {
//    return this.timeSlots;
//  }

  public void setTimeSlot(Set<TimeSlot> theTimeSlots) {
    this.timeSlots = theTimeSlots;
  }
}
