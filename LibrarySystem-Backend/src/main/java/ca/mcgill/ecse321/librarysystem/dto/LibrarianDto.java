package ca.mcgill.ecse321.librarysystem.dto;

import java.util.Set;

import ca.mcgill.ecse321.librarysystem.model.*;

public class LibrarianDto {
	private String idNum;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String username;
	private String password; 
	private Set<Shift> shifts;
	public LibrarianDto() {
	}
	
	public LibrarianDto(String idNum) {
		this(idNum, "First Name", "Surname", "Address", "Email", "username", "password"); 
	}
	
	public LibrarianDto(String idNum, String firstName, String lastName, String address, String email, String username, String password) {
		this.idNum = idNum; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.address = address; 
		this.email = email; 
		this.username = username; 
		this.password = password; 
	}
	
	public LibrarianDto(String idNum, String firstName, String lastName, String address, String email, String username, String password, Set<Shift> shifts) {
		this.idNum = idNum; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.address = address; 
		this.email = email; 
		this.username = username; 
		this.password = password; 
		this.shifts= shifts; 
	}
	
	public String getIdNum() {
		return this.idNum; 
	}
	
	public void setIdNum(String idNum) {
		this.idNum = idNum; 
	}
	
	public Set<Shift> getShifts() {
		return this.shifts; 
	} 
	
	public void setShifts(Set<Shift> shifts) {
		this.shifts = shifts; 
	}
}
