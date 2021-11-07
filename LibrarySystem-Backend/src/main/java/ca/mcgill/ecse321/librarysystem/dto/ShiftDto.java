package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Time;

import ca.mcgill.ecse321.librarysystem.model.TimeSlot;

public class ShiftDto {
	String librarianId; 
	Time startTime; 
	TimeSlot.DayOfWeek dayOfWeek;
	Time endTime; 
	
	public ShiftDto(String librarianId, TimeSlot.DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		this.librarianId = librarianId; 
		this.startTime= startTime; 
		this.dayOfWeek = dayOfWeek;
		this.endTime= endTime;
	}
	
	public Time getStartTime() {
		return this.startTime;
	}
	
	public Time getEndTime() {
		return this.endTime; 
	}
	
}
