package com.restaurant.userservice.model;

import lombok.Data;

@Data
public class ResetPwdObject {
	private String email;
	private String new_pwd;
}
