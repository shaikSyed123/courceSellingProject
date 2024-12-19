package com.tsarit.service.internship.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class internship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
	@Column(nullable = false)
  private String firstname;
	@Column(nullable = false)
  private String lastname;
	@Column(nullable = false,unique = true)
  private String email;
	@Column(nullable = false)
  private Long phone;
	@Column(nullable = false)
  private String address;
	@Column(nullable = false)
  private String course;
	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-M-d")
  private LocalDate date_of_birth;
	@Column(nullable = false)
  private String gender;
//	@Column(nullable = false)
  private Long amount;
  
  private String orderId; 
public String getOrderId() {
	return orderId;
}
public void setOrderId(String orderId) {
	this.orderId = orderId;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getFirstname() {
	return firstname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}
public String getLastname() {
	return lastname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public Long getPhone() {
	return phone;
}
public void setPhone(Long phone) {
	this.phone = phone;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getCourse() {
	return course;
}
public void setCourse(String course) {
	this.course = course;
}
public LocalDate getDate_of_birth() {
	return date_of_birth;
}
public void setDate_of_birth(LocalDate date_of_birth) {
	this.date_of_birth = date_of_birth;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public Long getAmount() {
	return amount;
}
public void setAmount(Long amount) {
	this.amount = amount;
}
  
}
