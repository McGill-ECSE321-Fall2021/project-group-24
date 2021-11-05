package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.ItemReservation;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ItemReservationRepository extends CrudRepository<ItemReservation, String> {
	ItemReservation findItemReservationByTimeSlotId(String timeSlotId);
	List<ItemReservation> findItemReservationsByIdNum(String idNum);
	List<ItemReservation> findItemReservationsByItemNumber(String itemNumber);
}
