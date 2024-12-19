package com.tsarit.service.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsarit.service.internship.model.internship;

@Repository
public interface internshipRepository extends JpaRepository<internship, Integer> {

}
