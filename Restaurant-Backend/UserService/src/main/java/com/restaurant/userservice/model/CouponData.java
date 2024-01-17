package com.restaurant.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponData {
	private String couponCode;
	private double discountAmount;
	private double totalAmount;
}
