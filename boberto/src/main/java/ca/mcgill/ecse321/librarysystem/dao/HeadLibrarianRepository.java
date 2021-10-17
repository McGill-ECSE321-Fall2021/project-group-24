package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.HeadLibrarian;
import org.springframework.data.repository.CrudRepository;


public interface HeadLibrarianRepository extends CrudRepository<HeadLibrarian, String> {
	HeadLibrarian findUserByIdNum(String idNumber);
}
