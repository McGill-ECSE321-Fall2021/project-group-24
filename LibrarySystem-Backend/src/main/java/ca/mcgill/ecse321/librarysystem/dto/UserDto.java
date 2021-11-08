package ca.mcgill.ecse321.librarysystem.dto;

import javax.persistence.Id;

public class UserDto {
	private String username;
	private String password;
	private String email;

	@Id
	private String idNum;

	private String firstName;
	private String lastName;
	private String address;
	private boolean isLoggedIn;
	
	
	public UserDto() {	
	}
	
	public UserDto(String user, String pass, String id, String mail, String first, String last, String Address, boolean isLoggedIn) {
		this.username = user;
		this.password = pass;
		this.email=mail;
		this.idNum = id;
		this.firstName = first;
		this.lastName = last;
		this.address = Address;
		this.isLoggedIn = isLoggedIn;
	}
	
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String timeSlotId) {
		this.idNum = timeSlotId;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}
	
	public void setIsLoggedIn(boolean x) {
		this.isLoggedIn=x;
	}
	
	public boolean getIsLoggedIn() {
		return this.isLoggedIn;
	}
	
}
