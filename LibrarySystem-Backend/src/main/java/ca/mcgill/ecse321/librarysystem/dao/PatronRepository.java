package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.Patron;
import org.springframework.data.repository.CrudRepository;

public interface PatronRepository extends CrudRepository<Patron, String> {
  Patron findPatronByIdNum(String idNum);
  Patron findUserByIdNum(String idNum);
  Patron findPatronByUsername(String username);
  void deletePatronByIdNum(String idNum);
}
