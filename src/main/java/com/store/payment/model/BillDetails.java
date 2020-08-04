package com.store.payment.model;

import java.io.Serializable;
import java.util.List;

public class BillDetails implements Serializable {

	private static final long serialVersionUID = -2174194506689061053L;

	private String billId;
	private List<BillItems> billItems;
	private Double discount;
	private Double actualAmount;

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public List<BillItems> getProducts() {
		return billItems;
	}

	public void setProducts(List<BillItems> billItems) {
		this.billItems = billItems;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

}
