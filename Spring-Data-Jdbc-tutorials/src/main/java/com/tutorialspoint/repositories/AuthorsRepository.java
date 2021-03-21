package com.tutorialspoint.repositories;

import org.springframework.data.repository.CrudRepository;

import com.tutorialspoint.entities.Authors;

public interface AuthorsRepository extends CrudRepository<Authors, Long> {

}
