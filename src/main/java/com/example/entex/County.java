package com.example.entex;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class County {
  
  @Id
  private Integer countyId;

  private String countyName;

  private String countyCode;

  public Integer getCountyId() {
    return countyId;
  }

  public void setCountyId(Integer id) {
    this.countyId = id;
  }

  public String getCountyName() {
    return countyName;
  }

  public void setCountyName(String countyName) {
    this.countyName = countyName;
  }

  public String getCountyCode() {
    return countyCode;
  }

  public void setCountyCode(String countyCode) {
    this.countyCode = countyCode;
  }
}
