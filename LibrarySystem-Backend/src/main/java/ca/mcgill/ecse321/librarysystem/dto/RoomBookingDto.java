package ca.mcgill.ecse321.librarysystem.dto;

public class RoomBookingDto {
	  private String roomNum;
	  private String idNum;
	
	public RoomBookingDto() {
		
	}
	
	public RoomBookingDto(String roomNum, String idNum) {
	    this.roomNum = roomNum;
	    this.idNum = idNum;
	}
	
	public String getRoomNum() {
		return this.roomNum; 
	}
	
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum; 
	}
	
	public String getidNum() {
		return this.idNum; 
	}
	
	public void setIdNum(String idNum) {
		this.idNum = idNum; 
	}
}
