package ca.mcgill.ecse321.librarysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.librarysystem.model.*;

@RestController
@SpringBootApplication
public class LibrarySystemApplication {
  
  private static LibrarySystem librarySystem; 
  private static User currentUser; 
  	
  public static void main(String[] args) {
    SpringApplication.run(LibrarySystemApplication.class, args);
  }
  
  @RequestMapping("/")
  public String greeting() {
    return "Hello world!";
  }
  
   public static LibrarySystem getLibrarySystem() {
	   return librarySystem; 
   }
   
   public static void newLibrarySystem() {
	   librarySystem = new LibrarySystem(); 
   }

	public static User getCurrentUser() {
		return currentUser;
	}
	public static void setCurrentUser(User user) {
		currentUser = user;
	}
	

  
}
