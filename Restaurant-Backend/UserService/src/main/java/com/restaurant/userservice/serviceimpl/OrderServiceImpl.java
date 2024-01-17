package com.restaurant.userservice.serviceimpl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.restaurant.userservice.constants.UserServiceConstants;
import com.restaurant.userservice.exception.OrderCancelFailedException;
import com.restaurant.userservice.exception.OrderNotFoundException;
import com.restaurant.userservice.model.CustomSucccessResponse;
import com.restaurant.userservice.model.DistanceMatrix;
import com.restaurant.userservice.model.Order;
import com.restaurant.userservice.model.OrderConfirmation;
import com.restaurant.userservice.model.OrderResponse;
import com.restaurant.userservice.model.OrderStatusEnum;
import com.restaurant.userservice.model.PaymentDetails;
import com.restaurant.userservice.repository.OrderRepository;
import com.restaurant.userservice.service.OrderService;
import com.restaurant.userservice.utility.UniqueIdGeneratorService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UniqueIdGeneratorService uniqueIdGenerator;

	CustomSucccessResponse response;
	
	private RazorpayClient client;
	
	@Value("${google.apiKey}")
	private String googleAPIKey;
	
	@Value("${razorpay.secretId}")
	private String SECRET_ID1;
	
	@Value("${razorpay.secretKey}")
	private String SECRET_KEY1;
	
	@Value("${stripe.apiKey}")
    private String stripeAPIKey;
	
	@Override
	public ResponseEntity<Object> addNewOrder(Order order) {
		// TODO Auto-generated method stub
		List<Order> existingOrders = orderRepository.findAll();
		String orderId = uniqueIdGenerator.generateUniqueOrderId(existingOrders.size());
		order.setOrderId(orderId);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		order.setDateOfOrder(timestamp.getTime());
		orderRepository.save(order);
		OrderConfirmation orderConfirmation = new OrderConfirmation(order.getOrderId(), order.getDateOfOrder());
		return new ResponseEntity<Object>(orderConfirmation, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> initializeStripePaymentDetails(PaymentDetails payment) throws StripeException {
		// TODO Auto-generated method stub
		Stripe.apiKey = stripeAPIKey;
		SessionCreateParams params = SessionCreateParams.builder()
				// We will use the credit card payment method 
				.setCustomerEmail(payment.getEmail())
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl(payment.getSuccessUrl())
				.setCancelUrl(
						payment.getCancelUrl())
				.addLineItem(
						SessionCreateParams.LineItem.builder().setQuantity(payment.getQuantity())
								.setPriceData(
										SessionCreateParams.LineItem.PriceData.builder()
												.setCurrency(payment.getCurrency()).setUnitAmount(payment.getAmount()*100)
												.setProductData(SessionCreateParams.LineItem.PriceData.ProductData
														.builder().setName(payment.getName()).build())
												.build())
								.build())
				.build();
  // create a stripe session
		Session session = Session.create(params);
		System.out.println(session);
		Map<String, String> responseData = new HashMap<>();
    // We get the sessionId and we putted inside the response data you can get more info from the session object
		responseData.put("id", session.getId());
		return new ResponseEntity<Object>(responseData, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> initializeRazorPayPaymentDetails(@RequestBody PaymentDetails orderRequest) throws RazorpayException {
		OrderResponse response = new OrderResponse();
		try {
			client = new RazorpayClient(SECRET_ID1, SECRET_KEY1);

			com.razorpay.Order order = createRazorPayOrder(orderRequest.getAmount());
			System.out.println("---------------------------");
			String orderId = (String) order.get("id");
			System.out.println("Order ID: " + orderId);
			System.out.println("---------------------------");
			response.setRazorpayOrderId(orderId);
			response.setApplicationFee("" + orderRequest.getAmount());
			response.setSecretKey(SECRET_KEY1);
			response.setSecretId(SECRET_ID1);
			response.setPgName("razor1");
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			throw new RazorpayException("Invalid Request");
		}

		return new ResponseEntity<Object>(response,HttpStatus.OK);

	}

	private com.razorpay.Order createRazorPayOrder(long amount) throws RazorpayException {

		JSONObject options = new JSONObject();
		options.put("amount", amount*100);
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		options.put("payment_capture", 1); // You can enable this if you want to do Auto Capture.
		return client.orders.create(options);
	}
	public ResponseEntity<Object> getActiveOrders(String userId) {
		// TODO Auto-generated method stub
		List<Order> activeOrders = orderRepository.findAll().stream().filter(order-> UserServiceConstants.ACTIVEORDERSTATUS.contains(order.getStatus().toString()) && order.getUserId().equals(userId)).sorted(Comparator.comparing(Order::getDateOfOrder).reversed()).collect(Collectors.toList());
		return new ResponseEntity<Object>(activeOrders, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getPastOrders(String userId) {
		// TODO Auto-generated method stub
		List<Order> pastOrders = orderRepository.findAll().stream().filter(order-> UserServiceConstants.PASTORDERSTATUS.contains(order.getStatus().toString()) && order.getUserId().equals(userId)).sorted(Comparator.comparing(Order::getDateOfOrder).reversed()).collect(Collectors.toList());
		return new ResponseEntity<Object>(pastOrders, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> updateOrderStatus(String orderId, String status) throws OrderNotFoundException, OrderCancelFailedException {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order Not Found."));
		if(status.equalsIgnoreCase(OrderStatusEnum.CANCELLED.toString())) {
			if(order.getStatus() == OrderStatusEnum.PLACED) {
				order.setStatus(OrderStatusEnum.valueOf(status.toUpperCase()));
			}else {
				throw new OrderCancelFailedException("Order Cancellation Failed.");
			}
		}
		if(!status.equalsIgnoreCase(OrderStatusEnum.CANCELLED.toString())){
			order.setStatus(OrderStatusEnum.valueOf(status.toUpperCase()));
		}
		orderRepository.save(order);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Order Status Updated Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> updateOrderRating(String orderId, int rating) throws OrderNotFoundException {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order Not Found."));
		order.setRating(rating);
		orderRepository.save(order);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Order Rating Updated Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getOrderById(String orderId) throws OrderNotFoundException {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order Not Found."));
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> calculateDistanceMatrix(DistanceMatrix matrixObject) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String source = URLEncoder.encode(matrixObject.getSource(), StandardCharsets.UTF_8.toString());
		String destination = URLEncoder.encode(matrixObject.getDestination(), StandardCharsets.UTF_8.toString());
		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + source + "&destinations=" + destination + "&key=" + googleAPIKey;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return new ResponseEntity<>(response.body(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getAllOrdersByStatus(String status) {
		List<Order> allOrders = orderRepository.findAll().stream()
		.filter((order)-> ((status.equalsIgnoreCase("PAST"))?
				UserServiceConstants.PASTORDERSTATUS.contains(order.getStatus().toString()):
					UserServiceConstants.ACTIVEORDERSTATUS.contains(order.getStatus().toString())))
		.sorted(Comparator.comparing(Order::getDateOfOrder).reversed()).collect(Collectors.toList());
		return new ResponseEntity<>(allOrders, HttpStatus.OK);
	}

}
