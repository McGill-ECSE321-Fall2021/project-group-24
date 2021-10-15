package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.PrintedMedia;

public interface PrintedMediaRepository extends CrudRepository<PrintedMedia, String> {
	PrintedMedia findBookByIdNum(String idNum);
}