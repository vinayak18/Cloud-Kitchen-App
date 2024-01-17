package com.restaurant.productservice.service;

import java.io.IOException;
import java.util.zip.DataFormatException;

import org.springframework.http.ResponseEntity;

import com.restaurant.productservice.exception.ReviewNotFoundException;
import com.restaurant.productservice.model.Review;
import com.restaurant.productservice.model.UserDetails;
import com.restaurant.productservice.model.UserReview;

public interface ReviewService {
	
	public ResponseEntity<Object> getReviewByPID(long pid) throws ReviewNotFoundException, DataFormatException, IOException;
	public ResponseEntity<Object> addReview(long pId, UserReview userReview);
	public ResponseEntity<Object> updateReview(long pId, UserReview userReview) throws ReviewNotFoundException;
	public ResponseEntity<Object> deleteReview(long pId, UserReview userReview) throws ReviewNotFoundException;
	public String updateUserImages(UserDetails user);
}
