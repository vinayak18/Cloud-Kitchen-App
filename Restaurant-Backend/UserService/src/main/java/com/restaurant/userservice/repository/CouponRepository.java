package com.restaurant.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.userservice.model.CouponDetails;

@Repository
public interface CouponRepository extends MongoRepository<CouponDetails, String> {

}