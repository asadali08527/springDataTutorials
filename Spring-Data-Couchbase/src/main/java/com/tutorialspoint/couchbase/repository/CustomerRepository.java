package com.tutorialspoint.couchbase.repository;

import java.util.List;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.tutorialspoint.couchbase.document.Customer;

@Repository
public interface CustomerRepository extends CouchbaseRepository<Customer, Long> {
	List<Customer> findByEmail(String string);

}
