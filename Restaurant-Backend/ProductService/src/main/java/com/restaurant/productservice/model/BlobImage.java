package com.restaurant.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlobImage {
	
	private String name;
	private String type;
	private byte[] picByte;

}
