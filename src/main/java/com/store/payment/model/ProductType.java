package com.store.payment.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "producttype")
public class ProductType implements Serializable {

	private static final long serialVersionUID = 3309246466540146482L;

	public ProductType(String productTypeId, String productTypeName) {
		super();
		this.productTypeId = productTypeId;
		this.productTypeName = productTypeName;
	}

	private String productTypeId;
	private String productTypeName;

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

}
