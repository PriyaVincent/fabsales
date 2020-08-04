package com.store.payment.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.payment.model.Customer;
import com.store.payment.model.DiscountDetails;
import com.store.payment.model.BillDetails;
import com.store.payment.model.BillItems;
import com.store.payment.repository.BillDetailsRepository;
import com.store.payment.repository.CustomerRepository;
import com.store.payment.repository.DiscountDetailsRepository;
import com.store.payment.requests.BillRequest;
import com.store.payment.utils.DataUtils;

@Service
public class BillDetailsServiceImpl implements BillDetailsService {

	CustomerRepository customerRepository;
	DiscountDetailsRepository discountDetailsRepository;
	BillDetailsRepository billDetailsRepository;

	@Autowired
	public BillDetailsServiceImpl(CustomerRepository customerRepository,
			DiscountDetailsRepository discountDetailsRepository, BillDetailsRepository billDetailsRepository) {
		this.customerRepository = customerRepository;
		this.discountDetailsRepository = discountDetailsRepository;
		this.billDetailsRepository = billDetailsRepository;
	}

	public void createBill(HashMap<String, String> headers, BillRequest billRequest) {
		Double discountInPercentage = findDiscountForExistingCustomer(billRequest.getRegisteredId());
		billRequest = updateDiscountAndAmount(billRequest, discountInPercentage);
		billDetailsRepository.save(billRequest.getBillDetails());
	}

	public BillRequest updateDiscountAndAmount(BillRequest billRequest, Double discountInPercentage) {
		Double totalSum = findTotalSum(billRequest);
		Double discount = calculateDiscount(billRequest, totalSum, discountInPercentage);

		Double total = billRequest.getBillDetails().getProducts().stream().mapToDouble(item -> item.getAmount()).sum();
		billRequest.getBillDetails().setDiscount(discount);
		billRequest.getBillDetails().setActualAmount(total - discount);

		return billRequest;
	}

	public Double findTotalSum(BillRequest billRequest) {

		List<BillItems> discountableItems = billRequest.getBillDetails().getProducts().stream()
				.filter(items -> !items.getProduct().getProductType().getProductTypeName().equals("GRO"))
				.collect(Collectors.toList());

		Double totalSum = discountableItems.stream().mapToDouble(item -> item.getAmount()).sum();
		return totalSum;
	}

	public Double calculateDiscount(BillRequest billRequest, Double totalSum, Double discountInPercentage) {
		Double discount = 0.0;
		if (discountInPercentage != null) {
			discount = totalSum * discountInPercentage / 100;
		} else {
			Double lessThanHundred = totalSum % 100;
			discount = (totalSum - lessThanHundred) * 5 / 100;
		}
		return discount;
	}

	public Double findDiscountForExistingCustomer(String registeredId) {
		Customer customer = customerRepository.findByRegiteredId(registeredId);
		if (customer != null) {
			DiscountDetails discountDetails = discountDetailsRepository
					.findByCustomerType(customer.getCustomerType().getCutomerTypeId());
			// customer type is employee or associate with concern - predefined customertype
			if (discountDetails != null)
				return discountDetails.getDiscount();
			else {
				// calculate the customer is the past regular customer for past 2 years
				Boolean isTwoYearsCustomer = DataUtils.calculate2Years(customer.getRegisteredOn());
				if (isTwoYearsCustomer) {
					return 5.0;
				}
			}
		}
		return null;
	}

	@Override
	public BillDetails getReceipt(String receiptId) {
		return billDetailsRepository.findOne(receiptId);
	}

}
