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
public class ArchiveService  {
	
	@Autowired 
	ArchiveRepository archiveRepository;
		
	// creates archive, returns it so we know it's not null 
	@Transactional 
	public Archive createArchive(
		String itemTitle,
		String description,
		String imageUrl,
		String itemNumber,
		String genre,
		Date publishDate,
		boolean isReservable,
		boolean isCheckedOut)
	{
		Archive archive = new Archive();
	    archive.setItemTitle(itemTitle);
	    archive.setDescription(description);
	    archive.setImageUrl(imageUrl);
	    archive.setItemNumber(itemNumber);
	    archive.setGenre(genre);
	    archive.setPublishDate(publishDate);
	    archive.setIsReservable(isReservable);
		archive.setIsCheckedOut(isCheckedOut);

	    archiveRepository.save(archive);
	    return archive;		
	}
	public Archive createArchive(String itemNumber) {
		Archive archive = new Archive(); 
		// FIX PAST THIS POINT
		long currentMillis = System.currentTimeMillis();
		Date currentDay = new Date(currentMillis);

		archive.setItemTitle("itemTitle");
	    archive.setDescription("description");
	    archive.setImageUrl("imageUrl");
	    archive.setItemNumber(itemNumber);
	    archive.setGenre("genre");
	    archive.setPublishDate(currentDay);
	    archive.setIsReservable(true);
		archive.setIsCheckedOut(false);
	    
		archiveRepository.save(archive);
		return archive;
	}
	
	// looks for an archive with the given item number, returns them if found
	@Transactional 
	public Archive getArchive(String itemNumber) {
		Archive archive = archiveRepository.findArchiveByItemNumber(itemNumber); 
		return archive;
	}
	
	@Transactional 
	public List<Archive> getAllArchives() {
		return toList(archiveRepository.findAll()); 
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
