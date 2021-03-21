package com.tutorialspoint.repositories;

import org.springframework.data.repository.CrudRepository;

import com.tutorialspoint.entities.Books;

public interface BooksRepository extends CrudRepository<Books, Long> {

}
