package com.store.payment.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customertype")
public class CustomerType implements Serializable {

	private static final long serialVersionUID = 6361013469786993267L;

	private String cutomerTypeId;
	private String customerType;
	private String description;

	public CustomerType(String cutomerTypeId, String customerType, String description) {
		super();
		this.cutomerTypeId = cutomerTypeId;
		this.customerType = customerType;
		this.description = description;
	}

	public String getCutomerTypeId() {
		return cutomerTypeId;
	}

	public void setCutomerTypeId(String cutomerTypeId) {
		this.cutomerTypeId = cutomerTypeId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
