package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Date;

public class PrintedMediaDto {
	private String itemTitle;
	private String description;
	private String imageUrl;
	private String itemNumber;
	private String genre;
	private Date publishDate;
	private boolean isReservable;
	private boolean isCheckedOut;
	  private String issueNumber;
	
	public PrintedMediaDto() {
	}
	
	public PrintedMediaDto(String itemNumber) {
		this( "itemTitle",
			     "description",
			    "imageURL",
			    false,
			    "genre",
			    new Date(0),
			    true,
			    itemNumber, "issueNumber"); 
	}
	
	public PrintedMediaDto(String itemTitle,
		    String description,
		    String imageURL,
		    boolean isCheckedOut,
		    String genre,
		    Date publishDate,
		    boolean isReservable,
		    String itemNumber,String issueNumber) {
	    this.itemTitle =  itemTitle;
	    this.description = description;
	    this.imageUrl = imageURL;
	    this.isCheckedOut = isCheckedOut;
	    this.genre = genre;
	    this.publishDate = publishDate;
	    this.isReservable = isReservable;
	    this.itemNumber = itemNumber;
	    this.issueNumber = issueNumber;
	}
	
	public String getItemNumber() {
		return this.itemNumber; 
	}
	
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber; 
	}
}
