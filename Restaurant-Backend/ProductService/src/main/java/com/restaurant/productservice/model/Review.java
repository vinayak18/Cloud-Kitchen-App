package com.restaurant.productservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "review")
public class Review {
	@Transient
    public static final String SEQUENCE_NAME = "review_sequence";
	
	@Id
	private long reviewId;
	private long pid;
	private int totalRating;
	private int noOfRating;
	private List<UserReview> userReview = new ArrayList<UserReview>();
	
}
