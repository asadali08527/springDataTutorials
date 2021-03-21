package com.tutorialspoint;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tutorialspoint.entities.Authors;
import com.tutorialspoint.entities.Books;
import com.tutorialspoint.repositories.AuthorsRepository;
import com.tutorialspoint.repositories.BooksRepository;

@SpringBootTest
class SpringDataJdbcTutorialsApplicationTests {

	@Autowired
	private AuthorsRepository authorsRepository;

	@Autowired
	private BooksRepository booksRepository;

	@Test
	void contextLoads() {

		Authors authors = new Authors();
		authors.setName("Asad Ali");
		System.out.println(authorsRepository.save(authors));

		Books books = new Books();
		books.setTitle("An Introduction to Spring Data JDBC");
		books.setIsbn("5261327");
		books.addAuthor(authors);
		System.out.println(booksRepository.save(books));

		System.out.println(booksRepository.findAll());

	}

}
