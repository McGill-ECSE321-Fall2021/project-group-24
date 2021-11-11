package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.HeadLibrarianService;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
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

  public static void validBookInput(
    // String currentUserId,
    String itemTitle,
    String description,
    String imageUrl,
    String genre,
    Date publishDate,
    boolean isReservable,
    String author,
    String publisher
  ) {
    if (
      itemTitle == null || itemTitle.trim().length() == 0
    ) throw new IllegalArgumentException("Item must have a title");
  }


}
