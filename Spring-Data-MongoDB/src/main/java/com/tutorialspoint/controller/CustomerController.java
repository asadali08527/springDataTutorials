package com.tutorialspoint.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorialspoint.document.Customer;
import com.tutorialspoint.repository.CustomerRepository;

@RestController
@RequestMapping("/rest")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customers")
	public List<Customer> fetchCustomers() {
		return customerRepository.findAll();
	}

	@PostMapping("/customer/add")
	public void persisDocument(@RequestBody Customer customer) {
		customerRepository.save(customer);
	}

}
