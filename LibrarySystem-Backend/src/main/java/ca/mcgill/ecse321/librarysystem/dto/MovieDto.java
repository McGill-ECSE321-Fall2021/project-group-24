package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Date;

public class MovieDto {
	private String itemTitle;
	private String description;
	private String imageUrl;
	private String itemNumber;
	private String genre;
	private Date publishDate;
	private boolean isReservable;
	private boolean isCheckedOut;
	private String productionCompany;
	private String movieCast;
	private String director;
	private String producer;
	
	public MovieDto() {
	}
	
	public MovieDto(String itemNumber) {
		this( "itemTitle",
			     "description",
			    "imageURL",
			    false,
			    "genre",
			    new Date(0),
			    true,
			    itemNumber,
			    "productionCompany","movieCast", "director", "producer"); 
	}
	
	public MovieDto(String itemTitle,
		    String description,
		    String imageURL,
		    boolean isCheckedOut,
		    String genre,
		    Date publishDate,
		    boolean isReservable,
		    String itemNumber,   String productionCompany,
	   String movieCast,
	   String director,
	   String producer) {
	    this.itemTitle =  itemTitle;
	    this.description = description;
	    this.imageUrl = imageURL;
	    this.isCheckedOut = isCheckedOut;
	    this.genre = genre;
	    this.publishDate = publishDate;
	    this.isReservable = isReservable;
	    this.itemNumber = itemNumber;
	    this.productionCompany = productionCompany;
	    this.movieCast = movieCast;
	    this.director = director;
	    this.producer = producer;
	}
	
	public String getItemNumber() {
		return this.itemNumber; 
	}
	
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber; 
	}
}
