package com.restaurant.feedbackservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.restaurant.feedbackservice.model.CustomErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	CustomErrorResponse error;
	@ExceptionHandler(value = {FeedbackNotAvailableException.class})
	public ResponseEntity<Object> exception(FeedbackNotAvailableException exception){
		error = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.NOT_FOUND);
	}

}
