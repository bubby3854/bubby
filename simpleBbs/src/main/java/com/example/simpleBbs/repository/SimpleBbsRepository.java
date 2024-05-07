package com.example.simpleBbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simpleBbs.entity.SimpleBbs;

public interface SimpleBbsRepository extends JpaRepository<SimpleBbs, Long> {
	
}
