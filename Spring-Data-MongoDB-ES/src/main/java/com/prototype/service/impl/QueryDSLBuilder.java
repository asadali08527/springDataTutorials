package com.prototype.service.impl;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prototype.es.document.Customer;
import com.prototype.es.repository.CustomerRepository;

@Service
public class QueryDSLBuilder {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	private CustomerRepository customerRepository;

	// Find all customer whose name or email is as per given text
	public List<Customer> fetchCustomerWithMultiTextMatch(String text) {
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.multiMatchQuery(text, "name", "email"));
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
		return elasticsearchTemplate.queryForList(searchQuery, Customer.class);
	}

	// Find all the customer whose name and salary is matched
	public List<Customer> fetchCustomerByMultiFieldMatch(String text, Double salary) {
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", text))
				.must(QueryBuilders.matchQuery("salary", salary));
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
		return elasticsearchTemplate.queryForList(searchQuery, Customer.class);
	}

	// Fetch all the customer whose name matches with given text
	public List<Customer> fetchCustomerByPartilaMatch(String text) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withFilter(QueryBuilders.regexpQuery("name", ".*" + text + ".*")).build();
		return elasticsearchTemplate.queryForList(searchQuery, Customer.class);
	}

	// Update a customer whose name is ? to ?
	@Transactional
	public void updateHavingName(String from, String to) {
		// First find the customer whose name is ?
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", from));
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
		List<Customer> customers = elasticsearchTemplate.queryForList(searchQuery, Customer.class);
		// Iterate through each customer whose name is ?
		for (Customer customer : customers) {
			// Update Customer name
			customer.setName(to);
			customerRepository.save(customer);
		}
	}

	// Delete a customer whose name is ?
	public void deleteByName(String name) {
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", name));
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
		List<Customer> customers = elasticsearchTemplate.queryForList(searchQuery, Customer.class);
		for (Customer customer : customers) {
			customerRepository.delete(customer);
		}
	}

}
