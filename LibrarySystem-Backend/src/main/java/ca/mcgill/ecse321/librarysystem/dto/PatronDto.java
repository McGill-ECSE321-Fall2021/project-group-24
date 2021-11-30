package ca.mcgill.ecse321.librarysystem.dto;

import javax.persistence.Id;

public class PatronDto {

  @Id
  private String idNum;

  private String firstName;
  private String lastName;
  private String address;
  private String email;
  private String username;
  private String password;

  private boolean isVerified;
  private boolean isResident;
  private boolean isRegisteredOnline;

  public PatronDto() {}

  public PatronDto(String idNum) {
    this(
      idNum,
      "First Name",
      "Surname",
      "Address",
      "Email",
      "username",
      "password",
      true,
      true,
      false
    );
  }

  public PatronDto(
    String idNum,
    String firstName,
    String lastName,
    String address,
    String email,
    String username,
    String password,
    boolean isVerified,
    boolean isResident,
    boolean isRegisteredOnline
  ) {
    this.idNum = idNum;
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.email = email;
    this.username = username;
    this.password = password;
    this.isResident = isResident;
    this.isRegisteredOnline = isRegisteredOnline;
    this.isVerified = isVerified;
  }

  public String getIdNum() {
    return this.idNum;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getAddress() {
    return this.address;
  }

  public String getEmail() {
    return this.email;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public boolean isVerified() {
    return this.isVerified;
  }

  public boolean isResident() {
    return this.isResident;
  }

  public boolean isRegisteredOnline() {
    return this.isRegisteredOnline;
  }
}
