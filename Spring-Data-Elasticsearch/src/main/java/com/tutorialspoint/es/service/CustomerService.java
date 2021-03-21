package com.tutorialspoint.es.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tutorialspoint.es.document.Customer;

public interface CustomerService {

	Customer save(Customer customer);

	Optional<Customer> findById(Long id);

	Page<Customer> findByName(String name);

	Page<Customer> findByAddressCity(String city);

	void deleteById(Long id);

}
