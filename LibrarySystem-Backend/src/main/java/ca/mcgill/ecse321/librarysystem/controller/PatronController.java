package ca.mcgill.ecse321.librarysystem.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.librarysystem.dao.PatronRepository;
import ca.mcgill.ecse321.librarysystem.dto.PatronDto;
import ca.mcgill.ecse321.librarysystem.model.Patron;
import ca.mcgill.ecse321.librarysystem.service.PatronService;
import ca.mcgill.ecse321.librarysystem.service.UserService;

@CrossOrigin(origins = "*")
@RestController

public class PatronController {
	
	@Autowired
	private PatronService patronService;
	
	@Autowired
	PatronRepository patronRepository;
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = {"/patrons","/patrons/"})
	public List<PatronDto> getAllPatrons(){
		return patronService.getAllPatrons().stream().map(patron -> 
		convertToDto(patron)).collect(Collectors.toList());
	}
	
	@GetMapping(value = {"/patronById/{id}","/patronById/{id}/"})
	public ResponseEntity<?> getPatronById(@PathVariable("id") String id){
		try {
			Patron p = patronService.getPatronAccountByID(id);
			if(p==null) return new ResponseEntity("Patron does not exist.", HttpStatus.NOT_FOUND);
			PatronDto output = convertToDto(p);
			return new ResponseEntity(output, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping(value = {"/patronByUsername/{username}","/patronByUsername/{username}/"})
	public ResponseEntity<?> getPatronByUsername(@PathVariable("username") String username){
		try {
			Patron p = patronService.getPatronAccountByUsername(username);
		
			if(p==null) {
				return new ResponseEntity<>("Patron does not exist.", HttpStatus.NOT_FOUND);
			}
		
			PatronDto output = convertToDto(p);
			return new ResponseEntity<>(output,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@RequestMapping(value = { "/patronIRL", "/patronIRL/" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> createPatronIRL(
			@RequestParam ("first") String first, 
			@RequestParam ("last") String last, 
			@RequestParam ("isResident") boolean isResident,
			@RequestParam ("address") String address
			) throws IllegalArgumentException {
		
		Patron patron = null;
		try {
			patron = patronService.createPatronIRL(first, last, isResident, address);
			return new ResponseEntity<>(patron, HttpStatus.OK);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@RequestMapping(value = { "/createPatron", "/createPatron/" }, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> createPatron(
			@RequestParam ("username") String username,
			@RequestParam ("password") String password,
			@RequestParam ("first") String firstName,
			@RequestParam ("last") String lastName,
			@RequestParam ("isResident") boolean isResident,
			@RequestParam ("address") String address,
			@RequestParam ("email") String email
			) throws IllegalArgumentException {
		
		try {
			Patron patron = patronService.createPatronOnline(username, password, firstName, lastName, isResident, address, email);
			return new ResponseEntity<>(convertToDto(patron), HttpStatus.OK);
			
		}catch(IllegalArgumentException e) {	
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	
	
	
	
	
	private PatronDto convertToDto(Patron patron) {
		PatronDto dto = new PatronDto(patron.getIdNum(), patron.getFirstName(), patron.getLastName(), patron.getAddress(), patron.getEmail(), patron.getUsername(), patron.getPassword(),  patron.getIsVerified(),patron.getIsResident(), patron.getIsRegisteredOnline());
		return dto;
	}
}
