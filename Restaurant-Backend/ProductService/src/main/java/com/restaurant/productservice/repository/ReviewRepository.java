package com.restaurant.productservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.productservice.model.Review;

@Repository
public interface ReviewRepository extends MongoRepository<Review, Long> {
	
	public Optional<Review> findByPid(long pid);
	
}