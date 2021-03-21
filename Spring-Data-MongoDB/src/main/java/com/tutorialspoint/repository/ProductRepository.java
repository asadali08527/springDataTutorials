package com.tutorialspoint.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tutorialspoint.document.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

	Product findByName(String string);

}
