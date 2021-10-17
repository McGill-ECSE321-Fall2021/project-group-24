package ca.mcgill.ecse321.librarysystem.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.librarysystem.model.RoomBooking;

public interface RoomBookingRepository extends CrudRepository<RoomBooking, String> {
	RoomBooking findRoomBookingByBookingID(String bookingID);
}
