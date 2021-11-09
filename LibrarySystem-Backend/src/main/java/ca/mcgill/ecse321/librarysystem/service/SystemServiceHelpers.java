package ca.mcgill.ecse321.librarysystem.service;

public class SystemServiceHelpers {

  public static void validInput(
    String firstName,
    String lastName,
    String address,
    String email,
    String username,
    String password
  ) {
    if (
      firstName == null || firstName.trim().length() == 0
    ) throw new IllegalArgumentException("Please submit a valid email.");
    if (
      lastName == null || lastName.trim().length() == 0
    ) throw new IllegalArgumentException("Please submit a valid name.");
    if (
      address == null || address.trim().length() == 0
    ) throw new IllegalArgumentException("Please submit a valid password.");
    if (
      email == null || email.trim().length() == 0
    ) throw new IllegalArgumentException("Please submit a valid phone.");
    if (
      username == null || username.trim().length() == 0
    ) throw new IllegalArgumentException("Please enter a valid username.");
    if (
      password == null || password.trim().length() == 0
    ) throw new IllegalArgumentException("Please submit a valid phone.");
  }
}
