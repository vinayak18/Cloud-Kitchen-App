package com.restaurant.userservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.userservice.model.UserDetails;

@Repository
public interface UserRepository extends MongoRepository<UserDetails, String> {
	public Optional<UserDetails> findByEmail(String email);
	public void deleteByEmail(String email);
}