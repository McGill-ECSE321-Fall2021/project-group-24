package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class LibraryCalendar{

    private Set<TimeSlot> timeSlots;

    @OneToMany(cascade={CascadeType.ALL})
    public Set<TimeSlot> getTimeSlot() {
        return this.timeSlots;
    }

    public void setTimeSlot(Set<TimeSlot> theTimeSlots){
        this.timeSlots = theTimeSlots;
    }
}