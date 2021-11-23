package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.model.Item;
import java.sql.Date;

public class MusicAlbumDto extends ItemDto {

  private String artist;
  private String recordingLabel;

  public MusicAlbumDto(
    String itemTitle,
    String description,
    String imageURL,
    String genre,
    Date publishDate,
    boolean isReservable,
    String currentReservationId,
    String itemNumber,
    String artist,
    String recordingLabel
  ) {
    this.itemTitle = itemTitle;
    this.description = description;
    this.imageUrl = imageURL;
    this.genre = genre;
    this.publishDate = publishDate;
    this.isReservable = isReservable;
    this.itemNumber = itemNumber;
    this.currentReservationId = currentReservationId;
    this.type = Item.Type.MusicAlbum;
    this.artist = artist;
    this.recordingLabel = recordingLabel;
  }

  public String getRecordingLabel() {
    return this.recordingLabel;
  }

  public String getArtist() {
    return this.artist;
  }
}
