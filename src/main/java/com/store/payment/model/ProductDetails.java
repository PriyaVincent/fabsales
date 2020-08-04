package com.store.payment.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productdetails")
public class ProductDetails implements Serializable {

	private static final long serialVersionUID = -8710966815928359962L;

	private String productId;
	private ProductType productType;

	private String productName;
	private Double price;

	public ProductDetails(String productId, ProductType productType, String productName, Double price) {
		super();
		this.productId = productId;
		this.productType = productType;
		this.productName = productName;
		this.price = price;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
