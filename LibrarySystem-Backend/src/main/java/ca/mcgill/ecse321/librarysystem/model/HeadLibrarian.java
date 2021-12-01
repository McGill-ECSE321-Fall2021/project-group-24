package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("headLibrarian")
public class HeadLibrarian extends Librarian {}
