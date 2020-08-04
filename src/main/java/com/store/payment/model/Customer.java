package com.store.payment.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer implements Serializable {

	private static final long serialVersionUID = 2467374700674470100L;
	private String customerId;
	private CustomerType customerType;
	private String customerName;
	private Date registeredOn;
	private String registeredId;

	public Customer(String customerId, CustomerType customerType, String customerName, Date registeredOn,
			String registeredId) {
		super();
		this.customerId = customerId;
		this.customerType = customerType;
		this.customerName = customerName;
		this.registeredOn = registeredOn;
		this.registeredId = registeredId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}

	public String getRegisteredId() {
		return registeredId;
	}

	public void setRegisteredId(String registeredId) {
		this.registeredId = registeredId;
	}

}
