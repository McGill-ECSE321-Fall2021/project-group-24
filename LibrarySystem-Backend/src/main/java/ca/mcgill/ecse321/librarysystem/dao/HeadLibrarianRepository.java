package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.HeadLibrarian;

public interface HeadLibrarianRepository extends CrudRepository<HeadLibrarian, String> {
	HeadLibrarian findHeadLibrarianByIdNum(String idNum);
}
