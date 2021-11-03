package ca.mcgill.ecse321.librarysystem.dto;

public class PatronDto {
	private String idNum;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String username;
	private String password; 

	  private boolean isVerified;
	  private boolean isResident;
	  private boolean isRegisteredOnline;
	  
	public PatronDto() {
	}
	
	public PatronDto(String idNum) {
		this(idNum, "First Name", "Surname", "Address", "Email", "username", "password", true, true, false); 
	}
	
	public PatronDto(String idNum, String firstName, String lastName, String address, String email, String username, String password, boolean isVerified, boolean isResident, boolean isRegisteredOnline) {
		this.idNum = idNum; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.address = address; 
		this.email = email; 
		this.username = username; 
		this.password = password; 
		this.isResident = isResident;
		this.isRegisteredOnline = isRegisteredOnline;
		this.isVerified = isVerified;
	}
	
	public String getIdNum() {
		return this.idNum; 
	}
	
	public void setIdNum(String idNum) {
		this.idNum = idNum; 
	}
}
