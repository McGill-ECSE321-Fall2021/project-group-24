package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class Librarian extends User{

    private Set<Shift> shifts; 

    @OneToMany(cascade={CascadeType.ALL})
    public Set<Shift> getShifts() {
        return this.shifts;
    }

    public void setShifts(Set<Shift> theShifts){
        this.shifts = theShifts;
    }   
}