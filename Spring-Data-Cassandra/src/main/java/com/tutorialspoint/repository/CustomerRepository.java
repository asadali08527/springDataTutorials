package com.tutorialspoint.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import com.tutorialspoint.entity.Customer;

public interface CustomerRepository extends CassandraRepository<Customer, Long> {

	@AllowFiltering
	Customer findByName(String string);

}
