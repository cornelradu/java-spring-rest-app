package com.example.entex;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity // This tells Hibernate to make a table out of this class
public class User {
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer user_id;

  private String name;

  @Column(unique=true)
  private String email;

  private String locality_name;

  private String county_name;

  private String password;

  public Integer getUserId() {
    	return user_id;
  }

  public void setUserId(Integer id){
	this.user_id = id;
  }

  public String getName() {
    	return name;
  }

  public void setName(String name){
	this.name = name;
  }

  public String getEmail() {
    	return email;
  }

  public void setEmail(String email){
	this.email = email;
  }

  public String getLocalityName() {
    	return locality_name;
  }

  public void setLocalityName(String locality_name){
	this.locality_name = locality_name;
  }

  public String getCountyName(){
	return county_name;
  }

  public void setCountyName(String county_name){
	this.county_name = county_name;
  }

  public String getPassword(){
	return password;
  }

  public void setPassword(String password){
	this.password = password;
  }

}
