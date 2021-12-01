package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "user")
public abstract class User {

  private String username;
  private String password;
  private String email;

  @Id
  private String idNum;

  private String firstName;
  private String lastName;
  private String address;
  private boolean isLoggedIn;

  public String getIdNum() {
    return idNum;
  }

  public void setIdNum(String timeSlotId) {
    this.idNum = timeSlotId;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress() {
    return this.address;
  }

  public void setIsLoggedIn(boolean x) {
    this.isLoggedIn = x;
  }

  public boolean getIsLoggedIn() {
    return this.isLoggedIn;
  }
}
