package com.prototype.md.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.prototype.md.document.Customer;

@Repository("mdCustomerRepository")
public interface CustomerRepository extends MongoRepository<Customer, String> {

	Optional<Customer> findByEmail(String email);

	@Query(value = "{address.country:?0}")
	List<Customer> findByCountry(String country);

}
