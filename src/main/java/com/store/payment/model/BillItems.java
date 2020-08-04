package com.store.payment.model;

import java.io.Serializable;

public class BillItems implements Serializable {

	private static final long serialVersionUID = -5924428389165649588L;

	private ProductDetails product;
	private Double quantity;
	private Double amount;

	public BillItems(ProductDetails product, Double quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.amount = quantity * product.getPrice();
	}

	public ProductDetails getProduct() {
		return product;
	}

	public void setProduct(ProductDetails product) {
		this.product = product;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
