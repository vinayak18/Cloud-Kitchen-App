package com.restaurant.messagingservice.exception;

public class InvalidOTPException extends Exception {
	private String message;
	public InvalidOTPException(String message){
		super(message);
		this.message =  message;
	}
}
