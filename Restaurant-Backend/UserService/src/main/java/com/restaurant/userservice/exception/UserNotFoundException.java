package com.restaurant.userservice.exception;

public class UserNotFoundException extends Exception {
	private String message;
	public UserNotFoundException(String message){
		super(message);
		this.message =  message;
	}
}
