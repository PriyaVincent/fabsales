package com.store.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.store.payment.model.DiscountDetails;

public interface DiscountDetailsRepository extends MongoRepository<DiscountDetails, String> {

	@Query("{'customerType.cutomerTypeId': ?0}")
	public DiscountDetails findByCustomerType(String customerTypeId);
}
