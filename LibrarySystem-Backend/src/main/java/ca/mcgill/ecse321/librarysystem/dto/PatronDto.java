package ca.mcgill.ecse321.librarysystem.dto;

public class PatronDto extends UserDto{
	
	private boolean isVerified;
	private boolean isResident;
	private boolean isRegisteredOnline;
	
	
	public PatronDto() {
	}
	
	public PatronDto(String idNum) {
		this(idNum, "First Name", "Surname", "Address", "Email", "username", "password", true, true, false); 
	}
	
	public PatronDto(String user, String pass, String id, String mail, String first, String last, String Address, boolean ResidentStatus, boolean Verification, boolean Registration) {
		super(user, pass, id, mail, first, last, Address);
		this.isRegisteredOnline = ResidentStatus;
		this.isVerified = Verification;
		this.isRegisteredOnline = Registration;
	}
	
	
	public boolean getIsVerified() {
	    return this.isVerified;
	  }

	  public void setIsVerified(boolean isVerified) {
	    this.isVerified = isVerified;
	  }

	  public boolean getIsResident() {
	    return this.isResident;
	  }

	  public void setIsResident(boolean isResident) {
	    this.isResident = isResident;
	  }

	  public boolean getIsRegisteredOnline() {
	    return this.isRegisteredOnline;
	  }

	  public void setIsRegisteredOnline(boolean isRegisteredOnline) {
	    this.isRegisteredOnline = isRegisteredOnline;
	  }
}
