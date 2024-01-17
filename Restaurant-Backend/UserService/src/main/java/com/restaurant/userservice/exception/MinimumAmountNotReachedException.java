package com.restaurant.userservice.exception;

public class MinimumAmountNotReachedException extends Exception {
	private String message;
	public MinimumAmountNotReachedException(String message){
		super(message);
		this.message =  message;
	}
}
