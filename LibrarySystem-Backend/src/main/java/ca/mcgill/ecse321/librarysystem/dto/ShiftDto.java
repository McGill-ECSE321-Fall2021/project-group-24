package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Date;
import java.sql.Time;

public class ShiftDto {
	Date startDate;
	Time startTime; 
	Date endDate; 
	Time endTime; 
	
	public ShiftDto() {
	}
	
	public ShiftDto(Date startDate, Time startTime, Time endTime) {
		this(startDate, startTime, startDate, endTime); 
	}
	public ShiftDto(Date startDate, Time startTime, Date endDate, Time endTime) {
		this.startDate= startDate;
		this.startTime= startTime; 
		this.endDate = endDate;
		this.endTime= endTime;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public Time getStartTime() {
		return this.startTime;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public Time getEndTime() {
		return this.endTime; 
	}
}
