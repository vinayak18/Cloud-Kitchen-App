package com.restaurant.productservice.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.uuid.Generators;
import com.restaurant.productservice.exception.ReviewNotFoundException;
import com.restaurant.productservice.model.BlobImage;
import com.restaurant.productservice.model.CustomSucccessResponse;
import com.restaurant.productservice.model.Review;
import com.restaurant.productservice.model.UserDetails;
import com.restaurant.productservice.model.UserReview;
import com.restaurant.productservice.repository.ReviewRepository;
import com.restaurant.productservice.service.ReviewService;
import com.restaurant.productservice.utilty.SequenceGeneratorService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	CustomSucccessResponse response;

	@Override
	public ResponseEntity<Object> getReviewByPID(long pid) throws ReviewNotFoundException, DataFormatException, IOException {
		Review review = reviewRepository.findByPid(pid)
				.orElseThrow(() -> new ReviewNotFoundException("Review not found!"));
		for (UserReview userReview : review.getUserReview()) {
			if (null != userReview.getBlobImage()) {
				BlobImage image = userReview.getBlobImage();
				image.setPicByte(decompressBytes(image.getPicByte()));
				userReview.setBlobImage(image);
			}
		}
		return new ResponseEntity<>(review, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> addReview(long pId, UserReview userReview) {
		Optional<Review> optionalReview = reviewRepository.findByPid(pId);
		Review review;
		if (optionalReview.isEmpty()) {
			review = new Review();
			review.setPid(pId);
			review.setReviewId(sequenceGeneratorService.generateSequence(Review.SEQUENCE_NAME));
		} else {
			review = optionalReview.get();
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		userReview.setDateOfReview(timestamp.getTime());
		userReview.setUserReviewId(Generators.timeBasedGenerator().generate());
		review.setNoOfRating(review.getNoOfRating() + 1);
		review.setTotalRating(review.getTotalRating() + userReview.getUserRating());
		review.getUserReview().add(userReview);
		reviewRepository.save(review);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(),
				"Review Added Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> updateReview(long pId, UserReview userReview) throws ReviewNotFoundException {
		Optional<Review> oldReview = reviewRepository.findByPid(pId);
		if (oldReview.isEmpty()) {
			throw new ReviewNotFoundException("Review not found!");
		}
		Review review = oldReview.get();
		review.getUserReview().removeIf(usrReview -> usrReview.getUserReviewId().equals(userReview.getUserReviewId()));
		review.getUserReview().add(userReview);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		userReview.setDateOfReview(timestamp.getTime());
		reviewRepository.save(review);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(),
				"Review Updated Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> deleteReview(long pId, UserReview userReview) throws ReviewNotFoundException {
		Optional<Review> optionalReview = reviewRepository.findByPid(pId);
		if (optionalReview.isEmpty()) {
			throw new ReviewNotFoundException("Review not found!");
		}
		Review review = optionalReview.get();
		List<UserReview> userReviews = review.getUserReview();
		if (userReviews.indexOf(userReview) != -1) {
			userReviews.remove(userReview);
			review.setUserReview(userReviews);
			review.setNoOfRating(review.getNoOfRating() - 1);
			review.setTotalRating(review.getTotalRating() - userReview.getUserRating());
		} else {
			throw new ReviewNotFoundException("Review not found!");
		}
		reviewRepository.save(review);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(),
				"Review Deleted Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) throws DataFormatException, IOException {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!inflater.finished()) {
			int count = inflater.inflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		return outputStream.toByteArray();
	}

	@Override
	public String updateUserImages(UserDetails user) {
		// TODO Auto-generated method stub
		List<Review> reviewList = reviewRepository.findAll();
		for (Review review : reviewList) {
			for (UserReview userReview : review.getUserReview()) {
				if (null != user && null != user.getBlobImage() && user.getUserId().equals(userReview.getUserId())) {
					userReview.setBlobImage(user.getBlobImage());
					userReview.setImg_url(user.getImg_url());
				}
			}
		}
		reviewRepository.saveAll(reviewList);
		return "User Image Updated Successfully";
	}

}
