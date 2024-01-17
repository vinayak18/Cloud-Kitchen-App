package com.restaurant.productservice.exception;

public class ReviewNotFoundException extends Exception {
	private String message;
	public ReviewNotFoundException(String message){
		super(message);
		this.message =  message;
	}
}
