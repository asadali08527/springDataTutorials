package com.tutorialspoint.jedis.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tutorialspoint.jedis.entity.Customer;
import com.tutorialspoint.jedis.repository.CustomerRepository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

	private final String CUSTOMER_LIST_KEY = "customer_list";
	private final String CUSTOMER_SET_KEY = "customer_set";
	private final String CUSTOMER_HASH_KEY = "customer_hash";

	@Autowired
	private RedisTemplate<String, Customer> redisTemplate;

	@Override
	public void setCustomerAsString(String key, Customer value) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key, 60, TimeUnit.SECONDS);
	}

	@Override
	public Object getCustomerAsString(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void deleteByKey(String key) {
		redisTemplate.expire(key, 0, TimeUnit.SECONDS);
	}

	// List Operations
	@Override
	public void persistCustomerList(Customer customer) {
		redisTemplate.opsForList().leftPush(CUSTOMER_LIST_KEY, customer);
	}

	@Override
	public List<Customer> fetchAllCustomer() {
		return redisTemplate.opsForList().range(CUSTOMER_LIST_KEY, 0, -1);
	}

	// Set Operations
	@Override
	public void persistCustomerSet(Customer customer) {
		// Add an object to given set
		redisTemplate.opsForSet().add(CUSTOMER_SET_KEY, customer);
	}

	@Override
	public Set<Customer> fetchAllCustomerFromSet() {
		// Fetch an object from a given set key
		return redisTemplate.opsForSet().members(CUSTOMER_SET_KEY);
	}

	// Hash operations
	@Override
	public boolean isSetMember(Customer customer) {
		// Check whether an object is part of the guven set or not?
		return redisTemplate.opsForSet().isMember(CUSTOMER_SET_KEY, customer);
	}

	@Override
	public void persistCustomeHash(Customer customer) {
		// Persisting an object to the hash
		redisTemplate.opsForHash().put(CUSTOMER_HASH_KEY, customer.getId(), customer);
	}

	@Override
	public void updateCustomerHash(Customer customer) {
		// Updating an object to the hash
		redisTemplate.opsForHash().put(CUSTOMER_HASH_KEY, customer.getId(), customer);

	}

	@Override
	public Map<Object, Object> findAllCustomerHash() {
		// Finding all customer in Hash
		return redisTemplate.opsForHash().entries(CUSTOMER_HASH_KEY);
	}

	@Override
	public Customer findCustomerInHash(Long id) {
		// Find a customer from Hash based on id
		return (Customer) redisTemplate.opsForHash().get(CUSTOMER_HASH_KEY, id);
	}

	@Override
	public void deleteCustomerHash(Long id) {
		// Delete a customer from Hash
		redisTemplate.opsForHash().delete(CUSTOMER_HASH_KEY, id);
	}

}
