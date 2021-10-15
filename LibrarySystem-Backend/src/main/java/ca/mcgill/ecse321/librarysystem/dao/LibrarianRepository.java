package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Librarian;

public interface LibrarianRepository extends CrudRepository<Librarian, String> {
	Librarian findLibrarianByIdNum(String idNum);
}
