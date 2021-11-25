package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dao.PatronRepository;
import ca.mcgill.ecse321.librarysystem.dto.UserDto;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	PatronRepository patronRepository;

	@Autowired
	UserService userService;

	@GetMapping(value = { "/all", "/all/" })
	public List<UserDto> getAllUsers() {
		return userService
			.getAllUsers()
			.stream()
			.map(user -> convertToDto(user))
			.collect(Collectors.toList());
	}

	@GetMapping(value = { "/usersLoggedIn", "/usersLoggedIn/" })
	public List<UserDto> getAllUsersLoggedIn() {
		return userService
			.getListOfUsersLoggedIn()
			.stream()
			.map(user -> convertToDto(user))
			.collect(Collectors.toList());
	}

	@RequestMapping(
		value = { "/login/", "/login" },
		method = { RequestMethod.GET, RequestMethod.POST }
	)
	public ResponseEntity<?> login(
		@RequestParam("username") String user,
		@RequestParam("password") String pass
	) {
		try {
			User u = userService.logIn(user, pass);
			if (u == null) return new ResponseEntity<Object>(
				"User does not exist.",
				HttpStatus.BAD_REQUEST
			);
			return new ResponseEntity<Object>(convertToDto(u), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(
		value = { "/logout/{username}", "/logout/{username}/" },
		method = { RequestMethod.GET, RequestMethod.POST }
	)
	public ResponseEntity<?> logout(@PathVariable("username") String username) {
		try {
			User u = userService.logOut(username);
			if (u == null) return new ResponseEntity<Object>(
				"User does not exist.",
				HttpStatus.BAD_REQUEST
			);
			return new ResponseEntity<Object>(convertToDto(u), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = { "/change_password", "/change_password/" })
	public ResponseEntity<?> changePassword(
		@RequestParam("username") String user,
		@RequestParam("newPass") String newPass,
		@RequestParam("oldPass") String oldPass
	) {
		try {
			User u = userService.changePassword(user, newPass, oldPass);
			UserDto userDto = convertToDto(u);
			return new ResponseEntity<>(userDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**Method changes the user's name
	 * @author Arman
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @return userDto
	 */
	@PutMapping(value = { "/change_name", "/change_name/" })
	public ResponseEntity<?> changeName(
		@RequestParam String username,
		@RequestParam String firstName,
		@RequestParam String lastName
	) {
		User user = null;
		try {
			user = userService.changeName(username, firstName, lastName);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDto(user), HttpStatus.CREATED);
	}

	/**Method changes the user's email
	 * @author Arman
	 * @param username
	 * @param email
	 * @return userDto
	 */
	@PutMapping(value = { "/change_email", "/change_email/" })
	public ResponseEntity<?> changeEmail(
		@RequestParam String username,
		@RequestParam String email
	) {
		User user = null;
		try {
			user = userService.changeEmail(username, email);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDto(user), HttpStatus.CREATED);
	}

	/**Method changes the user's physical address
	 * @author Arman
	 * @param username
	 * @param address
	 * @return userDto
	 */
	@PutMapping(value = { "/change_address", "/change_address/" })
	public ResponseEntity<?> changeAddress(
		@RequestParam String username,
		@RequestParam String address
	) {
		User user = null;
		try {
			user = userService.changeAddress(username, address);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDto(user), HttpStatus.CREATED);
	}

	@GetMapping(value = { "get_user_by_id/{idNum}", "get_user_by_id/{idNum}/" })
	public ResponseEntity<?> getUserById(@PathVariable String idNum) {
		try {
			User user = userService.getUserAccountById(idNum);
			UserDto dto = convertToDto(user);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = { "get_user_by_username/{username}", "get_user_by_username/{username}/" })
	public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
		try {
			User user = userService.getUserAccountByUsername(username);
			UserDto dto = convertToDto(user);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	private UserDto convertToDto(User user) {
		UserDto dto = null;
		if (user instanceof Patron) {
			dto =
				new UserDto(
					user.getUsername(),
					user.getPassword(),
					user.getIdNum(),
					user.getEmail(),
					user.getFirstName(),
					user.getLastName(),
					user.getAddress(),
					user.getIsLoggedIn(),
					true
				);
		} else {
			dto =
				new UserDto(
					user.getUsername(),
					user.getPassword(),
					user.getIdNum(),
					user.getEmail(),
					user.getFirstName(),
					user.getLastName(),
					user.getAddress(),
					user.getIsLoggedIn(),
					false
				);
		}
		return dto;
	}
}
