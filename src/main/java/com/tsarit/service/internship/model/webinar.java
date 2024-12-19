package com.tsarit.service.internship.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class webinar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false,unique =  true)
  private String email;
  @Column(nullable = false)
  private String phone;
  @Column(nullable = false)
  private String course;
  private String orderId;
  private Long amount;
  
  private String date;
  

public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getOrderId() {
	return orderId;
}
public void setOrderId(String orderId) {
	this.orderId = orderId;
}
public String getCourse() {
	return course;
}
public void setCourse(String course) {
	this.course = course;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}

public Long getAmount() {
	return amount;
}
public void setAmount(Long amount) {
	this.amount = amount;
}
 
}
