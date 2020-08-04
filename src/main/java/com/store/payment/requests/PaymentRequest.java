package com.store.payment.requests;

import java.io.Serializable;

public class PaymentRequest implements Serializable {

	private static final long serialVersionUID = 592715056184011483L;

	private String registeredId;
	private Double purchaseAmount;

	public String getRegisteredId() {
		return registeredId;
	}

	public void setRegisteredId(String registeredId) {
		this.registeredId = registeredId;
	}

	public Double getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(Double purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

}
