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
	public PatronDto getPatronById(@PathVariable("id") String id){
		Patron p = patronService.getPatronAccountByID(id);
		PatronDto output = convertToDto(p);
		return output;
	}
	
	
	@GetMapping(value = {"/patronByUsername/{username}","/patronByUsername/{username}/"})
	public PatronDto getPatronByUsername(@PathVariable("username") String username){
		Patron p = patronService.getPatronAccountByUsername(username);
		PatronDto output = convertToDto(p);
		return output;
	}
	
	
	
	@RequestMapping(value = { "/patronIRL", "/patronIRL/" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> createPatronIRL(@RequestParam("username") String username,  
			@RequestParam ("first") String first, 
			@RequestParam ("last") String last, 
			@RequestParam ("ResidentStatus") boolean ResidentStatus,
			@RequestParam ("Address") String Address, 
			@RequestParam ("mail") String mail
			) throws IllegalArgumentException {
		
		Patron patron = null;
		try {
			patron = patronService.createPatronIRL(username, first, last, ResidentStatus, Address, mail);
			return new ResponseEntity<>(patron, HttpStatus.OK);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@PostMapping(value = { "/patron/{username}", "/patron/{username}/" })
	public PatronDto createPatron(@PathVariable("username") String username,
			@RequestParam String password,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam boolean isResident,
			@RequestParam String address,
			@RequestParam String email
			) throws IllegalArgumentException {
		String idNum = firstName+"Patron-"+patronService.toList(patronRepository.findAll()).size();
		
		Patron patron = patronService.createPatronOnline(username, password, firstName, lastName, isResident, address, email);
		patron.setIdNum(idNum);
		return convertToDto(patron);
	}
	
	@PostMapping(value = { "/patronChangePassword/{newPass}", "/patronChangePassword/{newPass}/"})
	public ResponseEntity<?> changePatronPassword(@PathVariable("newPass") String password, @RequestParam String username, @RequestParam String oldPass) {
		Patron p = patronRepository.findPatronByUsername(username);
		if(p==null) {
			return new ResponseEntity<>("Invalid request.", HttpStatus.BAD_REQUEST);
		}
		
		if(!p.getPassword().equals(oldPass)) {
			return new ResponseEntity<>("Wrong password entered.", HttpStatus.BAD_REQUEST);
		}
		
		userService.changePassword(username, password);
		PatronDto patronDto = convertToDto(p);
		return new ResponseEntity<>(patronDto, HttpStatus.OK);
	}
	
	
	
	
	
	private PatronDto convertToDto(Patron patron) {
		PatronDto dto = new PatronDto(patron.getUsername(), patron.getPassword(), patron.getIdNum(), patron.getEmail(), patron.getFirstName(), patron.getLastName(), patron.getAddress(), patron.getIsResident(), patron.getIsVerified(), patron.getIsRegisteredOnline());
		return dto;
	}
}
