package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("patron")
public class Patron extends User {

  private boolean isVerified;
  private boolean isResident;
  private boolean isRegisteredOnline;

  public boolean getIsVerified() {
    return this.isVerified;
  }

  public void setIsVerified(boolean isVerified) {
    this.isVerified = isVerified;
  }

  public boolean getIsResident() {
    return this.isResident;
  }

  public void setIsResident(boolean isResident) {
    this.isResident = isResident;
  }

  public boolean getIsRegisteredOnline() {
    return this.isRegisteredOnline;
  }

  public void setIsRegisteredOnline(boolean isRegisteredOnline) {
    this.isRegisteredOnline = isRegisteredOnline;
  }
}
