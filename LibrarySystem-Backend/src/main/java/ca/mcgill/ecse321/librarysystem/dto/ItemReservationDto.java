package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Date;

public class ItemReservationDto {
	  private String itemReservationId;
	  private Date startDate;
	  private Date endDate;
	private int numOfRenewalsLeft;
	
	private String idNum;
	private String itemNumber;
	private boolean isCheckedOut;
	
	public ItemReservationDto() {
		
	}
	
	public ItemReservationDto( String itemReservationId,
	   Date startDate,
	   Date endDate,
	 int numOfRenewalsLeft,
	 String idNum,
	 String itemNumber, boolean isCheckedOut) {
		this.itemReservationId = itemReservationId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numOfRenewalsLeft = numOfRenewalsLeft;
		this.idNum = idNum;
		this.itemNumber = itemNumber;
		this.isCheckedOut = isCheckedOut;
		
	}
	
	public String getItemReservationId() {
		return this.itemReservationId; 
	}


	public String getItemNumber() {
		return itemNumber;
	}

	public int getNumOfRenewalsLeft() {
		return numOfRenewalsLeft;
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
	
	public boolean getIsCheckedOut() {
		return isCheckedOut;
	}
}
