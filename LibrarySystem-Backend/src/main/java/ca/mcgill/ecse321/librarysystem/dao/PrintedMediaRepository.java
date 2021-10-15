package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.PrintedMedia;
import org.springframework.data.repository.CrudRepository;

public interface PrintedMediaRepository
  extends CrudRepository<PrintedMedia, String> {
  PrintedMedia findPrintedMediaByIdNum(String idNum);
}
