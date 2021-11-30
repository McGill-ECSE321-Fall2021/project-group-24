package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

/*
 *@Arman
 *Del 2: Added librarian ID as an attribute
 */

@Entity
@DiscriminatorValue("shift")
public class Shift extends TimeSlot {

  private String librarianId;

  public String getLibrarianId() {
    return this.librarianId;
  }

  public void setLibrarianId(String librarianId) {
    this.librarianId = librarianId;
  }
}
