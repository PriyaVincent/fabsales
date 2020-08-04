package com.store.payment.controllers;

import static com.store.payment.constants.ServiceConstants.BASE_URL;
import static com.store.payment.constants.ServiceConstants.HEALTH_ENDPOINT;
import static com.store.payment.constants.ServiceConstants.HEALTH_OK;
import static com.store.payment.constants.ServiceConstants.PAYMENT_URL;
import static com.store.payment.constants.ServiceConstants.RECEIPT_ID;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.payment.requests.BillRequest;
import com.store.payment.response.model.Response;
import com.store.payment.service.BillDetailsService;

@RestController
@RequestMapping(BASE_URL)
public class BillPaymentController {

	private static final Logger LOGGER = LogManager.getLogger(BillPaymentController.class);

	private final BillDetailsService billDetailsService;

	@GetMapping(HEALTH_ENDPOINT)
	public ResponseEntity<String> handshake() {
		return new ResponseEntity<String>(HEALTH_OK, HttpStatus.OK);

	}

	@Autowired
	public BillPaymentController(BillDetailsService paymentDetailsService) {
		this.billDetailsService = paymentDetailsService;
	}

	@GetMapping(PAYMENT_URL)
	public ResponseEntity<Response> getBill(@RequestHeader HttpHeaders headers,
			@PathVariable(RECEIPT_ID) String receiptId) {
		LOGGER.info("BillPaymentController getBill() starts");
		billDetailsService.getReceipt(receiptId);
		return new ResponseEntity<Response>(HttpStatus.OK);
	}

	@PostMapping(PAYMENT_URL)
	public ResponseEntity<Response> createPayment(@RequestHeader HttpHeaders headers,
			@RequestBody BillRequest billRequest) {
		LOGGER.info("BillPaymentController createBill() starts");
		billDetailsService.createBill(new HashMap<String, String>(), billRequest);
		return new ResponseEntity<Response>(HttpStatus.CREATED);
	}
}