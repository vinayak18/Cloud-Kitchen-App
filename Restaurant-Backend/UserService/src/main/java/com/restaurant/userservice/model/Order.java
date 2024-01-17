package com.restaurant.userservice.model;

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
@Document(collection = "order")
public class Order {
	@Id
	@Field("_id")
	private String orderId;
	private String userId;
	private long dateOfOrder;
	private List<Product> orderDetails;
	private double actualAmount;
	private double tax;
	private double deliveryFee;
	private CouponData coupon;
	private double netAmount;
	private String deliveryType;
	private CustomerInfo customerInfo;
	private OrderStatusEnum status;
	private String paymentType;
	private String paymentId;
	private int rating;
}
