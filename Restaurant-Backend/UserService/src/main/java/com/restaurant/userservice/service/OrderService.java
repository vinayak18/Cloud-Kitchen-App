package com.restaurant.userservice.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.http.ResponseEntity;
import com.razorpay.RazorpayException;
import com.restaurant.userservice.exception.OrderCancelFailedException;
import com.restaurant.userservice.exception.OrderNotFoundException;
import com.restaurant.userservice.model.DistanceMatrix;
import com.restaurant.userservice.model.Order;
import com.restaurant.userservice.model.PaymentDetails;
import com.stripe.exception.StripeException;

public interface OrderService {

	public ResponseEntity<Object> addNewOrder(Order order);

	public ResponseEntity<Object> initializeStripePaymentDetails(PaymentDetails payment) throws StripeException;

	public ResponseEntity<Object> initializeRazorPayPaymentDetails(PaymentDetails payment)throws RazorpayException;
	
	public ResponseEntity<Object> getActiveOrders(String userId);

	public ResponseEntity<Object> getPastOrders(String userId);

	public ResponseEntity<Object> updateOrderStatus(String orderId, String status) throws OrderNotFoundException, OrderCancelFailedException;

	public ResponseEntity<Object> updateOrderRating(String orderId, int rating) throws OrderNotFoundException;

	public ResponseEntity<Object> getOrderById(String orderId) throws OrderNotFoundException;

	public ResponseEntity<Object> calculateDistanceMatrix(DistanceMatrix matrixObject) throws UnsupportedEncodingException, IOException, InterruptedException;

	public ResponseEntity<Object> getAllOrdersByStatus(String status);

}
