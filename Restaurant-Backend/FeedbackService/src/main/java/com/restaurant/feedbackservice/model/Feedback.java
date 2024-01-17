package com.restaurant.feedbackservice.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "feedback")
public class Feedback {
	@Id
	private UUID id;
	private String name;
	private String email;
	private long phoneNo;
	private int restaurantRating;
	private String message;
	private boolean active;
}
