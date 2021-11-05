package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Date;

import ca.mcgill.ecse321.librarysystem.model.Item;

public abstract class ItemDto {
	protected String itemTitle;
	protected String description;
	protected String imageUrl;
	protected String itemNumber;
	protected String genre;
	protected Date publishDate;
	protected boolean isReservable;
	protected String currentReservationId;
	protected Item.Type type;
	
	
	public String getItemNumber() {
		return this.itemNumber; 
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber; 
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
		  }  public String getCurrentReservationId() {
			    return this.currentReservationId;
		  }
		  public Item.Type getType() {
			    return this.type;
			  }
}
