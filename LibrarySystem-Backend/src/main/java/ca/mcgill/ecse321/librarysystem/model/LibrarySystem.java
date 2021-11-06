package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
/*@Arman (Del 2)
 * Del 2 edits: Added name, email, address, and phone number (i.e. basic contact info for the library) 
 */
/*
 * @Saagar (Del 2)
 * Changed LibrarySystem to just have the info about the library
 */
@Entity
public class LibrarySystem {

	private String systemNum;
	private String name; 
	private String email; 
	private String address; 
	private String phoneNumber; 
	
	public String getName() {
		return this.name; 
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
	    return address;
	}
	
	public void setAddress(String address) {
	    this.address = address;
	}
	
	public String getPhoneNumber() {
	    return this.phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
	    this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
	    return email;
	}
	
	public void setEmail(String email) {
	    this.email = email;
	}

	
	@Id
	public String getSystemNum() {
		return this.systemNum;
	}

	public void setSystemNum(String systemNum) {
		this.systemNum = systemNum;
	}
}
