package ca.mcgill.ecse321.librarysystem.dto;

public class HeadLibrarianDto {
	private String idNum;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String username;
	private String password; 
	
	public HeadLibrarianDto() {
	}
	
	public HeadLibrarianDto(String idNum) {
		this(idNum, "First Name", "Surname", "Address", "Email", "username", "password"); 
	}
	
	public HeadLibrarianDto(String idNum, String firstName, String lastName, String address, String email, String username, String password) {
		this.idNum = idNum; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.address = address; 
		this.email = email; 
		this.username = username; 
		this.password = password; 
	}
	
	public String getIdNum() {
		return this.idNum; 
	}
	
	public void setIdNum(String idNum) {
		this.idNum = idNum; 
	}
}
