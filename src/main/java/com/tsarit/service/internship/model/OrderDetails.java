//package com.tsarit.service.internship.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//
//@Entity
//public class OrderDetails {
//
//	 @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    private Long id;
//
//	    private String orderId;
//	    private Long amount;
//	    
//	    
//	    private String paymentId;
//	    private String signature;
//
//	    public String getPaymentId() {
//			return paymentId;
//		}
//
//		public void setPaymentId(String paymentId) {
//			this.paymentId = paymentId;
//		}
//
//		public String getSignature() {
//			return signature;
//		}
//
//		public void setSignature(String signature) {
//			this.signature = signature;
//		}
//
//		@ManyToOne
//	    @JoinColumn(name = "internship_id")
//	    private internship internship;
//
//		public Long getId() {
//			return id;
//		}
//
//		public void setId(Long id) {
//			this.id = id;
//		}
//
//		public String getOrderId() {
//			return orderId;
//		}
//
//		public void setOrderId(String orderId) {
//			this.orderId = orderId;
//		}
//
//		public double getAmount() {
//			return amount;
//		}
//
//		public void setAmount(Long amount) {
//			this.amount = amount;
//		}
//
//		public internship getInternship() {
//			return internship;
//		}
//
//		public void setInternship(internship internship) {
//			this.internship = internship;
//		}
//}
