package com.store.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.store.payment.model.BillDetails;

public interface BillDetailsRepository extends MongoRepository<BillDetails, String> {

}
