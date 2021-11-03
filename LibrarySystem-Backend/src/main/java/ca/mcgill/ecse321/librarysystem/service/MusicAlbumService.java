package ca.mcgill.ecse321.librarysystem.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;

@Service
public class MusicAlbumService {
	
	@Autowired 
	MusicAlbumRepository musicAlbumRepository; 
		
	// creates music album, returns it so we know it's not null 
	@Transactional 
	public MusicAlbum musicAlbum(
		String itemTitle,
		String description,
		String imageUrl,
		String itemNumber,
		String genre,
		Date publishDate,
		boolean isReservable,
		boolean isCheckedOut,
		String artist,
		String recordingLabel)
	{
		MusicAlbum musicAlbum = new MusicAlbum();
	    musicAlbum.setItemTitle(itemTitle);
	    musicAlbum.setDescription(description);
	    musicAlbum.setImageUrl(imageUrl);
	    musicAlbum.setItemNumber(itemNumber);
	    musicAlbum.setGenre(genre);
	    musicAlbum.setPublishDate(publishDate);
	    musicAlbum.setIsReservable(isReservable);
		musicAlbum.setIsCheckedOut(isCheckedOut);
		musicAlbum.setArtist(artist);
		musicAlbum.setRecordingLabel(recordingLabel);

	    musicAlbumRepository.save(musicAlbum);
	    return musicAlbum;		
	}
	public MusicAlbum createMusicAlbum(String itemNumber) {
		MusicAlbum musicAlbum = new MusicAlbum(); 
		long currentMillis = System.currentTimeMillis();
		Date currentDay = new Date(currentMillis);

		musicAlbum.setItemTitle("itemTitle");
	    musicAlbum.setDescription("description");
	    musicAlbum.setImageUrl("imageUrl");
	    musicAlbum.setItemNumber(itemNumber);
	    musicAlbum.setGenre("genre");
	    musicAlbum.setPublishDate(currentDay);
	    musicAlbum.setIsReservable(true);
		musicAlbum.setIsCheckedOut(false);
		musicAlbum.setArtist("artist");
		musicAlbum.setRecordingLabel("recordingLabel");
	    
		musicAlbumRepository.save(musicAlbum);
		return musicAlbum;
	}
	
	// looks for an archive with the given item number, returns them if found
	@Transactional 
	public MusicAlbum getMusicAlbum(String itemNumber) {
		MusicAlbum musicAlbum = musicAlbumRepository.findMusicAlbumByItemNumber(itemNumber); 
		return musicAlbum;
	}
	
	@Transactional 
	public List<MusicAlbum> getAllMusicAlbum() {
		return toList(musicAlbumRepository.findAll()); 
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
