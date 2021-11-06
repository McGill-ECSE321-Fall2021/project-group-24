package ca.mcgill.ecse321.librarysystem.dto;

public class LibrarianDto {

  private String idNum;
  private String firstName;
  private String lastName;
  private String address;
  private String email;
  private String username;
  private String password;

  public LibrarianDto() {}

  public LibrarianDto(String idNum) {
    this(
      idNum,
      "First Name",
      "Surname",
      "Address",
      "Email",
      "username",
      "password"
    );
  }

  public LibrarianDto(
    String idNum,
    String firstName,
    String lastName,
    String address,
    String email,
    String username,
    String password
  ) {
    this.idNum = idNum;
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.email = email;
    this.username = username;
    this.password = password;
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
}
