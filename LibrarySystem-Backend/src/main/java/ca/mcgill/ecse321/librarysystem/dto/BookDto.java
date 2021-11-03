package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Date;

public class BookDto {
	private String itemTitle;
	private String description;
	private String imageUrl;
	private String itemNumber;
	private String genre;
	private Date publishDate;
	private boolean isReservable;
	private boolean isCheckedOut;
	private String author;
	private String publisher;
	
	public BookDto() {
	}
	
	public BookDto(String itemNumber) {
		this( "itemTitle",
			     "description",
			    "imageURL",
			    "publisher",
			    "author",
			    false,
			    "genre",
			    new Date(0),
			    true,
			    itemNumber); 
	}
	
	public BookDto(String itemTitle,
		    String description,
		    String imageURL,
		    String publisher,
		    String author,
		    boolean isCheckedOut,
		    String genre,
		    Date publishDate,
		    boolean isReservable,
		    String itemNumber) {
	    this.itemTitle =  itemTitle;
	    this.description = description;
	    this.imageUrl = imageURL;
	    this.publisher = publisher;
	    this.author = author;
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
	public String getAuthor() {
		return this.author; 
	}
	
	public String getPublisher() {
		    return this.publisher;
	 }
	public String getItemTitle() {
		    return this.itemTitle;
	}
	public String getDescription() {
		    return this.description;
	}
	  public String getImageUrl() {
		    return this.imageUrl;
		  }
	  public String getGenre() {
		    return this.genre;
		  } public Date getPublishDate() {
			    return this.publishDate;
		  }  public boolean getIsReservable() {
			    return this.isReservable;
		  }  public boolean getIsCheckedOut() {
			    return this.isCheckedOut;
		  }
}
