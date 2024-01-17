package com.restaurant.userservice.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.restaurant.userservice.exception.CouponCodeAlreadyExists;
import com.restaurant.userservice.exception.CouponCodeNotFoundException;
import com.restaurant.userservice.exception.MinimumAmountNotReachedException;
import com.restaurant.userservice.exception.NoCouponsAvailableException;
import com.restaurant.userservice.model.CouponData;
import com.restaurant.userservice.model.CouponDetails;
import com.restaurant.userservice.model.CustomSucccessResponse;
import com.restaurant.userservice.repository.CouponRepository;
import com.restaurant.userservice.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {
	
	@Autowired
	private CouponRepository couponRepository;
	
	CustomSucccessResponse response;

	@Override
	public ResponseEntity<Object> addNewCoupon(CouponDetails coupon) throws CouponCodeAlreadyExists {
		// TODO Auto-generated method stub
		Optional<CouponDetails> couponDetails = couponRepository.findById(coupon.getCouponCode());
		if (!couponDetails.isEmpty()) {
			throw new CouponCodeAlreadyExists("Coupon Code Already Exists.");
		}
		couponRepository.save(coupon);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "New Coupon Added Successfully.");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getAllCoupon() throws NoCouponsAvailableException {
		// TODO Auto-generated method stub
		List<CouponDetails> couponsList = couponRepository.findAll();
		if(CollectionUtils.isEmpty(couponsList)) {
			throw new NoCouponsAvailableException("No coupons are available right now.");
		}
		return new ResponseEntity<>(couponsList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getCouponByCouponCode(CouponData couponData) throws CouponCodeNotFoundException, MinimumAmountNotReachedException {
		// TODO Auto-generated method stub
		CouponDetails coupon = couponRepository.findById(couponData.getCouponCode()).orElseThrow(()-> new CouponCodeNotFoundException("Coupon Code Not Found."));
		if (null!=couponData && couponData.getTotalAmount()>=coupon.getMinimumAmount()) {
			double discountAmount = couponData.getTotalAmount() * coupon.getDiscountRate()/100;
			couponData.setDiscountAmount(discountAmount);
			return new ResponseEntity<>(couponData, HttpStatus.OK);
		}
		else {
			throw new MinimumAmountNotReachedException("Minimum amount should be Rs. "+coupon.getMinimumAmount());
		}
	}

	@Override
	public ResponseEntity<Object> deleteCoupon(String couponCode) throws CouponCodeNotFoundException {
		// TODO Auto-generated method stub
		CouponDetails coupon = couponRepository.findById(couponCode).orElseThrow(()-> new CouponCodeNotFoundException("Coupon Code Not Found."));
		couponRepository.deleteById(couponCode);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Coupon Deleted Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> updateCoupon(CouponDetails coupon) throws CouponCodeNotFoundException {
		// TODO Auto-generated method stub
		CouponDetails existingCoupon = couponRepository.findById(coupon.getCouponCode()).orElseThrow(()-> new CouponCodeNotFoundException("Coupon Code Not Found."));
		couponRepository.save(coupon);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Coupon Details Updated Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
