package ca.mcgill.ecse321.librarysystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Librarian extends User {

  private Set<Shift> shifts;

  @OneToMany(cascade = { CascadeType.ALL })
  public Set<Shift> getShifts() {
    return this.shifts;
  }

  public void setShifts(Set<Shift> theShifts) {
    this.shifts = theShifts;
  }
}
