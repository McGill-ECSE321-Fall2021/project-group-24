package ca.mcgill.ecse321.librarysystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class LibraryHour extends TimeSlot {
	private String hourID;
	
	public String getHourID() {
		return hourID;
	}
	
	public void setHourID(String HourID) {
		this.hourID = HourID;
	}
}
