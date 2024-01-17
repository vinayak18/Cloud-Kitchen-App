package com.restaurant.productservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "UserService", url = "${user.auth.URL}")
public interface UserAuthFeign {
	@GetMapping(value = "/api/v1/userservice/validate/user")
	public void validateUser(@RequestHeader("Authorization") String token);
}
