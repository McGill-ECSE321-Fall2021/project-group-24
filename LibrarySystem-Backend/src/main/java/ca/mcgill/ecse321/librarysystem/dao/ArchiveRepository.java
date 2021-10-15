package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.Archive;
import org.springframework.data.repository.CrudRepository;

public interface ArchiveRepository extends CrudRepository<Archive, String> {
  Archive findArchiveByIdNum(String idNum);
}
