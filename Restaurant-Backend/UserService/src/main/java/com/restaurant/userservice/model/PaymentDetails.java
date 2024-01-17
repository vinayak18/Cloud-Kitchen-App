package com.restaurant.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {
	private String name;
	private String email;
	private String currency;
	private String successUrl;
	private String cancelUrl;
	private String phoneNumber;
	private long amount;
	private long quantity;
}
