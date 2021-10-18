package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("archive")
public class Archive extends Item {}
