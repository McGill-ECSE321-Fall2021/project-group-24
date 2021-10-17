package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("printedMedia")
public class PrintedMedia extends Item {

  private String issueNumber;

  public String getIssueNumber() {
    return this.issueNumber;
  }

  public void setIssueNumber(String issueNumber) {
    this.issueNumber = issueNumber;
  }
}
