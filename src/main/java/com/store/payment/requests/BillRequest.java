package com.store.payment.requests;

import java.io.Serializable;

import com.store.payment.model.BillDetails;

public class BillRequest implements Serializable {

	private static final long serialVersionUID = 592715056184011483L;

	private BillDetails billDetails;
	private String registeredId;

	public BillDetails getBillDetails() {
		return billDetails;
	}

	public void setBillDetails(BillDetails billDetails) {
		this.billDetails = billDetails;
	}

	public String getRegisteredId() {
		return registeredId;
	}

	public void setRegisteredId(String registeredId) {
		this.registeredId = registeredId;
	}

}
