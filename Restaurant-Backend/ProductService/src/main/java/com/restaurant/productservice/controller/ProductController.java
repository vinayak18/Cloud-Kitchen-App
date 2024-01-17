package com.restaurant.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.productservice.constants.ProductServiceConstants;
import com.restaurant.productservice.exception.FoodTypeNotExistsException;
import com.restaurant.productservice.exception.ProductNotFoundException;
import com.restaurant.productservice.feign.UserAuthFeign;
import com.restaurant.productservice.model.CartItemsInfo;
import com.restaurant.productservice.model.Product;
import com.restaurant.productservice.service.ProductService;
import com.restaurant.productservice.utilty.SequenceGeneratorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserAuthFeign userAuthFeign;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping(value = ProductServiceConstants.RETRIEVEALLPRODUCT_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllProducts(){
		log.info("Retrieve All Products - inside getAllProducts() controller method");
		return productService.getAllProduct();
	}
	
	@GetMapping(value = ProductServiceConstants.RETRIEVEPRODUCTBYPID_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProductById(@PathVariable long pId) throws ProductNotFoundException{
		log.info("Retrieve Product by PID - inside getProductById() controller method");
		log.info("Retrieve Product by PID - Product ID: "+pId);
		return productService.getProductById(pId);
	}
	
	@GetMapping(value = ProductServiceConstants.RETRIEVEPRODUCTBYFOODTYPE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getProductByFoodType(@PathVariable("foodType") String type) throws FoodTypeNotExistsException{
		log.info("Retrieve Product by Foodtype - inside getProductByFoodType() controller method");
		log.info("Retrieve Product by Foodtype - FoodType: "+type);
		return productService.getProductByFoodType(type);
	}
	
	@GetMapping(value = ProductServiceConstants.RETRIEVEBESTSELLERPRODUCTBYFOODTYPE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getBestsellerProductByFoodType(@PathVariable("pId") long pId) throws ProductNotFoundException{
		log.info("Retrieve Bestseller Product by FoodType - inside getBestsellerProductByFoodType() controller method");
		log.info("Retrieve Bestseller Product by FoodType - Product ID: "+pId);
		return productService.getBestsellerProductByFoodType(pId);
	}
	
	@PostMapping(value = ProductServiceConstants.RETRIEVEMULTIPLEPRODUCTSBYPID_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMultiProductById(@RequestBody List<CartItemsInfo> multiproduct) throws ProductNotFoundException{
		log.info("inside user service to get all users");
		return productService.getMultiProductById(multiproduct);
	}
	
	//Below services are used for admin portal
	//Need to add authentication and authorization check for the below services
	@PostMapping(value = ProductServiceConstants.ADDPRODUCT_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addProduct(@RequestHeader("Authorization") String token, @RequestBody Product product){
		log.info("Add New Product to the Menu - inside addProduct() controller method");
		log.info("Add New Product to the Menu - Token Validation is In-progress");
		userAuthFeign.validateUser(token);
		log.info("Add New Product to the Menu - Token Validation Successful");
		log.info("Add New Product to the Menu - New Product : "+product.toString());
		product.setPid(sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME));
		log.info("Add New Product to the Menu - New Product Id : "+ product.getPid());
		return productService.addProduct(product);
	}
	
	@PutMapping(value = ProductServiceConstants.UPDATEPRODUCT_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateProduct(@RequestHeader("Authorization") String token, @RequestBody Product product) throws ProductNotFoundException{
		log.info("Update Existing Product Details - inside updateProduct() controller method");
		log.info("Update Existing Product Details - Token Validation is In-progress");
		userAuthFeign.validateUser(token);
		log.info("Update Existing Product Details - Token Validation Successful");
		log.info("Update Existing Product Details - Updated Product Details : "+product.toString());
		return productService.updateProduct(product);
	}
	
	@DeleteMapping(value = ProductServiceConstants.DELETEPRODUCT_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteProduct(@RequestHeader("Authorization") String token, @PathVariable long pId) throws ProductNotFoundException{
		log.info("Delete Existing Product - inside deleteProduct() controller method");
		log.info("Delete Existing Product - Token Validation is In-progress");
		userAuthFeign.validateUser(token);
		log.info("Delete Existing Product - Token Validation Successful");
		log.info("Delete Existing Product - Product Id : "+pId);
		return productService.deleteProduct(pId);
	}
	
	@PutMapping(value = ProductServiceConstants.UPDATEPRODUCTLIVESTATUS_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateLiveStatus(@RequestHeader("Authorization") String token, @PathVariable long pId) throws ProductNotFoundException{
		log.info("Update Live Status of Existing Product - inside updateLiveStatus() controller method");
		log.info("Update Live Status of Existing Product - Token Validation is In-progress");
		userAuthFeign.validateUser(token);
		log.info("Update Live Status of Existing Product - Token Validation Successful");
		log.info("Update Live Status of Existing Product - Product Id : "+pId);
		return productService.updateLiveStatus(pId);
	}
	
}
