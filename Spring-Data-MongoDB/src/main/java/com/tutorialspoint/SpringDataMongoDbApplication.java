package com.tutorialspoint;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tutorialspoint.document.Address;
import com.tutorialspoint.document.Customer;
import com.tutorialspoint.document.Product;
import com.tutorialspoint.document.Review;
import com.tutorialspoint.repository.CustomerRepository;
import com.tutorialspoint.repository.ProductRepository;

@SpringBootApplication
public class SpringDataMongoDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataMongoDbApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository, ProductRepository productRepository) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				/*
				 * Product samsung = new Product("Samsung", 50000.0d, Arrays.asList(new
				 * Review("ali", 5, true))); samsung = productRepository.save(samsung); Product
				 * product = productRepository.findByName("IpHone"); Customer customer = new
				 * Customer("Ali", "ali@gmail.com", new Address("Gurgaon", "India", 122018),
				 * Arrays.asList(samsung, product)); customer =
				 * customerRepository.save(customer);
				 */
				System.out.println(customerRepository.findByEmail("ali@gmail.com"));
				System.out.println(customerRepository.findByEmail("asad@gmail.com"));

			}
		};

	}

}
