package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;

public class SystemServiceHelpers {

  static LibrarianRepository librarianRepository;
  static HeadLibrarianRepository headLibrarianRepository;

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
    ) throw new IllegalArgumentException("Please enter your first name.");
    if (
      lastName == null || lastName.trim().length() == 0
    ) throw new IllegalArgumentException("Please enter your last name.");
    if (
      address == null || address.trim().length() == 0
    ) throw new IllegalArgumentException("Please enter your address.");
    if (
      email == null || email.trim().length() == 0
    ) throw new IllegalArgumentException("Please enter your email.");
    if (
      username == null || username.trim().length() == 0
    ) throw new IllegalArgumentException("Please enter your username.");
    if (
      password == null || password.trim().length() == 0
    ) throw new IllegalArgumentException("Please enter your password.");

    if (username.contains(" ")) throw new IllegalArgumentException(
      "Please enter a username without spaces."
    );
    if (password.contains(" ")) throw new IllegalArgumentException(
      "Please enter a password without spaces."
    );
    if (email.contains(" ")) throw new IllegalArgumentException(
      "Please enter an email without spaces."
    );
  }

  public static boolean isHeadLibrarian(String currentUserId) {
    System.out.println("HERE");
    HeadLibrarian currentHeadLibrarian = headLibrarianRepository.findUserByIdNum(
      currentUserId
    );
    System.out.println("HERE2");
    if (currentHeadLibrarian == null || !currentHeadLibrarian.getIsLoggedIn()) {
      throw new IllegalArgumentException(
        "You are not authorized to do this. Only the Head Librarian can."
      );
    }
    return true;
  }
}
