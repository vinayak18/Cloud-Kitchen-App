package com.restaurant.feedbackservice.serviceimpl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.uuid.Generators;
import com.restaurant.feedbackservice.exception.FeedbackNotAvailableException;
import com.restaurant.feedbackservice.model.CustomSucccessResponse;
import com.restaurant.feedbackservice.model.Feedback;
import com.restaurant.feedbackservice.repository.FeedbackRepository;
import com.restaurant.feedbackservice.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Override
	public ResponseEntity<Object> getAllFeedback() throws FeedbackNotAvailableException {
		// TODO Auto-generated method stub
		List<Feedback> feedbackList = feedbackRepository.findAll();
		if (CollectionUtils.isEmpty(feedbackList)) {
			throw new FeedbackNotAvailableException("No Feedback Found.");
		}
		return new ResponseEntity<>(feedbackList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> addNewFeedback(Feedback feedback) {
		// TODO Auto-generated method stub
		feedback.setId(Generators.timeBasedGenerator().generate());
		feedbackRepository.save(feedback);
		CustomSucccessResponse response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Feedback Added Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getTopCustomersTestimony() {
		// TODO Auto-generated method stub
		List<Feedback> feedbackList = feedbackRepository.findAll();
		feedbackList = feedbackList.stream().filter(feedback-> feedback.isActive()).limit(3).collect(Collectors.toList());
		return new ResponseEntity<>(feedbackList, HttpStatus.OK);
	}

}
