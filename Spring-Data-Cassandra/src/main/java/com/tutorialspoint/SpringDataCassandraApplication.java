package com.tutorialspoint;

import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tutorialspoint.entity.Customer;
import com.tutorialspoint.entity.User;
import com.tutorialspoint.entity.UserKey;
import com.tutorialspoint.repository.CustomerRepository;
import com.tutorialspoint.repository.UserRepository;

@SpringBootApplication
public class SpringDataCassandraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataCassandraApplication.class, args);
	}

	/*
	 * @Bean CommandLineRunner commandLineRunner(CustomerRepository
	 * customerRepository) { return new CommandLineRunner() {
	 * 
	 * @Override public void run(String... args) throws Exception {
	 * 
	 * // Creating some entries Customer customer1 = customerRepository.save(new
	 * Customer(1l, "Asad"));
	 * System.out.println(customerRepository.save(customer1)); Customer customer2 =
	 * customerRepository.save(new Customer(2l, "Ali"));
	 * System.out.println(customer2); Customer customer3 =
	 * customerRepository.save(new Customer(3l, "John"));
	 * System.out.println(customer3);
	 * 
	 * // Fetching entry System.out.println(customerRepository.findByName("Asad"));
	 * // Find all entry System.out.println(customerRepository.findAll()); // Update
	 * entry Optional<Customer> customerOptional = customerRepository.findById(3l);
	 * if (customerOptional.isPresent()) { Customer customer =
	 * customerOptional.get(); customer.setName("John Montek");
	 * System.out.println(customerRepository.save(customer)); }
	 * 
	 * // Deleting entry customerRepository.delete(customer2); // fetch all Entry
	 * System.out.println(customerRepository.findAll());
	 * 
	 * } };
	 * 
	 * }
	 */

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {

				// Creating entries into the database
				final UserKey key1 = new UserKey("Miller", 81000d, UUID.randomUUID());
				final User user1 = new User(key1, "John", "john@tutorialspoint.com");
				userRepository.insert(user1);
				final UserKey key2 = new UserKey("Monty", 85000d, UUID.randomUUID());
				final User user2 = new User(key2, "Carlos", "carlos@tutorialspoint.com");
				userRepository.insert(user2);

				final UserKey key3 = new UserKey("Benjamin", 95000d, UUID.randomUUID());
				final User user3 = new User(key3, "Franklin", "franklin@tutorialspoint.com");
				userRepository.insert(user3);
				// Fetching entry by last name
				userRepository.findByKeyLastName("Miller").forEach(System.out::println);
				// Find entry by last name and salary greater than
				userRepository.findByKeyLastNameAndKeySalaryGreaterThan("Monty", 81000d).forEach(System.out::println);

				// find entry by first name
				userRepository.findByFirstName("Franklin").forEach(System.out::println);

			}
		};

	}
}
