package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class ItemReservation{

   private String reservationID;
   private int numOfRenewalsLeft; 

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

}