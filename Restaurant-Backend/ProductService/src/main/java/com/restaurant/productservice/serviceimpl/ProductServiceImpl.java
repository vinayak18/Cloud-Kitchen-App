package com.restaurant.productservice.serviceimpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import com.restaurant.productservice.exception.FoodTypeNotExistsException;
import com.restaurant.productservice.exception.ProductNotFoundException;
import com.restaurant.productservice.model.CartItemsInfo;
import com.restaurant.productservice.model.CustomSucccessResponse;
import com.restaurant.productservice.model.FoodTypeEnum;
import com.restaurant.productservice.model.Product;
import com.restaurant.productservice.repository.ProductRepository;
import com.restaurant.productservice.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	CustomSucccessResponse response;

	@Override
	public ResponseEntity<Object> getAllProduct() {
		log.info("getting all products");
		List<Product> allProducts = productRepository.findAll();
		return new ResponseEntity<>(allProducts, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getProductById(long pId) throws ProductNotFoundException {
		log.info("getting a particular product by pId");
		Product product = productRepository.findById(pId).orElseThrow(()-> new ProductNotFoundException("Product Not Found."));
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getProductByFoodType(String type) throws FoodTypeNotExistsException {
		log.info("getting product based on food-type");
		try {
			FoodTypeEnum foodtype = EnumUtils.findEnumInsensitiveCase(FoodTypeEnum.class, type);
			List<Product> list = productRepository.findAll();
			list = list.stream().filter(product -> product.getType().equals(foodtype)).collect(Collectors.toList());
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			throw new FoodTypeNotExistsException("Food type does not exisits.");
		}
	}

	@Override
	public ResponseEntity<Object> getBestsellerProductByFoodType(long pId) throws ProductNotFoundException {
		log.info("getting bestseller product based on food-type");
		Product product = productRepository.findById(pId).orElseThrow(()-> new ProductNotFoundException("Product Not Found."));
		FoodTypeEnum foodtype = EnumUtils.findEnumInsensitiveCase(FoodTypeEnum.class, product.getType().name());
		List<Product> list = productRepository.findAll();
		list = list.stream()
				.filter((prod) -> prod.getType().equals(foodtype) && prod.getPid() != product.getPid())
				.sorted(Comparator.comparingDouble(Product::getAvgRating).reversed()).limit(3)
				.collect(Collectors.toList());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> addProduct(Product product) {
		log.info("adding a new product");
		productRepository.save(product);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Product Added Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> updateProduct(Product newProduct) throws ProductNotFoundException {
		log.info("updating an existing product");
		Product product = productRepository.findById(newProduct.getPid()).orElseThrow(()-> new ProductNotFoundException("Product Not Found."));
		productRepository.save(newProduct);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Product Updated Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> deleteProduct(long pId) throws ProductNotFoundException {
		log.info("deleting an existing product");
		Product product = productRepository.findById(pId).orElseThrow(()-> new ProductNotFoundException("Product Not Found."));
		productRepository.deleteById(pId);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Product Deleted Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> updateLiveStatus(long pId) throws ProductNotFoundException {
		log.info("updating the live status of an existing product");
		Product product = productRepository.findById(pId).orElseThrow(()-> new ProductNotFoundException("Product Not Found."));
		product.setLive(!product.isLive());
		productRepository.save(product);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Product Live Status Updated Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getMultiProductById(List<CartItemsInfo> multiproduct) throws ProductNotFoundException {
		log.info("getting a multiple products by pId");
		List<Product> products = new ArrayList<>();
		
		multiproduct.stream()
		.forEach((CartItemsInfo cartItemsInfo)->{
			try {
				Product product = productRepository.findById(cartItemsInfo.getPid()).orElseThrow(()-> new ProductNotFoundException("Product Not Found."));
				product.setQuantity(cartItemsInfo.getQuantity());
				products.add(product);
				
			} catch (ProductNotFoundException e) {
				log.error("Error message: "+ e.getMessage());
			}
		});
		
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
}
