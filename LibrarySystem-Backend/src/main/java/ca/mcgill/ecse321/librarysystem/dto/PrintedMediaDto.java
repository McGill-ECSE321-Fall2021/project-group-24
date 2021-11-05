package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Date;

import ca.mcgill.ecse321.librarysystem.model.Item;

public class PrintedMediaDto extends ItemDto{
	
	  private String issueNumber;
	
	
	public PrintedMediaDto(String itemTitle,
		    String description,
		    String imageURL,
		    String genre,
		    Date publishDate,
		    boolean isReservable,
		    String currentReservationId,
		    String itemNumber, String issueNumber) {
	    this.itemTitle =  itemTitle;
	    this.description = description;
	    this.imageUrl = imageURL;
	    this.genre = genre;
	    this.publishDate = publishDate;
	    this.isReservable = isReservable;
	    this.itemNumber = itemNumber;
	    this.currentReservationId = currentReservationId;
	    this.type = Item.Type.PrintedMedia;
	    this.issueNumber = issueNumber;
	}
	
	
	  public String getIssueNumber() {
		    return this.issueNumber;
		  }
	
}
