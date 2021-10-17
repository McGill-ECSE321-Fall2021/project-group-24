package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "shift")
public class Shift extends TimeSlot {
}
