package com.restaurant.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.userservice.constants.UserServiceConstants;
import com.restaurant.userservice.exception.CouponCodeAlreadyExists;
import com.restaurant.userservice.exception.CouponCodeNotFoundException;
import com.restaurant.userservice.exception.MinimumAmountNotReachedException;
import com.restaurant.userservice.exception.NoCouponsAvailableException;
import com.restaurant.userservice.exception.UserAlreadyExistsException;
import com.restaurant.userservice.model.CouponData;
import com.restaurant.userservice.model.CouponDetails;
import com.restaurant.userservice.model.UserDetails;
import com.restaurant.userservice.service.CouponService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CouponController {
	
	@Autowired
	private CouponService couponService;
	
	@PostMapping(value = UserServiceConstants.REGISTERNEWCOUPON_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addNewCoupon(@RequestBody CouponDetails coupon) throws CouponCodeAlreadyExists{
        return couponService.addNewCoupon(coupon);
	}
	
	@PutMapping(value = UserServiceConstants.UPDATECOUPONDETAILS_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateCoupon(@RequestBody CouponDetails coupon) throws CouponCodeNotFoundException{
        return couponService.updateCoupon(coupon);
	}
	
	@DeleteMapping(value = UserServiceConstants.DELETECOUPON_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteCoupon(@PathVariable("code") String couponCode) throws CouponCodeNotFoundException{
        return couponService.deleteCoupon(couponCode);
	}
	
	@PostMapping(value = UserServiceConstants.RETRIEVECOUPONBYCOUPONCODE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getCouponByCouponCode(@RequestBody CouponData couponData) throws CouponCodeNotFoundException, MinimumAmountNotReachedException {
        return couponService.getCouponByCouponCode(couponData);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEALLCOUPON_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllCoupon() throws NoCouponsAvailableException{
        return couponService.getAllCoupon();
	}

}
