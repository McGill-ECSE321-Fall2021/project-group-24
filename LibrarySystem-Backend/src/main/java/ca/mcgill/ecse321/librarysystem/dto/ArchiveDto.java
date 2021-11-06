package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Date;

import ca.mcgill.ecse321.librarysystem.model.Item;

public class ArchiveDto extends ItemDto{

	
	
	public ArchiveDto(String itemTitle,
		    String description,
		    String imageURL,
		    String genre,
		    Date publishDate,
		    boolean isReservable,
		    String currentReservationId,
		    String itemNumber) {
	    this.itemTitle =  itemTitle;
	    this.description = description;
	    this.imageUrl = imageURL;
	    this.genre = genre;
	    this.publishDate = publishDate;
	    this.isReservable = isReservable;
	    this.itemNumber = itemNumber;
	    this.currentReservationId = currentReservationId;
	    this.type = Item.Type.Archive;
	}
	
}
