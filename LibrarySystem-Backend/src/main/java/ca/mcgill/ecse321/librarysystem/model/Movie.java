package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("movie")
public class Movie extends Item {

  private String productionCompany;
  private String movieCast;
  private String director;
  private String producer;

  public String getProductionCompany() {
    return this.productionCompany;
  }

  public void setProductionCompany(String productionCompany) {
    this.productionCompany = productionCompany;
  }

  public String getCast() {
    return this.movieCast;
  }

  public void setMovieCast(String movieCast) {
    this.movieCast = movieCast;
  }

  public String getDirector() {
    return this.director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public String getProducer() {
    return this.producer;
  }

  public void setProducer(String producer) {
    this.producer = producer;
  }
}
