package com.restaurant.userservice.service;

import org.springframework.http.ResponseEntity;

import com.restaurant.userservice.exception.CouponCodeAlreadyExists;
import com.restaurant.userservice.exception.CouponCodeNotFoundException;
import com.restaurant.userservice.exception.MinimumAmountNotReachedException;
import com.restaurant.userservice.exception.NoCouponsAvailableException;
import com.restaurant.userservice.model.CouponData;
import com.restaurant.userservice.model.CouponDetails;

public interface CouponService {

	ResponseEntity<Object> addNewCoupon(CouponDetails coupon) throws CouponCodeAlreadyExists;
	ResponseEntity<Object> getAllCoupon() throws NoCouponsAvailableException;
	ResponseEntity<Object> getCouponByCouponCode(CouponData couponData) throws CouponCodeNotFoundException, MinimumAmountNotReachedException;
	ResponseEntity<Object> deleteCoupon(String couponCode) throws CouponCodeNotFoundException;
	ResponseEntity<Object> updateCoupon(CouponDetails coupon) throws CouponCodeNotFoundException;

}
