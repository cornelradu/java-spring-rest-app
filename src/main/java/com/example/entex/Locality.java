package com.example.entex;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Locality {
  
  @Id
  private Integer localityId;

  private String localityName;

  private String countyCode;

  public Integer getLocalityId() {
    return localityId;
  }

  public void setLocalityId(Integer id) {
    this.localityId = id;
  }

  public String getLocalityName() {
    return localityName;
  }

  public void setLocalityName(String localityName) {
    this.localityName = localityName;
  }

  public String getCountyCode() {
    return countyCode;
  }

  public void setCountyCode(String countyCode) {
    this.countyCode = countyCode;
  }
}
