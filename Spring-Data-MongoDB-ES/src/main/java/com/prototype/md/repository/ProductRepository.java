package com.prototype.md.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.prototype.md.document.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

	Product findByName(String string);

}
