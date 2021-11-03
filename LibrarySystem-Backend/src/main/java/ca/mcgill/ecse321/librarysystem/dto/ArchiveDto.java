package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Date;

public class ArchiveDto {
	private String itemTitle;
	private String description;
	private String imageUrl;
	private String itemNumber;
	private String genre;
	private Date publishDate;
	private boolean isReservable;
	private boolean isCheckedOut;
	
	public ArchiveDto() {
	}
	
	public ArchiveDto(String itemNumber) {
		this( "itemTitle",
			     "description",
			    "imageURL",
			    false,
			    "genre",
			    new Date(0),
			    true,
			    itemNumber); 
	}
	
	public ArchiveDto(String itemTitle,
		    String description,
		    String imageURL,
		    boolean isCheckedOut,
		    String genre,
		    Date publishDate,
		    boolean isReservable,
		    String itemNumber) {
	    this.itemTitle =  itemTitle;
	    this.description = description;
	    this.imageUrl = imageURL;
	    this.isCheckedOut = isCheckedOut;
	    this.genre = genre;
	    this.publishDate = publishDate;
	    this.isReservable = isReservable;
	    this.itemNumber = itemNumber;
	}
	
	public String getItemNumber() {
		return this.itemNumber; 
	}
	
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber; 
	}
}
