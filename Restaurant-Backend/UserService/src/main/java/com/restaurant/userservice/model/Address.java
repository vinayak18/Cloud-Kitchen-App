package com.restaurant.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	private String streetAddress;
	private String flatNo;
	private String landmark;
	private String pincode;
	private String state;
	private String city;
	private boolean active;
}
