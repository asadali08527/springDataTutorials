package com.tutorialspoint.jedis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tutorialspoint.jedis.entity.Customer;

public interface CustomerService {

	void setCustomerAsString(String key, Customer value);

	String getCustomerAsString(String key);

	void deleteById(String id);

	void persistCustomerToList(Customer customer);

	List<Customer> fetchAllCustomer();

	void persistCustomerToSet(Customer customer);

	Set<Customer> findAllSetCustomer();

	boolean isSetMember(Customer customer);

	void deleteFromHash(Long id);

	Customer findHashCustomer(Long id);

	void updateCustomerTohash(Customer customer);

	Map<Object, Object> findAllHashCustomer();

	void persistCustomerToHash(Customer customer);

}
