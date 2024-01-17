package com.restaurant.messagingservice.exception;

public class EmailConfigException extends Exception {
	private String message;
	public EmailConfigException(String message){
		super(message);
		this.message =  message;
	}
}
