package ca.mcgill.ecse321.librarysystem.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.librarysystem.model.*;
import java.util.List;

public interface RoomBookingRepository extends CrudRepository<RoomBooking, String> {
	RoomBooking findRoomBookingByTimeSlotId(String timeSlotId);
	List<RoomBooking> findRoomBookingByRoomNum(String roomNum);
	List<RoomBooking> findRoomBookingsByIdNum(String idNum);
}
