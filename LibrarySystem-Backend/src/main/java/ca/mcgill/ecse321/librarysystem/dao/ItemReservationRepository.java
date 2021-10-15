package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.ItemReservation;

public interface ItemReservationRepository extends CrudRepository<ItemReservation, String> {
	ItemReservation findReservationByReservationID(String reservationID);
}
