package com.restaurant.productservice.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
	private String userId;
	private String email;
	private String name;
	private long phoneNo;
	private String password;
	private String img_url;
	private BlobImage blobImage;
	private List<Address> address = new ArrayList<Address>();
	private List<CartItemsInfo> cart = new ArrayList<CartItemsInfo>();
	private List<String> role = new ArrayList<String> ();
}
