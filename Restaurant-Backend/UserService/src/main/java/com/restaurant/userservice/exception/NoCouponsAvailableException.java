package com.restaurant.userservice.exception;

public class NoCouponsAvailableException extends Exception {
	private String message;
	public NoCouponsAvailableException(String message){
		super(message);
		this.message =  message;
	}
}
