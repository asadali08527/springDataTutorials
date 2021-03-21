package com.tutorialspoint.jedis.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tutorialspoint.jedis.entity.Customer;

public interface CustomerRepository {
	// Working with Strings
	void setCustomerAsString(String key, Customer value);

	Object getCustomerAsString(String key);

	void deleteByKey(String id);

	// Working with List
	void persistCustomerList(Customer customer);

	List<Customer> fetchAllCustomer();

	// Working with Set

	void persistCustomerSet(Customer customer);

	Set<Customer> fetchAllCustomerFromSet();

	boolean isSetMember(Customer customer);

	// Working with Hash
	void persistCustomeHash(Customer customer);

	void updateCustomerHash(Customer customer);

	Map<Object, Object> findAllCustomerHash();

	Customer findCustomerInHash(Long id);

	void deleteCustomerHash(Long id);

}
