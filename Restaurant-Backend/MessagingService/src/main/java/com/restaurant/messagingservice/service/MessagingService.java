package com.restaurant.messagingservice.service;

import org.springframework.http.ResponseEntity;

import com.restaurant.messagingservice.exception.EmailConfigException;
import com.restaurant.messagingservice.exception.InvalidOTPException;

public interface MessagingService {

	public ResponseEntity<Object> sendSMS(String phoneNo, int otp);

	public ResponseEntity<Object> sendEmail(String email, int otp) throws EmailConfigException;

	public ResponseEntity<Object> verifyOTP(String email, String otp) throws InvalidOTPException;

}
