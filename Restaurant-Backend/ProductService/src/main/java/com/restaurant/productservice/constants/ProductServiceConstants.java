package com.restaurant.productservice.constants;

public class ProductServiceConstants {
	
	//Product Controller Constants
	public static final String RETRIEVEALLPRODUCT_URI = "/api/v1/productservice/product/all";
	public static final String RETRIEVEPRODUCTBYPID_URI = "/api/v1/productservice/product/{pId}";
	public static final String RETRIEVEPRODUCTBYFOODTYPE_URI = "/api/v1/productservice/product/type/{foodType}";
	public static final String RETRIEVEBESTSELLERPRODUCTBYFOODTYPE_URI = "/api/v1/productservice/product/bestseller/{pId}";
	public static final String RETRIEVEMULTIPLEPRODUCTSBYPID_URI = "/api/v1/productservice/product/multiple";
	
	//Admin
	public static final String UPDATEPRODUCT_URI = "/api/v1/productservice/verify/product/update";
	public static final String ADDPRODUCT_URI = "/api/v1/productservice/verify/product/add";
	public static final String DELETEPRODUCT_URI = "/api/v1/productservice/verify/product/delete/{pId}";
	public static final String UPDATEPRODUCTLIVESTATUS_URI = "/api/v1/productservice/verify/product/update/status/{pId}";
	
	//Review Controller Constants
	public static final String RETRIEVEREVIEWBYPID_URI = "/api/v1/productservice/verify/user/{pId}/review";
	public static final String UPDATEREVIEW_URI = "/api/v1/productservice/verify/user/{pId}/review/update";
	public static final String ADDREVIEW_URI = "/api/v1/productservice/verify/user/{pId}/review/add";
	public static final String DELETEREVIEW_URI = "/api/v1/productservice/verify/user/{pId}/review/delete";
	public static final String UPDATEUSERIMAGES_URI = "/api/v1/productservice/verify/user/update/image";
}
