package com.restaurant.productservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.restaurant.productservice.exception.FoodTypeNotExistsException;
import com.restaurant.productservice.exception.ProductNotFoundException;
import com.restaurant.productservice.model.CartItemsInfo;
import com.restaurant.productservice.model.Product;

public interface ProductService {
	public ResponseEntity<Object> getAllProduct();
	public ResponseEntity<Object> getProductById(long pId) throws ProductNotFoundException;
	public ResponseEntity<Object> getProductByFoodType(String type) throws FoodTypeNotExistsException;
	public ResponseEntity<Object> getBestsellerProductByFoodType(long pId) throws ProductNotFoundException;
	public ResponseEntity<Object> addProduct(Product product);
	public ResponseEntity<Object> updateProduct(Product product) throws ProductNotFoundException;
	public ResponseEntity<Object> deleteProduct(long pId) throws ProductNotFoundException;
	public ResponseEntity<Object> updateLiveStatus(long pId) throws ProductNotFoundException;
	public ResponseEntity<Object> getMultiProductById(List<CartItemsInfo> multiproduct) throws ProductNotFoundException;
}
