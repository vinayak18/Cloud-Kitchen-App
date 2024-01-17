package com.restaurant.messagingservice.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.messagingservice.exception.EmailConfigException;
import com.restaurant.messagingservice.exception.InvalidOTPException;
import com.restaurant.messagingservice.feign.UserAuthFeign;
import com.restaurant.messagingservice.model.MessageObject;
import com.restaurant.messagingservice.service.MessagingService;

@RestController
public class MessagingController {
	
	@Autowired
	private MessagingService messagingService;
	
	@Autowired
	private UserAuthFeign userAuthFeign;
	
	@GetMapping(value = "/api/v1/messagingservice/auth/user/validate/{email}/otp/{otp}")
    public ResponseEntity<Object> validateOTP(@PathVariable String email, @PathVariable String otp) throws InvalidOTPException {
		return messagingService.verifyOTP(email, otp);
    }
	
	@PostMapping(value = "/api/v1/messagingservice/auth/user/forgetpassword")
    public ResponseEntity<Object> forgetPassword(@RequestBody MessageObject messageObject) throws EmailConfigException {
		String phoneNo = userAuthFeign.isUserExists(messageObject.getEmail());
		System.out.println("phone no - " + phoneNo);
//		if(!phoneNo.equals(messageObject.getPhoneNo())) {
//			throw new EmailConfigException("Invalid phone number");
//		}
		messageObject.setPhoneNo(phoneNo);
		ResponseEntity<Object> response;
		Random rand = new Random();
        int otp = rand.nextInt(100000,999999);
        System.out.println(otp);
        response = messagingService.sendEmail(messageObject.getEmail(), otp);
//        if (null != messageObject.getPhoneNo() && "0" != messageObject.getPhoneNo()) {
//        	response = messagingService.sendSMS(messageObject.getPhoneNo(), otp);
//		}
		return response;
    }


}
