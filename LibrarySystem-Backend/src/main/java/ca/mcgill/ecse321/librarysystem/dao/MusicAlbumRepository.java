package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.MusicAlbum;

public interface MusicAlbumRepository extends CrudRepository<MusicAlbum, String> {
	MusicAlbum findBookByIdNum(String idNum);
}