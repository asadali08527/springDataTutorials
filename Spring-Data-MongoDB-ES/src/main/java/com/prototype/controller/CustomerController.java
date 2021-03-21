package com.prototype.controller;

import java.util.Iterator;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prototype.md.document.Customer;
import com.prototype.md.repository.CustomerRepository;

@RestController
@RequestMapping("/rest")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private com.prototype.es.repository.CustomerRepository esCustomerRepository;

	@GetMapping("/customers")
	public Iterable<com.prototype.es.document.Customer> fetchCustomers() {
		esCustomerRepository.findAll().forEach(System.out::println);
		return esCustomerRepository.findAll();

	}

	@PostMapping("/customer/add")
	public void persisDocument(@RequestBody Customer customer) {
		customerRepository.save(customer);
	}

	@DeleteMapping("/customer/delete/es")
	public void deleteRecord() {
		esCustomerRepository.deleteAll();
	}

	@PostMapping("/customer/transfer/es")
	public void pushDocumentToES(@RequestBody Customer customer) {
		Iterable<Customer> customersItr = customerRepository.findAll();
		System.out.println(customersItr);
		Iterator<Customer> custIterator = customersItr.iterator();
		while (custIterator.hasNext()) {
			Customer mdCustomer = custIterator.next();
			com.prototype.es.document.Customer esCustomer = new com.prototype.es.document.Customer();
			esCustomer.setName(mdCustomer.getName());
			esCustomer.setEmail(mdCustomer.getEmail());
			esCustomer.setId(new Random().nextLong());
			esCustomer.setType("work");
			esCustomer = esCustomerRepository.save(esCustomer);
			System.out.println(esCustomer);
		}
		esCustomerRepository.findAll().forEach(System.out::println);
		System.out.println(esCustomerRepository.count());

	}

}
