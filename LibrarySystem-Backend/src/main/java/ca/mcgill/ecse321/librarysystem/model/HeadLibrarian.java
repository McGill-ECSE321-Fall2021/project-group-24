package ca.mcgill.ecse321.librarysystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("headLibrarian")
public class HeadLibrarian extends Librarian {}
