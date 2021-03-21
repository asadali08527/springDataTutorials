
package com.tutorialspoint.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tutorialspoint.document.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	Optional<Customer> findByEmail(String email);

	@Query(value = "{address.country:?0}")
	List<Customer> findByCountry(String country);

}
