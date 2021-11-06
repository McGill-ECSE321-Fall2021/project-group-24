package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Time;
import java.sql.Date;

public class RoomBookingDto {
	private String roomNum;
	private String idNum;
	private String timeSlotId;
	private Date startDate;
	private Time startTime;
	private Date endDate;
	private Time endTime;
	
	public RoomBookingDto() {
		
	}
	
	public RoomBookingDto( 
		String timeSlotId,
		Date startDate,
		Time startTime,
		Date endDate,
		Time endTime,
		String idNum,
		String roomNum) {
			this.timeSlotId = timeSlotId;
			this.startDate = startDate;
			this.startTime = startTime;
			this.endDate = endDate;
			this.endTime = endTime;
			this.idNum = idNum;
			this.roomNum = roomNum;		
	}
	
	public String getTimeSlotId() {
		return this.timeSlotId; 
	}
	
	public String getRoomNum() {
		return this.roomNum; 
	}	
	
	public String getidNum() {
		return this.idNum; 
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public Time getEndTime() {
		return endTime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
}
	
