package com.restaurant.userservice.exception;

public class OrderCancelFailedException extends Exception {
	private String message;
	public OrderCancelFailedException(String message){
		super(message);
		this.message =  message;
	}
}
