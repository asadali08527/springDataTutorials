package com.prototype.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.prototype.es.document.Customer;
import com.prototype.es.repository.CustomerRepository;
import com.prototype.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer save(Customer customer) {
		return customer = customerRepository.save(customer);
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	@Override
	public Page<Customer> findByName(String name) {
		return customerRepository.findByName(name, PageRequest.of(0, 2));

	}

	@Override
	public Page<Customer> findByAddressCity(String city) {
		return customerRepository.findByAddressCityUsingCustomQuery(city, PageRequest.of(0, 2));
	}

	@Override
	public void deleteById(Long id) {
		customerRepository.deleteById(id);
	}

}
