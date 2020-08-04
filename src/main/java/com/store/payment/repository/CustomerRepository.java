package com.store.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.store.payment.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
	
	@Query("{'registeredId': ?0}")
	public Customer findByRegiteredId(String registeredId);

}
