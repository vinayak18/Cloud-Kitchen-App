package com.restaurant.feedbackservice.service;

import org.springframework.http.ResponseEntity;

import com.restaurant.feedbackservice.exception.FeedbackNotAvailableException;
import com.restaurant.feedbackservice.model.Feedback;

public interface FeedbackService {

	public ResponseEntity<Object> getAllFeedback() throws FeedbackNotAvailableException;

	public ResponseEntity<Object> addNewFeedback(Feedback feedback);

	public ResponseEntity<Object> getTopCustomersTestimony();

}
