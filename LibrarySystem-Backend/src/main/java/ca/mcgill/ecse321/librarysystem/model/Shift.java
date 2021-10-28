package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

/*
 *@Arman 
 *Del 2: Added librarian as an attribute
 */

@Entity
@DiscriminatorValue("shift")
public class Shift extends TimeSlot {

}
