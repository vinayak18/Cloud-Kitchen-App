package com.restaurant.productservice.controller;

import java.io.IOException;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.productservice.constants.ProductServiceConstants;
import com.restaurant.productservice.exception.ReviewNotFoundException;
import com.restaurant.productservice.feign.UserAuthFeign;
import com.restaurant.productservice.model.UserDetails;
import com.restaurant.productservice.model.UserReview;
import com.restaurant.productservice.service.ReviewService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private UserAuthFeign userAuthFeign;

	@GetMapping(value = ProductServiceConstants.RETRIEVEREVIEWBYPID_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getReviewByPID(@PathVariable("pId") long pId) throws ReviewNotFoundException, DataFormatException, IOException{
		log.info("Retrieve Review by PID - inside getReviewByPID() controller method");
		log.info("Retrieve Product by PID - Product Id: "+pId);
		return reviewService.getReviewByPID(pId);
	}
	//Need to add authentication check for the below services
	@PostMapping(value = ProductServiceConstants.ADDREVIEW_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addReview(@RequestHeader("Authorization") String token, @PathVariable("pId") long pId, @RequestBody UserReview userReview){
		log.info("Add New Review to a Product - inside addReview() controller method");
		log.info("Add New Review to a Product - Token Validation is In-progress");
		userAuthFeign.validateUser(token);
		log.info("Add New Review to a Product - Token Validation Successful");
		log.info("Add New Review to a Product - Product Id : "+pId);
		log.info("Add New Review to a Product - User Review Details : "+userReview);
		return reviewService.addReview(pId, userReview);
	}
	
	@PutMapping(value = ProductServiceConstants.UPDATEREVIEW_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateReview(@RequestHeader("Authorization") String token, @PathVariable("pId") long pId, @RequestBody UserReview userReview) throws ReviewNotFoundException{
		log.info("Update Exisiting Review of a Product - inside updateReview() controller method");
		log.info("Update Exisiting Review of a Product - Token Validation is In-progress");
		userAuthFeign.validateUser(token);
		log.info("Update Exisiting Review of a Product - Token Validation Successful");
		log.info("Update Exisiting Review of a Product - Product Id : "+pId);
		log.info("Update Exisiting Review of a Product - User Review Details : "+userReview);
		return reviewService.updateReview(pId, userReview);
	}
	
	@DeleteMapping(value = ProductServiceConstants.DELETEREVIEW_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteReview(@RequestHeader("Authorization") String token, @PathVariable("pId") long pId, @RequestBody UserReview userReview) throws ReviewNotFoundException{
		log.info("Delete Exisiting Review of a Product - inside deleteReview() controller method");
		log.info("Delete Exisiting Review of a Product - Token Validation is In-progress");
		userAuthFeign.validateUser(token);
		log.info("Delete Exisiting Review of a Product - Token Validation Successful");
		log.info("Delete Exisiting Review of a Product - Product Id : "+pId);
		log.info("Delete Exisiting Review of a Product - User Review Details : "+userReview);
		return reviewService.deleteReview(pId, userReview);
	}
	
	@PostMapping(value = ProductServiceConstants.UPDATEUSERIMAGES_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateUserImages(@RequestBody UserDetails user){
		log.info("Update User Images in Existing Reviews - updateUserImages() controller method");
		log.info("Update User Images in Existing Reviews - User Details : "+user.toString());
		return reviewService.updateUserImages(user);
	}
	
}
