package com.restaurant.messagingservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService", url = "${user.auth.URL}")
public interface UserAuthFeign {
	
	@GetMapping(value = "/api/v1/userservice/public/user/exists/{email}")
	public String isUserExists(@PathVariable("email") String email);
}
