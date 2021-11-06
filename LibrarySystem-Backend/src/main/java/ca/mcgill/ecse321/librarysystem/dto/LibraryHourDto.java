package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse321.librarysystem.model.*;

public class LibraryHourDto {
	private TimeSlot.DayOfWeek dayOfWeek; 
	private Time startTime; 
	private Time endTime; 
	
	// default opening hours are set to 9am-5pm
	public LibraryHourDto(TimeSlot.DayOfWeek dayOfWeek) {
		this(dayOfWeek, Time.valueOf("09:00:00"), Time.valueOf("17:00:00")); 
	}
	
	public LibraryHourDto(TimeSlot.DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime; 
		this.endTime = endTime; 
	}
	
	public TimeSlot.DayOfWeek getDayOfWeek() {
		return this.dayOfWeek; 
	}
	
	public Time getStartTime() {
		return this.startTime;
	}
	
	public Time getEndTime() {
		return this.endTime; 
	}

}
