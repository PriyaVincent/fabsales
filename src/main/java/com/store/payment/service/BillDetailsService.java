package com.store.payment.service;

import java.util.HashMap;

import com.store.payment.model.BillDetails;
import com.store.payment.requests.BillRequest;

public interface BillDetailsService {
	
	public void createBill(HashMap<String, String> headers, BillRequest billRequest);
	
	public Double findTotalSum(BillRequest billRequest);
	
	public Double calculateDiscount(BillRequest billRequest, Double totalSum, Double discountInPercentage);
	
	public Double findDiscountForExistingCustomer(String registeredId);
	
	public BillRequest updateDiscountAndAmount(BillRequest billRequest, Double discountInPercentage);
	
	public BillDetails getReceipt(String receiptId);

}
