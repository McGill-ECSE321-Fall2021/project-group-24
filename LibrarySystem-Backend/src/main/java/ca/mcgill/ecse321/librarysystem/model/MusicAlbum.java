package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("musicAlbum")
public class MusicAlbum extends Item {

  private String artist;
  private String recordingLabel;

  public String getArtist() {
    return this.artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getRecordingLabel() {
    return this.recordingLabel;
  }

  public void setRecordingLabel(String recordingLabel) {
    this.recordingLabel = recordingLabel;
  }
}
