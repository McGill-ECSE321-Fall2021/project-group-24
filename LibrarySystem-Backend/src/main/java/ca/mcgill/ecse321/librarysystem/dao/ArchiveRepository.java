package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Archive;

public interface ArchiveRepository extends CrudRepository<Archive, String> {
	Archive findBookByIdNum(String idNum);
}