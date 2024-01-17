package com.restaurant.productservice.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReview {
	
	private UUID userReviewId;
	private String userId;
	private String name;
	private String img_url;
	private BlobImage blobImage;
	private int userRating;
	private long dateOfReview;
	private String review;
	
}
