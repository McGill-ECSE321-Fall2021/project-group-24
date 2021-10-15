package ca.mcgill.ecse321.librarysystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ItemReservation extends TimeSlot {

  private String reservationID;
  private int numOfRenewalsLeft;
  private Patron patron;
  private String itemNumber;

  @ManyToOne(optional = false)
  public Patron getPatron() {
    return this.patron;
  }

  public void setPatron(Patron thePatron) {
    this.patron = thePatron;
  }

  @Id
  public String getReservationID() {
    return this.reservationID;
  }

  public void setReservationID(String reservationID) {
    this.reservationID = reservationID;
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
  
  public void setItemNumber(String itemnumber) {
	  this.itemNumber = itemnumber;
  }
}
