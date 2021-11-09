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
import ca.mcgill.ecse321.librarysystem.dto.UserDto;
import ca.mcgill.ecse321.librarysystem.model.Patron;
import ca.mcgill.ecse321.librarysystem.model.User;
import ca.mcgill.ecse321.librarysystem.service.PatronService;
import ca.mcgill.ecse321.librarysystem.service.UserService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private PatronService patronService;
	
	@Autowired
	PatronRepository patronRepository;
	
	@Autowired
	UserService userService;
	
	
	@GetMapping(value = {"/users","/users/"})
	public List<UserDto> getAllUsers(){
		return userService.getAllUsers().stream().map(user -> 
		convertToDto(user)).collect(Collectors.toList());
	}
	
	
	@GetMapping(value= {"/usersLoggedIn","/usersLoggedIn/"})
	public List<UserDto> getAllUsersLoggedIn(){
		return userService.getListOfUsersLoggedIn().stream().map(user -> convertToDto(user)).collect(Collectors.toList()); 
	}
	
	
	@RequestMapping(value = { "/login/", "/login" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> login(@RequestParam("username") String user, @RequestParam("password") String pass){
		try {
			User u = userService.logIn(user, pass);
			if(u==null) return new ResponseEntity("User does not exist.", HttpStatus.BAD_REQUEST);
			return new ResponseEntity(convertToDto(u), HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = { "/logout/{username}", "/logout/{username}/" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> logout(@PathVariable("username") String username){
		try {
			User u = userService.logOut(username);
			if(u==null) return new ResponseEntity("User does not exist.", HttpStatus.BAD_REQUEST);
			return new ResponseEntity(convertToDto(u), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	
	
	@RequestMapping(value = { "/change_password", "/change_password/" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> changePassword(@RequestParam("username") String user, @RequestParam("newPass") String newPass, @RequestParam("oldPass") String oldPass) {
		
		try {
			User u = userService.changePassword(user, newPass, oldPass);
			UserDto UserDto = convertToDto(u);
			return new ResponseEntity<>(u, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping(value= {"get_user_by_id/{Id}", "get_user_by_id/{Id}/"})
	public ResponseEntity<?> getUserById(@PathVariable("Id") String Id){
		try {
			User user = userService.getUserAccountById(Id);
			UserDto dto = convertToDto(user);
			return new ResponseEntity(dto, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(value= {"get_user_by_username/{username}", "get_user_by_username/{username}/"})
	public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username){
		try {
			User user = userService.getUserAccountByUsername(username);
			UserDto dto = convertToDto(user);
			return new ResponseEntity(dto, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	private UserDto convertToDto(User user) {
		UserDto dto = new UserDto(user.getUsername(), user.getPassword(), user.getIdNum(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getIsLoggedIn());
		return dto;
	}
}
