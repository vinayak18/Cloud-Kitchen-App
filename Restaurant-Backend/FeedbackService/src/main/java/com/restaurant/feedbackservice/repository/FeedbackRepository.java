package com.restaurant.feedbackservice.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.feedbackservice.model.Feedback;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, UUID> {

}
