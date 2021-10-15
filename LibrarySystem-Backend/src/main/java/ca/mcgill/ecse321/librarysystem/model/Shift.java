package ca.mcgill.ecse321.librarysystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Shift extends TimeSlot {
	private String shiftID;
	
	public String getShiftID() {
		return shiftID;
	}
	
	public void setShiftID(String shiftID) {
		this.shiftID = shiftID;
	}
}
