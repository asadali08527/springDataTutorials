package com.tutorialspoint.es.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.tutorialspoint.es.document.Customer;

@Repository
public interface CustomerRepository extends ElasticsearchRepository<Customer, Long> {

	Page<Customer> findByName(String name, Pageable pageable);

	@Query("{\"bool\": {\"must\": [{\"match\": {\"addresses.city\": \"?0\"}}]}}")
	Page<Customer> findByAddressCityUsingCustomQuery(String name, Pageable pageable);

}
