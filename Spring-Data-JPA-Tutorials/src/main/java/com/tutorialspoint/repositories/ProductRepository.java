package com.tutorialspoint.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tutorialspoint.entities.Product;
import java.lang.String;
import java.util.List;
import java.util.Optional;
import java.lang.Long;
import java.lang.Double;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	Optional<Product> findById(Long id);

	List<Product> findByName(String name, Pageable pageable);

	List<Product> findByIdIn(List<Long> ids, Pageable pageable);

	List<Product> findByNameAndFeatures(String name, String features, Pageable pageable);

	List<Product> findByPriceGreaterThan(Double price, Pageable pageable);

	List<Product> findByPriceBetween(Double price1, Double price2, Pageable pageable);

	List<Product> findByNameContains(String features, Pageable pageable);

	List<Product> findByNameLike(String features, Pageable pageable);

}
