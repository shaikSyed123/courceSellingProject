package com.tsarit.service.internship.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.tsarit.service.internship.model.webinar;

	@Repository
	@EnableJpaRepositories
	public interface webinarRepository extends JpaRepository<webinar, Integer>{
	
		
		List<webinar> findByEmail(String email);
	}
