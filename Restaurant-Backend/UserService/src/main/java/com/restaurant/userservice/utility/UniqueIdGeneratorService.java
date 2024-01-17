package com.restaurant.userservice.utility;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restaurant.userservice.model.Order;
import com.restaurant.userservice.model.UserDetails;
import com.restaurant.userservice.repository.OrderRepository;
import com.restaurant.userservice.repository.UserRepository;

@Component
public class UniqueIdGeneratorService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	public String generateUniqueUsedId(String firstName) {
		Random rand = new Random();
        int randomNumber = rand.nextInt(1000);
		String username = firstName + randomNumber;
		Optional<UserDetails> user = userRepository.findById(username);
		if(!user.isEmpty()) {
			return generateUniqueUsedId(firstName);
		}
		return username;
	}
	
	public String generateUniqueOrderId(long orderSize) {
		Random rand = new Random();
        int randomNumber = rand.nextInt(100000,999999);
		String orderId = (orderSize + 1 + "" + randomNumber).substring(0,6);
		Optional<Order> order = orderRepository.findById(orderId);
		if(!order.isEmpty()) {
			return generateUniqueOrderId(orderSize);
		}
		return orderId;
	}
}
