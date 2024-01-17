package com.restaurant.userservice.model;

public enum OrderStatusEnum {
	DELIVERED("DELIVERED"),
	PREPARING("PREPARING"),
	PLACED("PLACED"),
	ACCEPTED("ACCEPTED"),
	DISPACHED("DISPACHED"),
	CANCELLED("CANCELLED"),
	DECLINED("DECLINED"),
	DEFAULT("DEFAULT");
	
	private String value;
	
	OrderStatusEnum(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(value);
	}
}
