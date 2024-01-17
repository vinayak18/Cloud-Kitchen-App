package com.restaurant.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.restaurant.productservice.model.CustomErrorResponse;

import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	CustomErrorResponse error;
	@ExceptionHandler(value = {ProductNotFoundException.class})
	public ResponseEntity<Object> exception(ProductNotFoundException exception){
		error = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {ReviewNotFoundException.class})
	public ResponseEntity<Object> exception(ReviewNotFoundException exception){
		error = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {FoodTypeNotExistsException.class})
	public ResponseEntity<Object> exception(FoodTypeNotExistsException exception){
		error = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignStatusException(FeignException exception, HttpServletResponse response) {
		error = new CustomErrorResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase(), exception.getMessage());
        return new ResponseEntity<Object>(error,HttpStatus.UNAUTHORIZED);
    }

}