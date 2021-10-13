package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class Patron{

    private boolean isVerified;
    private boolean isResident;
    private boolean isRegisteredOnline;

    public boolean isIsVerified() {
        return this.isVerified;
    }

    public boolean getIsVerified() {
        return this.isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean isIsResident() {
        return this.isResident;
    }

    public boolean getIsResident() {
        return this.isResident;
    }

    public void setIsResident(boolean isResident) {
        this.isResident = isResident;
    }

    public boolean isIsRegisteredOnline() {
        return this.isRegisteredOnline;
    }

    public boolean getIsRegisteredOnline() {
        return this.isRegisteredOnline;
    }

    public void setIsRegisteredOnline(boolean isRegisteredOnline) {
        this.isRegisteredOnline = isRegisteredOnline;
    }

   

}