package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("shift")
public class Shift extends TimeSlot {
}
