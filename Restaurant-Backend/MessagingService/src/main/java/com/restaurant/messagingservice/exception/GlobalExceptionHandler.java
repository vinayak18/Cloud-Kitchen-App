package com.restaurant.messagingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.restaurant.messagingservice.model.CustomErrorResponse;

import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {
	CustomErrorResponse error;
	@ExceptionHandler(value = {InvalidOTPException.class})
	public ResponseEntity<Object> exception(InvalidOTPException exception){
		error = new CustomErrorResponse(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(value = {EmailConfigException.class})
	public ResponseEntity<Object> exception(EmailConfigException exception){
		error = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignStatusException(FeignException exception, HttpServletResponse response) {
		error = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception.getMessage());
        return new ResponseEntity<Object>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}