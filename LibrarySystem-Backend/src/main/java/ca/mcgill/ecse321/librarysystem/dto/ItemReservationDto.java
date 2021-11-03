package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Date;
import java.sql.Time;

public class ItemReservationDto {
	  private String timeSlotId;
	  private Date startDate;
	  private Time startTime;
	  private Date endDate;
	  private Time endTime;
	private int numOfRenewalsLeft;
	
	private String idNum;
	private String itemNumber;
	
	public ItemReservationDto() {
		
	}
	
	public ItemReservationDto( String timeSlotId,
	   Date startDate,
	   Time startTime,
	   Date endDate,
	   Time endTime,
	 int numOfRenewalsLeft,
	 String idNum,
	 String itemNumber ) {
		this.timeSlotId = timeSlotId;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.numOfRenewalsLeft = numOfRenewalsLeft;
		this.idNum = idNum;
		this.itemNumber = itemNumber;
		
	}
	
	public String getTimeSlotId() {
		return this.timeSlotId; 
	}
	
	public void setTimeSlotId(String timeSlotId) {
		this.timeSlotId = timeSlotId; 
	}

	public Time getStartTime() {
		return startTime;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public int getNumOfRenewalsLeft() {
		return numOfRenewalsLeft;
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

	public String getIdNum() {
		return idNum;
	}
}
