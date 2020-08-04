package com.store.payment.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "discountdetails")
public class DiscountDetails implements Serializable {

	private static final long serialVersionUID = 4100790226648682086L;

	@DBRef
	private CustomerType cutomerType;
	private Double discount;

	public DiscountDetails(CustomerType cutomerType, Double discount) {
		super();
		this.cutomerType = cutomerType;
		this.discount = discount;
	}

	public CustomerType getCutomerType() {
		return cutomerType;
	}

	public void setCutomerType(CustomerType cutomerType) {
		this.cutomerType = cutomerType;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

}
