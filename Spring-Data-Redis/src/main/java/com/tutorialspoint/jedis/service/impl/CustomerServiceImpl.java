package com.tutorialspoint.jedis.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorialspoint.jedis.entity.Customer;
import com.tutorialspoint.jedis.repository.CustomerRepository;
import com.tutorialspoint.jedis.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public void setCustomerAsString(String key, Customer value) {
		customerRepository.setCustomerAsString(key, value);
	}

	@Override
	public String getCustomerAsString(String key) {
		return (String) customerRepository.getCustomerAsString(key);
	}

	@Override
	public void deleteById(String id) {
		customerRepository.deleteByKey(id);
	}

	@Override
	public void persistCustomerToList(Customer customer) {
		customerRepository.persistCustomerList(customer);
	}

	@Override
	public List<Customer> fetchAllCustomer() {
		return customerRepository.fetchAllCustomer();
	}

	@Override
	public void persistCustomerToSet(Customer customer) {
		customerRepository.persistCustomerSet(customer);
	}

	@Override
	public Set<Customer> findAllSetCustomer() {
		return customerRepository.fetchAllCustomerFromSet();
	}

	@Override
	public boolean isSetMember(Customer customer) {
		return customerRepository.isSetMember(customer);
	}

	// Hash related operations
	@Override
	public void deleteFromHash(Long id) {
		customerRepository.deleteCustomerHash(id);
	}

	@Override
	public Customer findHashCustomer(Long id) {
		Customer customer = customerRepository.findCustomerInHash(id);
		return customer;
	}

	@Override
	public void updateCustomerTohash(Customer customer) {
		customerRepository.updateCustomerHash(customer);

	}

	@Override
	public Map<Object, Object> findAllHashCustomer() {
		return customerRepository.findAllCustomerHash();
	}

	@Override
	public void persistCustomerToHash(Customer customer) {
		customerRepository.persistCustomeHash(customer);
	}

}
