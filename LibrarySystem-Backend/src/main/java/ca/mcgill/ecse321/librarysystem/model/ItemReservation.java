package ca.mcgill.ecse321.librarysystem.model;

import java.sql.Date;
import javax.persistence.*;

@Entity
@DiscriminatorValue("itemReservation")
public class ItemReservation {

  private int numOfRenewalsLeft;

  @Id
  private String itemReservationId;

  private String idNum;
  private String itemNumber;
  private boolean isCheckedOut;
  private Date startDate;
  private Date endDate;

  public String getItemReservationId() {
    return this.itemReservationId;
  }

  public void setItemReservationId(String itemReservationId) {
    this.itemReservationId = itemReservationId;
  }

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

  public boolean getIsCheckedOut() {
    return this.isCheckedOut;
  }

  public void setIsCheckedOut(boolean isCheckedOut) {
    this.isCheckedOut = isCheckedOut;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getStartDate() {
    return this.startDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Date getEndDate() {
    return this.endDate;
  }
}
