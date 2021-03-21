package com.tutorialspoint.repositories;

import org.springframework.data.repository.CrudRepository;

import com.tutorialspoint.entities.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

}
