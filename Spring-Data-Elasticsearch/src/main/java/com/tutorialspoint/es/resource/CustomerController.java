package com.tutorialspoint.es.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorialspoint.es.document.Customer;
import com.tutorialspoint.es.service.CustomerService;

@RestController
@RequestMapping("/rest")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	// Persisting a customer to ElasticSearch
	@PostMapping("/customer/save")
	public Customer persistCustomer(@RequestBody Customer customer) {
		return customerService.save(customer);
	}

	// Retrieving a customer from ElasticSearch
	@GetMapping("/customer/find-by-id/{id}")
	public Customer fetchCustomer(@PathVariable Long id) {
		Optional<Customer> customerOpt = customerService.findById(id);
		return customerOpt.isPresent() ? customerOpt.get() : null;

	}

	// Deleting a customer from elasticsearch
	@DeleteMapping("/customer/delete/{id}")
	public void deleteObject(@PathVariable Long id) {
		customerService.deleteById(id);
	}

	// Retrieving a customer from ElasticSearch by Name
	@GetMapping("/customer/find-by-name/{name}")
	public List<Customer> fetchCustomerByNAme(@PathVariable String name) {
		Page<Customer> customerOpt = customerService.findByName(name);
		return customerOpt.get().collect(Collectors.toList());

	}

	// Retrieving a customer from ElasticSearch by City name
	@GetMapping("/customer/find-by-address-city/{city}")
	public List<Customer> fetchCustomerByCity(@PathVariable String city) {
		Page<Customer> customerOpt = customerService.findByAddressCity(city);
		return customerOpt.get().collect(Collectors.toList());

	}

	/*
	 * // Retrieving a customer from ElasticSearch whose name or email matches with
	 * given keywords
	 * 
	 * @GetMapping("/customer/multi-match/{text}") public List<Customer>
	 * fetchCustomerWithTextMatch(@PathVariable String text) { return
	 * queryDSLBuilder.fetchCustomerWithMultiTextMatch(text);
	 * 
	 * }
	 * 
	 * // Retrieving a customer from ElasticSearch whose name and salary are as per
	 * given search keywords
	 * 
	 * @GetMapping("/customer/multi-field/{name}/{salary}") public List<Customer>
	 * fetchCustomerByFieldMatch(@PathVariable String name, @PathVariable Double
	 * salary) { return queryDSLBuilder.fetchCustomerByMultiFieldMatch(name,
	 * salary);
	 * 
	 * }
	 * 
	 * 
	 * Retrieving a customer from ElasticSearch whose name matches fully or
	 * partially with given text
	 * 
	 * @GetMapping("/customer/partial-match/{text}") public List<Customer>
	 * fetchCustomerByPartilaMatch(@PathVariable String text) { return
	 * queryDSLBuilder.fetchCustomerByPartilaMatch(text);
	 * 
	 * }
	 * 
	 * // Updating a customer name from X to Y
	 * 
	 * @PostMapping("/customer/update/{from}/{to}") public void
	 * updateCustomerHavingTextInName(@PathVariable String from, @PathVariable
	 * String to) { queryDSLBuilder.updateHavingName(from, to); }
	 * 
	 * // Deleting a customer from elasticsearch by name
	 * 
	 * @DeleteMapping("/customer/delete/by-name/{name}") public void
	 * deleteCustometHavingName(@PathVariable String name) {
	 * queryDSLBuilder.deleteByName(name); }
	 */
}
