package com.restaurant.userservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "product")
public class Product {
	@Transient
    public static final String SEQUENCE_NAME = "product_sequence";
	
	@Id
	private long pid;
	private String name;
	private String desc;
	private double price;
	private int quantity;
	private double avgRating;
	private List<String> img_url = new ArrayList<String>();
	private FoodTypeEnum type;
	private String category;
	private boolean live;
	
}
