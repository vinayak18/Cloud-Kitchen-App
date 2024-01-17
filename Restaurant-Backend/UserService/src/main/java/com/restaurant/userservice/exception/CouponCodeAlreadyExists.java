package com.restaurant.userservice.exception;

public class CouponCodeAlreadyExists extends Exception {
	private String message;
	public CouponCodeAlreadyExists(String message){
		super(message);
		this.message =  message;
	}
}
