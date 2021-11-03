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
public class PrintedMediaService  {
	
	@Autowired 
	PrintedMediaRepository printedMediaRepository;
		
	// creates archive, returns it so we know it's not null 
	@Transactional 
	public PrintedMedia createPrintedMedia(
		String itemTitle,
		String description,
		String imageUrl,
		String itemNumber,
		String genre,
		Date publishDate,
		boolean isReservable,
		boolean isCheckedOut,
		String issueNumber)
	{
		PrintedMedia printedMedia = new PrintedMedia();
	    printedMedia.setItemTitle(itemTitle);
	    printedMedia.setDescription(description);
	    printedMedia.setImageUrl(imageUrl);
	    printedMedia.setItemNumber(itemNumber);
	    printedMedia.setGenre(genre);
	    printedMedia.setPublishDate(publishDate);
	    printedMedia.setIsReservable(isReservable);
		printedMedia.setIsCheckedOut(isCheckedOut);
		printedMedia.setIssueNumber(issueNumber);

	    printedMediaRepository.save(printedMedia);
	    return printedMedia;		
	}
	public PrintedMedia createPrintedMedia(String itemNumber) {
		PrintedMedia printedMedia = new PrintedMedia(); 
		long currentMillis = System.currentTimeMillis();
		Date currentDay = new Date(currentMillis);

		printedMedia.setItemTitle("itemTitle");
	    printedMedia.setDescription("description");
	    printedMedia.setImageUrl("imageUrl");
	    printedMedia.setItemNumber(itemNumber);
	    printedMedia.setGenre("genre");
	    printedMedia.setPublishDate(currentDay);
	    printedMedia.setIsReservable(true);
		printedMedia.setIsCheckedOut(false);
		printedMedia.setIssueNumber("issueNumber");
	    
		printedMediaRepository.save(printedMedia);
		return printedMedia;
	}
	
	// looks for printed media with the given item number, returns them if found
	@Transactional 
	public PrintedMedia getPrintedMedia(String itemNumber) {
		PrintedMedia printedMedia = printedMediaRepository.findPrintedMediaByItemNumber(itemNumber); 
		return printedMedia;
	}
	
	@Transactional 
	public List<PrintedMedia> getAllPrintedMedias() {
		return toList(printedMediaRepository.findAll()); 
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
