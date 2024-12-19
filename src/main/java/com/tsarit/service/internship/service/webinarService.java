package com.tsarit.service.internship.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tsarit.service.internship.model.internship;
import com.tsarit.service.internship.model.webinar;
import com.tsarit.service.internship.repository.webinarRepository;

@Service
public class webinarService {

	@Autowired
	private webinarRepository webinarRepository;
	
	public webinar save(webinar webinar) {
		return webinarRepository.save(webinar);
	}
	public void updateAmountwebinar(int id,String orderId, Long amount,String data) {
        // Find the OrderDetails by orderId

	 webinar webinar = webinarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        // Update the amount
	 webinar.setAmount(amount);
	 webinar.setOrderId(orderId);
	 webinar.setDate(data);
        // Save updated OrderDetails to the database
	 webinarRepository.save(webinar);
    }
	
	public List<webinar> findemail(String email) {
		return webinarRepository.findByEmail(email);
	}
}
