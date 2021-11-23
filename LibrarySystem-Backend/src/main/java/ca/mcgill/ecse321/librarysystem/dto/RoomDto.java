package ca.mcgill.ecse321.librarysystem.dto;

public class RoomDto {

  private String roomNum;
  private int capacity;

  public RoomDto() {}

  public RoomDto(String roomNum) {
    this(roomNum, 5);
  }

  public RoomDto(String roomNum, int capacity) {
    this.roomNum = roomNum;
    this.capacity = capacity;
  }

  public String getRoomNum() {
    return this.roomNum;
  }

  public void setRoomNum(String roomNum) {
    this.roomNum = roomNum;
  }

  public int getCapacity() {
    return this.capacity;
  }
}
