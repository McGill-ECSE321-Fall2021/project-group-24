package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.ItemReservation;
import org.springframework.data.repository.CrudRepository;

public interface ItemReservationRepository extends CrudRepository<ItemReservation, String> {
	ItemReservation findItemReservationByTimeSlotId(String timeSlotId);
	ItemReservation findItemReservationByIdNum(String idNum);
}
