package com.store.payment.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.store.payment.model.BillDetails;
import com.store.payment.model.BillItems;
import com.store.payment.model.Customer;
import com.store.payment.model.CustomerType;
import com.store.payment.model.DiscountDetails;
import com.store.payment.model.ProductDetails;
import com.store.payment.model.ProductType;
import com.store.payment.repository.BillDetailsRepository;
import com.store.payment.repository.CustomerRepository;
import com.store.payment.repository.DiscountDetailsRepository;
import com.store.payment.requests.BillRequest;
import com.store.payment.service.BillDetailsService;
import com.store.payment.service.BillDetailsServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@EnableAutoConfiguration
public class BillDetailsServiceTest {

	@Mock
	CustomerRepository customerRepository;

	@Mock
	DiscountDetailsRepository discountDetailsRepository;

	@Mock
	BillDetailsRepository billDetailsRepository;

	BillDetailsService billDetailsService;

	ProductType productType1, productType2, productType3;
	ProductDetails product;
	BillItems billItems;
	List<BillItems> listProducts;

	BillRequest billRequest;
	BillDetails billDetails;

	Customer customer;
	CustomerType customerType;
	DiscountDetails discountDetails;

	@Before
	public void setup() {
		billDetailsService = new BillDetailsServiceImpl(customerRepository, discountDetailsRepository,
				billDetailsRepository);

		listProducts = new ArrayList<BillItems>();

		productType1 = new ProductType("1", "GRO");
		productType2 = new ProductType("2", "TEX");
		productType3 = new ProductType("3", "UTL");

		product = new ProductDetails("pr1", productType1, "RICE", 100.00);
		billItems = new BillItems(product, 2.0);
		listProducts.add(billItems);

		product = new ProductDetails("pr2", productType2, "DRESS", 350.00);
		billItems = new BillItems(product, 5.0);
		listProducts.add(billItems);

		billDetails = new BillDetails();
		billDetails.setProducts(listProducts);

		billRequest = new BillRequest();
		billRequest.setBillDetails(billDetails);
	}

	@Test
	public void testFindTotalSum() {
		Double expectedTotalSum = 1750.00;
		Double totalSum = billDetailsService.findTotalSum(billRequest);

		assertNotNull(totalSum);
		assertEquals(totalSum, expectedTotalSum);
	}

	@Test
	public void testCalculateDiscount() {
		Double expectedTotalSum = 1750.00;
		Double expectedDiscount = 175.00;
		Double discount = billDetailsService.calculateDiscount(billRequest, expectedTotalSum, 10.0);

		assertNotNull(discount);
		assertEquals(discount, expectedDiscount);
	}

	@Test
	public void testNoCalculateDiscountFromBackend() {
		Double expectedTotalSum = 1750.00;
		Double expectedDiscount = 85.00;
		Double discount = billDetailsService.calculateDiscount(billRequest, expectedTotalSum, null);

		assertNotNull(discount);
		assertEquals(discount, expectedDiscount);
	}

	@Test
	public void testUpdateDiscountAndAmount() {
		Double expectedDiscount = 175.00;
		Double expectedAmount = 1950.00 - expectedDiscount;
		BillRequest resultantBillRequest = billDetailsService.updateDiscountAndAmount(billRequest, 10.0);

		assertNotNull(resultantBillRequest);
		assertEquals(resultantBillRequest.getBillDetails().getDiscount(), expectedDiscount);
		assertEquals(resultantBillRequest.getBillDetails().getActualAmount(), expectedAmount);
	}

	@Test
	public void testFindDiscountEmployeeCustomer() throws ParseException {
		Double expectedDiscount = 30.00;

		Date registeredDate = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2018");

		String registeredId = "1";
		customerType = new CustomerType("1", "Emp", "Employee");
		customer = new Customer("cust1", customerType, "customer1", registeredDate, "1");
		Mockito.when(customerRepository.findByRegiteredId(registeredId)).thenReturn(customer);

		discountDetails = new DiscountDetails(customerType, 30.0);
		Mockito.when(discountDetailsRepository.findByCustomerType(customer.getCustomerType().getCutomerTypeId()))
				.thenReturn(discountDetails);

		Double discountAmount = billDetailsService.findDiscountForExistingCustomer(registeredId);

		assertNotNull(discountAmount);
		assertEquals(expectedDiscount, discountAmount);

	}

	// 2 years regular customer

	@Test
	public void testUpdateDiscountAndAmountForEmployeeCustomer() throws ParseException {
		Double expectedDiscount = 525.00;
		Double expectedAmount = 1950.00 - expectedDiscount;

		Date registeredDate = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2018");

		String registeredId = "1";
		customerType = new CustomerType("1", "Emp", "Employee");
		customer = new Customer("cust1", customerType, "customer1", registeredDate, "1");
		Mockito.when(customerRepository.findByRegiteredId(registeredId)).thenReturn(customer);

		discountDetails = new DiscountDetails(customerType, 30.0);
		Mockito.when(discountDetailsRepository.findByCustomerType(customer.getCustomerType().getCutomerTypeId()))
				.thenReturn(discountDetails);

		Double discountInPercentage = billDetailsService.findDiscountForExistingCustomer(registeredId);
		BillRequest resultantBillRequest = billDetailsService.updateDiscountAndAmount(billRequest,
				discountInPercentage);

		assertNotNull(resultantBillRequest);
		assertEquals(resultantBillRequest.getBillDetails().getDiscount(), expectedDiscount);
		assertEquals(resultantBillRequest.getBillDetails().getActualAmount(), expectedAmount);

	}

	@Test
	public void testUpdateDiscountAndAmountForRelativeCustomer() throws ParseException {
		Double expectedDiscount = 175.00;
		Double expectedAmount = 1950.00 - expectedDiscount;

		Date registeredDate = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2018");

		String registeredId = "2";
		customerType = new CustomerType("2", "Rel", "Relative");
		customer = new Customer("cust2", customerType, "customer2", registeredDate, "2");
		Mockito.when(customerRepository.findByRegiteredId(registeredId)).thenReturn(customer);

		discountDetails = new DiscountDetails(customerType, 10.0);
		Mockito.when(discountDetailsRepository.findByCustomerType(customer.getCustomerType().getCutomerTypeId()))
				.thenReturn(discountDetails);

		Double discountInPercentage = billDetailsService.findDiscountForExistingCustomer(registeredId);
		BillRequest resultantBillRequest = billDetailsService.updateDiscountAndAmount(billRequest,
				discountInPercentage);

		assertNotNull(resultantBillRequest);
		assertEquals(resultantBillRequest.getBillDetails().getDiscount(), expectedDiscount);
		assertEquals(resultantBillRequest.getBillDetails().getActualAmount(), expectedAmount);

	}

	@Test
	public void testUpdateDiscountAndAmountFor2YearsRegularCustomer() throws ParseException {
		Double expectedDiscount = 87.50;
		Double expectedAmount = 1950.00 - expectedDiscount;

		Date registeredDate = new SimpleDateFormat("dd-MM-yyyy").parse("04-08-2018");

		String registeredId = "3";
		customerType = new CustomerType("2", "Oth", "Others");
		customer = new Customer("cust3", customerType, "customer3", registeredDate, "3");
		Mockito.when(customerRepository.findByRegiteredId(registeredId)).thenReturn(customer);

		discountDetails = null;
		Mockito.when(discountDetailsRepository.findByCustomerType(customer.getCustomerType().getCutomerTypeId()))
				.thenReturn(discountDetails);

		Double discountInPercentage = billDetailsService.findDiscountForExistingCustomer(registeredId);
		BillRequest resultantBillRequest = billDetailsService.updateDiscountAndAmount(billRequest,
				discountInPercentage);

		assertNotNull(resultantBillRequest);
		assertEquals(resultantBillRequest.getBillDetails().getDiscount(), expectedDiscount);
		assertEquals(resultantBillRequest.getBillDetails().getActualAmount(), expectedAmount);

	}

	@Test
	public void testUpdateDiscountAndAmountForExact2YearsRegularCustomer() throws ParseException {
		Double expectedDiscount = 87.50;
		Double expectedAmount = 1950.00 - expectedDiscount;

		Date registeredDate = new SimpleDateFormat("dd-MM-yyyy").parse("05-08-2018");

		String registeredId = "3";
		customerType = new CustomerType("2", "Oth", "Others");
		customer = new Customer("cust3", customerType, "customer3", registeredDate, "3");
		Mockito.when(customerRepository.findByRegiteredId(registeredId)).thenReturn(customer);

		discountDetails = null;
		Mockito.when(discountDetailsRepository.findByCustomerType(customer.getCustomerType().getCutomerTypeId()))
				.thenReturn(discountDetails);

		Double discountInPercentage = billDetailsService.findDiscountForExistingCustomer(registeredId);
		BillRequest resultantBillRequest = billDetailsService.updateDiscountAndAmount(billRequest,
				discountInPercentage);

		assertNotNull(resultantBillRequest);
		assertEquals(resultantBillRequest.getBillDetails().getDiscount(), expectedDiscount);
		assertEquals(resultantBillRequest.getBillDetails().getActualAmount(), expectedAmount);

	}

	@Test
	public void testUpdateDiscountAndAmountForLessThan2YearsRegularCustomer() throws ParseException {
		Double expectedDiscount = 85.00;
		Double expectedAmount = 1950.00 - expectedDiscount;

		Date registeredDate = new SimpleDateFormat("dd-MM-yyyy").parse("07-08-2018");

		String registeredId = "3";
		customerType = new CustomerType("2", "Oth", "Others");
		customer = new Customer("cust3", customerType, "customer3", registeredDate, "3");
		Mockito.when(customerRepository.findByRegiteredId(registeredId)).thenReturn(customer);

		discountDetails = null;
		Mockito.when(discountDetailsRepository.findByCustomerType(customer.getCustomerType().getCutomerTypeId()))
				.thenReturn(discountDetails);

		Double discountInPercentage = billDetailsService.findDiscountForExistingCustomer(registeredId);
		BillRequest resultantBillRequest = billDetailsService.updateDiscountAndAmount(billRequest,
				discountInPercentage);

		assertNotNull(resultantBillRequest);
		assertEquals(resultantBillRequest.getBillDetails().getDiscount(), expectedDiscount);
		assertEquals(resultantBillRequest.getBillDetails().getActualAmount(), expectedAmount);

	}

	@Test
	public void testUpdateDiscountAndAmountForNonRegisteredCustomer() throws ParseException {
		Double expectedDiscount = 85.00;
		Double expectedAmount = 1950.00 - expectedDiscount;

		String registeredId = "3";
		Mockito.when(customerRepository.findByRegiteredId(registeredId)).thenReturn(null);

		Double discountInPercentage = billDetailsService.findDiscountForExistingCustomer(registeredId);
		BillRequest resultantBillRequest = billDetailsService.updateDiscountAndAmount(billRequest,
				discountInPercentage);

		assertNotNull(resultantBillRequest);
		assertEquals(resultantBillRequest.getBillDetails().getDiscount(), expectedDiscount);
		assertEquals(resultantBillRequest.getBillDetails().getActualAmount(), expectedAmount);

	}

}
