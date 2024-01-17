package com.restaurant.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.userservice.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>{

}
