package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Room;

public interface RoomRepository extends CrudRepository<Room, String> {
	Room findRoomByIdNum(String roomNum);
}
