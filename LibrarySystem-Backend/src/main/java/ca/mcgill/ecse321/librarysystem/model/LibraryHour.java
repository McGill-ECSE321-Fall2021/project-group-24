package ca.mcgill.ecse321.librarysystem.model;
import javax.persistence.*;

/* @Arman (Del 2)
 * Del 2 edits: added DayOfWeek 
 */

@Entity
@DiscriminatorColumn(name = "libraryHour") 
public class LibraryHour extends TimeSlot {
	 public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }
	 private DayOfWeek dayOfWeek; 
	 
	 public DayOfWeek getDayOfWeek() {
		 return this.dayOfWeek; 
	 }
	 public void setDayOfWeek(DayOfWeek dayOfWeek) {
		 this.dayOfWeek = dayOfWeek; 
	 }
}
