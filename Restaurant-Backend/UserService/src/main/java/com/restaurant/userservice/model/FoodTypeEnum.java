package com.restaurant.userservice.model;

public enum FoodTypeEnum {
	BREAKFAST("BREAKFAST"),
	LUNCH("LUNCH"),
	DINNER("DINNER"),
	SPECIAL_DISH("SPECIAL_DISH"),
	DEFAULT("DEFAULT");
	
	private String value;

	FoodTypeEnum(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(value);
	}
	
}
