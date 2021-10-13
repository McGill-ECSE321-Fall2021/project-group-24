package ca.mcgill.ecse321.librarysystem.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//import ca.mcgill.ecse321.eventregistration.model.Person;
//import ca.mcgill.ecse321.eventregistration.model.Event;

@Repository
public class LibrarySystemRepository {

	@Autowired
	EntityManager entityManager;

	//GOT TO 2.2.2

}