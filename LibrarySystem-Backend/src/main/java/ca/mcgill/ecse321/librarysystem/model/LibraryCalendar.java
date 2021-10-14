package ca.mcgill.ecse321.librarysystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class LibraryCalendar {

  private Set<TimeSlot> timeSlots;

  @OneToMany(cascade = { CascadeType.ALL })
  public Set<TimeSlot> getTimeSlot() {
    return this.timeSlots;
  }

  public void setTimeSlot(Set<TimeSlot> theTimeSlots) {
    this.timeSlots = theTimeSlots;
  }
}
