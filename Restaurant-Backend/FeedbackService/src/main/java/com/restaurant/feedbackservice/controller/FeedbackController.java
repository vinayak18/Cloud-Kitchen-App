package com.restaurant.feedbackservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.feedbackservice.constants.FeedbackServiceConstants;
import com.restaurant.feedbackservice.exception.FeedbackNotAvailableException;
import com.restaurant.feedbackservice.model.Feedback;
import com.restaurant.feedbackservice.service.FeedbackService;

@RestController
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;
	
	@GetMapping(value = FeedbackServiceConstants.RETRIEVEALLFEEDBACK_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllFeedback() throws FeedbackNotAvailableException{
		return feedbackService.getAllFeedback();
	}
	
	@PostMapping(value = FeedbackServiceConstants.ADDNEWFEEDBACK_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addNewFeedback(@RequestBody Feedback feedback){
		return feedbackService.addNewFeedback(feedback);
	}
	
	@GetMapping(value = FeedbackServiceConstants.RETRIEVECUSTOMERTESTIMONY_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getCustomerTestimony() throws FeedbackNotAvailableException{
		return feedbackService.getTopCustomersTestimony();
	}
	
}
