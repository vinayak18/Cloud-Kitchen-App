package com.restaurant.userservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class UserDetails {
	@Id
	@Field("_id")
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
