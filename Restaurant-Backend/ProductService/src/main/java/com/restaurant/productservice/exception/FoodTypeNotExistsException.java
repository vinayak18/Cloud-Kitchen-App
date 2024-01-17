package com.restaurant.productservice.exception;

public class FoodTypeNotExistsException extends Exception {
	private String message;
	public FoodTypeNotExistsException(String message){
		super(message);
		this.message =  message;
	}
}
