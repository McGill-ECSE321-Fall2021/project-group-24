package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("not null")
public class Shift extends TimeSlot {
}
