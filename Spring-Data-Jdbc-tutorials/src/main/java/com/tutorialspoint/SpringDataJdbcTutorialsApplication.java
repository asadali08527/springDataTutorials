package com.tutorialspoint;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tutorialspoint.entities.Address;
import com.tutorialspoint.entities.Authors;
import com.tutorialspoint.entities.Books;
import com.tutorialspoint.entities.Cineplex;
import com.tutorialspoint.entities.Users;
import com.tutorialspoint.repositories.AuthorsRepository;
import com.tutorialspoint.repositories.BooksRepository;
import com.tutorialspoint.repositories.CineplexRepository;
import com.tutorialspoint.repositories.UsersRepositories;

@SpringBootApplication
public class SpringDataJdbcTutorialsApplication {
	@Autowired
	private AuthorsRepository authorsRepository;

	@Autowired
	private BooksRepository booksRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJdbcTutorialsApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(CineplexRepository cineplexRepository) {
		return args -> {
			System.out.println(cineplexRepository.findById(676l));
			// cineplexRepository.findAll().forEach(System.out::println);

			/*
			 * Cineplex cineplex = new Cineplex(); cineplex.setId(676l);
			 * cineplex.setLeadActor("hasjf"); cineplex.setMovieDuration(2);
			 * cineplex.setMovieTitle("Hulk"); cineplex.setTicketPrice(55.0);
			 * System.out.println(cineplexRepository.save(cineplex));
			 */

		};

	}
}
