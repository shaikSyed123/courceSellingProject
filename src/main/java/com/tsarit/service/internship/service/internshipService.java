package com.tsarit.service.internship.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsarit.service.internship.model.internship;
import com.tsarit.service.internship.model.webinar;
import com.tsarit.service.internship.repository.internshipRepository;
import com.tsarit.service.internship.repository.webinarRepository;

@Service
public class internshipService {
	
	@Autowired
	private  internshipRepository internshipRepository;
	

	public internship save(internship internship) {
		return internshipRepository.save(internship);
	}
	
  
	 public void updateAmount(int id,String orderId, Long amount) {
	        // Find the OrderDetails by orderId

		 internship internship = internshipRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Internship not found"));

	        // Update the amount
		 internship.setAmount(amount);
		 internship.setOrderId(orderId);

	        // Save updated OrderDetails to the database
		 internshipRepository.save(internship);
	    }
//	 public void saveOrderDetails(int internshipId, String orderId, Long amount,String paymentId,String signature) {
//	        // Find the internship by ID
//	        internship internship = internshipRepository.findById(internshipId)
//	                .orElseThrow(() -> new RuntimeException("Internship not found"));
//
//	        // Create a new OrderDetails object
//	        OrderDetails orderDetails = new OrderDetails();
//	        orderDetails.setInternship(internship);
//	        orderDetails.setOrderId(orderId);
//	        orderDetails.setAmount(amount);
//	        orderDetails.setPaymentId(paymentId);
//	        orderDetails.setSignature(signature);
//
//	        // Save OrderDetails to the database
//	        OrderDetailsRepository.save(orderDetails);
//	    }
	 
	 
	 
}
