package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.*;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface RoomBookingRepository
  extends CrudRepository<RoomBooking, String> {
  RoomBooking findRoomBookingByTimeSlotId(String timeSlotId);
  List<RoomBooking> findRoomBookingByRoomNum(String roomNum);
  List<RoomBooking> findRoomBookingsByIdNum(String idNum);
}
