package com.restaurant.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.restaurant.userservice.model.UserDetails;

@FeignClient(name = "ProductService", url = "${review.service.URL}")
public interface ReviewFeign {
	@PostMapping(value = "/api/v1/productservice/verify/user/update/image", produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateUserImages(@RequestBody UserDetails user);
}
