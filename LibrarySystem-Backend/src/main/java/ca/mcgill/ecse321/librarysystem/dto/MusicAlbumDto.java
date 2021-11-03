package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Date;

public class MusicAlbumDto {
	private String itemTitle;
	private String description;
	private String imageUrl;
	private String itemNumber;
	private String genre;
	private Date publishDate;
	private boolean isReservable;
	private boolean isCheckedOut;
	  private String artist;
	  private String recordingLabel;
	
	public MusicAlbumDto() {
	}
	
	public MusicAlbumDto(String itemNumber) {
		this( "itemTitle",
			     "description",
			    "imageURL",
			    false,
			    "genre",
			    new Date(0),
			    true,
			    itemNumber, "artist", "recording label"); 
	}
	
	public MusicAlbumDto(String itemTitle,
		    String description,
		    String imageURL,
		    boolean isCheckedOut,
		    String genre,
		    Date publishDate,
		    boolean isReservable,
		    String itemNumber, String artist, String recordingLabel) {
	    this.itemTitle =  itemTitle;
	    this.description = description;
	    this.imageUrl = imageURL;
	    this.isCheckedOut = isCheckedOut;
	    this.genre = genre;
	    this.publishDate = publishDate;
	    this.isReservable = isReservable;
	    this.itemNumber = itemNumber;
	    this.artist = artist;
	    this.recordingLabel = recordingLabel;
	}
	
	public String getItemNumber() {
		return this.itemNumber; 
	}
	
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber; 
	}
}
