package com.restaurant.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerInfo {
	
	private String name;
	private String email;
	private long phoneNo;
	private Address deliveryAddress;
}
