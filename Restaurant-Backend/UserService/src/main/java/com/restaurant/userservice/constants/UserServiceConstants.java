package com.restaurant.userservice.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserServiceConstants {
	
	//User Controller Constants
	public static final String VALIDATEGOOGLESOCIALLOGIN_URI = "/api/v1/userservice/auth/google/login";
	public static final String VALIDATEFACEBOOKSOCIALLOGIN_URI = "/api/v1/userservice/auth/facebook/login/{id}";
	public static final String RETRIEVEALLUSERS_URI = "/api/v1/userservice/verify/user/all";
	public static final String RETRIEVEALLCUSTOMERS_URI = "/api/v1/userservice/verify/customer/all";
	public static final String RETRIEVEUSERBYEMAIL_URI = "/api/v1/userservice/verify/user/email/{email}";
	public static final String RETRIEVEUSERBYUSERID_URI = "/api/v1/userservice/verify/user/id/{userId}";
	public static final String UPDATEUSER_URI = "/api/v1/userservice/verify/update/user";
	public static final String UPDATECARTITEMS_URI = "/api/v1/userservice/verify/user/update/cart/{userId}";
	public static final String UPLOADUSERIMAGE_URI = "/api/v1/userservice/verify/user/upload/image/{userId}";
	public static final String RETRIEVEUSERIMAGE_URI = "/api/v1/userservice/verify/user/get/image";
	public static final String VALIDATEAPPLOGIN_URI = "/api/v1/userservice/auth/user/login";
	public static final String REGISTERNEWUSER_URI = "/api/v1/userservice/auth/user/register";
	public static final String VALIDATEUSER = "/api/v1/userservice/validate/user";
	public static final String ISUSEREXISTS = "/api/v1/userservice/public/user/exists/{email}";
	public static final String RESETPASSWORD = "/api/v1/userservice/public/user/reset/password";
	
	//Coupon Controller Constants
	public static final String RETRIEVECOUPONBYCOUPONCODE_URI = "/api/v1/userservice/verify/user/coupon/retrieve";
	//Admin
	public static final String REGISTERNEWCOUPON_URI = "/api/v1/userservice/verify/coupon/add";
	public static final String UPDATECOUPONDETAILS_URI = "/api/v1/userservice/verify/coupon/update";
	public static final String DELETECOUPON_URI = "/api/v1/userservice/verify/coupon/delete/{code}";
	public static final String RETRIEVEALLCOUPON_URI = "/api/v1/userservice/verify/coupon/retrieve/all";
	
	//Order Controller Constants
	public static final String INITIALIZESTRIPENEWORDER_URI = "/api/v1/userservice/verify/order/stripe/initialize";
	public static final String INITIALIZERAZORPAYNEWORDER_URI = "/api/v1/userservice/verify/order/razorpay/initialize";
	public static final String CALCULATEDISTANCE_URI = "/api/v1/userservice/verify/order/calculate/distance";
	public static final String REGISTERNEWORDER_URI = "/api/v1/userservice/verify/order/add";
	public static final String RETRIEVEORDERBYID_URI = "/api/v1/userservice/verify/order/retrieve/{id}";
	public static final String RETRIEVEACTIVEORDER_URI = "/api/v1/userservice/verify/order/{userId}/active";
	public static final String RETRIEVEPASTORDER_URI = "/api/v1/userservice/verify/order/{userId}/past";
	public static final String RETRIEVEALLORDERSBYSTATUS_URI = "/api/v1/userservice/verify/order/{status}";
	public static final String UPDATEORDERSTATUS_URI = "/api/v1/userservice/verify/order/{id}/update/status/{status}";
	public static final String UPDATEORDERRATING_URI = "/api/v1/userservice/verify/order/{id}/update/rating/{rating}";
	
	//Constants List
	public static final List<String> ACTIVEORDERSTATUS = new ArrayList<String>(Arrays.asList("PLACED","ACCEPTED","PREPARING","DISPACHED"));
	public static final List<String> PASTORDERSTATUS = new ArrayList<String>(Arrays.asList("DELIVERED","CANCELLED","DECLINED"));
	public static final List<String> CUSTOMERROLES = new ArrayList<String>(Arrays.asList("ROLE_CUSTOMER","ROLE_PRIME_CUSTOMER","ROLE_SOCIAL_CUSTOMER"));
	
}
