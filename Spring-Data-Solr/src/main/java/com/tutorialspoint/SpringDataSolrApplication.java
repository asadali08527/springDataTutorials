package com.tutorialspoint;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;

import com.tutorialspoint.entity.Users;
import com.tutorialspoint.repository.UsersRepository;

@SpringBootApplication
public class SpringDataSolrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataSolrApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UsersRepository usersRepository) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {

				/*
				 * // Creating some entries Users user1 = usersRepository.save(new Users(1l,
				 * "Kallis")); System.out.println(usersRepository.save(user1)); Users user2 =
				 * usersRepository.save(new Users(2l, "Mills")); System.out.println(user2);
				 * Users user3 = usersRepository.save(new Users(3l, "Wilson"));
				 * System.out.println(user3);
				 * 
				 * // Fetching entry System.out.println(usersRepository.findById(2l)); // Find
				 * all entry usersRepository.findAll().forEach(System.out::println); // Update
				 * entry Optional<Users> usersrOptional = usersRepository.findById(3l); if
				 * (usersrOptional.isPresent()) { Users user = usersrOptional.get();
				 * user.setName("Wilson Monk"); usersRepository.save(user); }
				 * System.out.println(usersRepository.findByName("Wilson Monk")); // Deleting
				 * entry
				 * 
				 * usersRepository.delete(user2);
				 * 
				 * // fetch all Entry usersRepository.findAll().forEach(System.out::println);
				 */

				// usersRepository.findByCustomQuery("Kallis", PageRequest.of(0,
				// 5)).forEach(System.out::println);
				usersRepository.findByNamedQuery("Wilson", PageRequest.of(0, 5)).forEach(System.out::println);
			}
		};

	}

}
