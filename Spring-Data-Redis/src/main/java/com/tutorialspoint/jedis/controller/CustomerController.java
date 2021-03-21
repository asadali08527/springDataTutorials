package com.tutorialspoint.jedis.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorialspoint.jedis.entity.Customer;
import com.tutorialspoint.jedis.service.CustomerService;

@RestController
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	// Persisting an object
	@PostMapping("/customer/add")
	public void persistObject(@RequestBody Customer customer) {
		customerService.setCustomerAsString(String.valueOf(customer.getId()), customer);
	}

	// Retrieving an object
	@GetMapping("/customer/find/{id}")
	public String fetchObject(@PathVariable String id) {
		return customerService.getCustomerAsString(id);
	}

	// Deleting an object
	@DeleteMapping("/customer/delete/{id}")
	public void deleteObject(@PathVariable String id) {
		customerService.deleteById(id);
	}

	// Add an object to the list
	@PostMapping("/customer/list/add")
	public void persistObjectList(@RequestBody Customer customer) {
		customerService.persistCustomerToList(customer);

	}

	// Retrieving all object from the list
	@GetMapping("/customer/find/all")
	public List<Customer> fetchObjectList() {
		return customerService.fetchAllCustomer();
	}

	// Add an object to the set
	@PostMapping("/customer/set/add")
	public void persistObjecSet(@RequestBody Customer customer) {
		customerService.persistCustomerToSet(customer);

	}

	// Retrieving all object from the set
	@GetMapping("/customer/find/set/all")
	public Set<Customer> findAllSetCustomer() {
		return customerService.findAllSetCustomer();
	}

	// Check whether an object is in the set or not
	@PostMapping("/customer/set/memeber")
	public boolean isSetMember(@RequestBody Customer customer) {
		return customerService.isSetMember(customer);
	}

	// Add an object to the HASH
	@PostMapping("/customer/hash/add")
	public void persistObjecHash(@RequestBody Customer customer) {
		customerService.persistCustomerToHash(customer);

	}

	// Retrieving all object from the HASH
	@GetMapping("/customer/find/hash/all")
	public Map<Object, Object> findAllHashCustomer() {
		return customerService.findAllHashCustomer();
	}

	// Update an object to the HASH
	@PostMapping("/customer/hash/update")
	public void updateHashCustomer(@RequestBody Customer customer) {
		customerService.updateCustomerTohash(customer);
	}

	// Retrieving all object from the HASH
	@GetMapping("/customer/find/hash/{id}")
	public Object findHashCustomer(@PathVariable Long id) {
		return customerService.findHashCustomer(id);
	}

	// Deleting an object
	@DeleteMapping("/customer/hash/delete/{id}")
	public void deleteObjectFromHash(@PathVariable Long id) {
		customerService.deleteFromHash(id);
	}

}
