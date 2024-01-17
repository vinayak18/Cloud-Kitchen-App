package com.restaurant.userservice.exception;

public class CouponCodeNotFoundException extends Exception {
	private String message;
	public CouponCodeNotFoundException(String message){
		super(message);
		this.message =  message;
	}
}
