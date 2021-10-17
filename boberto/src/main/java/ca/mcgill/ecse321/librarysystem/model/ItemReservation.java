package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "itemReservation")
public class ItemReservation extends TimeSlot {
	private int numOfRenewalsLeft;
	
	private String idNum;
	private String itemNumber;

	@ManyToOne(optional = false)
	public String getIdNum() {
		return this.idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public int getNumOfRenewalsLeft() {
		return this.numOfRenewalsLeft;
	}

	public void setNumOfRenewalsLeft(int numOfRenewalsLeft) {
		this.numOfRenewalsLeft = numOfRenewalsLeft;
	}

	public String getItemNumber() {
		return this.itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
}
