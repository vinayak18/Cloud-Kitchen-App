package com.restaurant.userservice.exception;

public class UserAlreadyExistsException extends Exception {
	private String message;
	public UserAlreadyExistsException(String message){
		super(message);
		this.message =  message;
	}
}
