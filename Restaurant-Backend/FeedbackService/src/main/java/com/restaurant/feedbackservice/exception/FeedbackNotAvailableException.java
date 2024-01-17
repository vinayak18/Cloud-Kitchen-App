package com.restaurant.feedbackservice.exception;

public class FeedbackNotAvailableException extends Exception {
	private String message;
	public FeedbackNotAvailableException(String message){
		super(message);
		this.message =  message;
	}
}
