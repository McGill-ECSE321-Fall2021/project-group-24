package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.Librarian;
import org.springframework.data.repository.CrudRepository;

public interface LibrarianRepository extends CrudRepository<Librarian, String> {
  Librarian findUserByIdNum(String idNum);
  Librarian findUserByUsername(String username);
}
