package ca.mcgill.ecse321.librarysystem;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.librarysystem.dao.HeadLibrarianRepository;
import ca.mcgill.ecse321.librarysystem.model.HeadLibrarian;
import ca.mcgill.ecse321.librarysystem.service.HeadLibrarianService;

@RestController
@SpringBootApplication
public class LibrarySystemApplication {
  
	
	@Autowired 
    HeadLibrarianRepository headLibrarianRepository;
	@Autowired
	HeadLibrarianService service;
	
  public static void main(String[] args) {
    SpringApplication.run(LibrarySystemApplication.class, args);
  }
  
  @RequestMapping("/")
  public String greeting() {
    return "Hello world!";
  }
  
  
  @Bean
  InitializingBean sendDatabase() {
      return () -> {
          if (service.toList(headLibrarianRepository.findAll()).size()==0) {
          	HeadLibrarian headLibrarian = new HeadLibrarian();
          	headLibrarian.setIdNum("admin");
          	headLibrarian.setUsername("admin");
          	headLibrarian.setPassword("admin");
          	headLibrarianRepository.save(headLibrarian);
          }
      };
  }
}
