package com.restaurant.userservice.exception;

public class OrderNotFoundException extends Exception {
	private String message;
	public OrderNotFoundException(String message){
		super(message);
		this.message =  message;
	}
}
