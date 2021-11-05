package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse321.librarysystem.model.LibraryHour.DayOfWeek;

public class LibraryHourDto {
	private DayOfWeek dayOfWeek; 
	private Time startTime; 
	private Time endTime; 
	private List<LibraryHourDto> libraryHours; 
	public LibraryHourDto() {
	}
	
	// default opening hours are set to 9am-5pm
	public LibraryHourDto(DayOfWeek dayOfWeek) {
		this(dayOfWeek, Time.valueOf("09:00:00"), Time.valueOf("17:00:00")); 
	}
	
	public LibraryHourDto(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime; 
		this.endTime = endTime; 
	}
	
	public DayOfWeek getDayOfWeek() {
		return this.dayOfWeek; 
	}
	
	public Time getStartTime() {
		return this.startTime;
	}
	
	public Time getEndTime() {
		return this.endTime; 
	}

	public List<LibraryHourDto> getAllLibraryHours() {
		return this.libraryHours; 
	}
	
}
