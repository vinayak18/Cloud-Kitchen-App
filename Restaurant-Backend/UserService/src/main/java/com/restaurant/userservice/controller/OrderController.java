package com.restaurant.userservice.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;
import com.restaurant.userservice.constants.UserServiceConstants;
import com.restaurant.userservice.exception.OrderCancelFailedException;
import com.restaurant.userservice.exception.OrderNotFoundException;
import com.restaurant.userservice.model.DistanceMatrix;
import com.restaurant.userservice.model.Order;
import com.restaurant.userservice.model.OrderResponse;
import com.restaurant.userservice.model.PaymentDetails;
import com.restaurant.userservice.service.OrderService;
import com.stripe.exception.StripeException;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Value("${stripe.apiKey}")
	private String stripeAPIKey;
	@GetMapping(value = UserServiceConstants.RETRIEVEACTIVEORDER_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getActiveOrders(@PathVariable("userId") String userId) {
		return orderService.getActiveOrders(userId);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEPASTORDER_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPastOrders(@PathVariable("userId") String userId) {
		return orderService.getPastOrders(userId);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEALLORDERSBYSTATUS_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllOrdersByStatus(@PathVariable String status) throws RazorpayException, UnsupportedEncodingException, IOException, InterruptedException {
		return orderService.getAllOrdersByStatus(status);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEORDERBYID_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getOrderById(@PathVariable("id") String orderId) throws OrderNotFoundException {
		return orderService.getOrderById(orderId);
	}
	
	@PutMapping(value = UserServiceConstants.UPDATEORDERSTATUS_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateOrderStatus(@PathVariable("id") String id, @PathVariable("status") String status) throws OrderNotFoundException, OrderCancelFailedException {
		return orderService.updateOrderStatus(id, status);
	}
	
	@PutMapping(value = UserServiceConstants.UPDATEORDERRATING_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateOrderRating(@PathVariable("id") String id, @PathVariable("rating") int rating) throws OrderNotFoundException {
		return orderService.updateOrderRating(id, rating);
	}
	@PostMapping(value = UserServiceConstants.REGISTERNEWORDER_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addNewOrder(@RequestBody Order order) {
		return orderService.addNewOrder(order);
	}

	@PostMapping(value = UserServiceConstants.INITIALIZESTRIPENEWORDER_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> initializeStripePaymentDetails(@RequestBody PaymentDetails payment) throws StripeException {
		return orderService.initializeStripePaymentDetails(payment);
	}
	
	@PostMapping(value = UserServiceConstants.INITIALIZERAZORPAYNEWORDER_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> initializeRazorPayPaymentDetails(@RequestBody PaymentDetails payment) throws RazorpayException {
		return orderService.initializeRazorPayPaymentDetails(payment);
	}
	
	@PostMapping(value = UserServiceConstants.CALCULATEDISTANCE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> calculateDistanceMatrix(@RequestBody DistanceMatrix matrixObject) throws RazorpayException, UnsupportedEncodingException, IOException, InterruptedException {
		return orderService.calculateDistanceMatrix(matrixObject);
	}
	
}
