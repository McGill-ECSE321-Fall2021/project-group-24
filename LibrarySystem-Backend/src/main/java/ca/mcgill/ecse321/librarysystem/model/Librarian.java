package ca.mcgill.ecse321.librarysystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("librarian")
public class Librarian extends User {

	@ElementCollection
  private Set<Shift> shifts;

  @OneToMany(cascade = { CascadeType.ALL })
  public Set<Shift> getShifts() {
    return this.shifts;
  }

  public void setShifts(Set<Shift> theShifts) {
    this.shifts = theShifts;
  }
}
