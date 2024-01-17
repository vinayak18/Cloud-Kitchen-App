package com.restaurant.userservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "coupon")
public class CouponDetails {
	@Id
	@Field("_id")
	private String couponCode;
	private double discountRate;
	private double minimumAmount;
	private List<String> userList = new ArrayList<String>();
	private boolean active;
}
