package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.MusicAlbum;
import org.springframework.data.repository.CrudRepository;

public interface MusicAlbumRepository
  extends CrudRepository<MusicAlbum, String> {
  MusicAlbum findMusicAlbumByIdNum(String idNum);
}
