package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name = "libraryHour")
public class LibraryHour extends TimeSlot {}
