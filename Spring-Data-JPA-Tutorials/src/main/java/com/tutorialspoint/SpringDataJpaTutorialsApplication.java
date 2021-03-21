package com.tutorialspoint;

import java.util.Date;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.tutorialspoint.entities.Product;
import com.tutorialspoint.entities.User;
import com.tutorialspoint.repositories.ProductRepository;
import com.tutorialspoint.repositories.UserRepositories;

@SpringBootApplication
public class SpringDataJpaTutorialsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaTutorialsApplication.class, args);
	}

	//@Bean
	ApplicationRunner applicationRunner(ProductRepository productRepository) {
		return args -> {

			Product product = new Product("IPhone", 70000.00, "IOS");
			Product product2 = new Product("Laptop", 90000.00, "Core i5");
			Product product3 = new Product("TV", 30000.00, "4k Smart");
			Product product4 = new Product("Bed", 40000.00, "King Size");
			Product product5 = new Product("Fridge", 25000.00, "Double Door");
			Product product6 = new Product("Wachine Machine", 50000.00, "Automatic");
			Product product7 = new Product("Xiomi Smart Phone", 45000.00, "Android");
			productRepository.save(product);
			productRepository.save(product2);
			productRepository.save(product3);
			productRepository.save(product4);
			productRepository.save(product5);
			productRepository.save(product6);
			productRepository.save(product7);

			// 1. Pagination with findAll() method
			Pageable pageable = PageRequest.of(0, 2);
			Page<Product> products = productRepository.findAll(pageable);
			products.stream().forEach(System.out::println);

			// 2. Sorting the records based on price, with findAll() method
			Sort sort = Sort.by("price");
			Iterable<Product> sortedProducts = productRepository.findAll(sort);// Default Ascending order
			sortedProducts.forEach(f -> System.out.println(f.getName() + " " + f.getPrice()));

			// 3. Sort in Descending order , with findAll() method
			Sort sortDesc = Sort.by(Direction.DESC, "price");
			Iterable<Product> sortedProductsDesc = productRepository.findAll(sortDesc);
			sortedProductsDesc.forEach(f -> System.out.println(f.getName() + " " + f.getPrice()));

			// 4. Sort by multiple properties in Descending order, with findAll() method
			Sort sortByMultiplePropDesc = Sort.by(Direction.DESC, "price", "name");
			Iterable<Product> sortByMultiplePropDescItr = productRepository.findAll(sortByMultiplePropDesc);
			sortByMultiplePropDescItr.forEach(f -> System.out.println(f.getName() + " " + f.getPrice()));

			// 5. Putting Paging and Sorting together, with findAll() method
			Pageable pageablewithSort = PageRequest.of(0, 2, Direction.DESC, "price");
			Page<Product> pageProduct = productRepository.findAll(pageablewithSort);
			pageProduct.stream().forEach(f -> System.out.println(f.getName() + "-" + f.getPrice()));

			// 6. Paging and Sorting with Query methods(custom finder methods)
			Pageable customPageablewithSort = PageRequest.of(0, 7, Direction.DESC, "name");
			List<Product> pageProductCustom = productRepository.findByNameContains("Phone", customPageablewithSort);
			pageProductCustom.stream().forEach(f -> System.out.println(f.getName() + "--" + f.getPrice()));
		};
	}

	@Bean
	ApplicationRunner applicationRunner(UserRepositories usersRepositories) {
		return args -> {

			User users1 = new User("Micheal", "Stoch", "stochk@xyz.com", 10000L, true, new Date());
			User users2 = new User("Benjamin", "Franklin", "franklin@xyz.com", 15000L, false, new Date());
			User users3 = new User("Rosoto", "Warner", "rosoto@xyz.com", 19000L, true, new Date());
			usersRepositories.save(users1);// persist user1
			usersRepositories.save(users2);// persist user2
			usersRepositories.save(users3);// persist user3 // Retrieve Users based on query methods
			System.out.println(usersRepositories.findByEmail("rosoto@xyz.com"));
			System.out.println(usersRepositories.findByFirstName("Micheal"));
			System.out.println(usersRepositories.findByFirstNameAndLastName("Benjamin", "Franklin"));
			System.out.println(usersRepositories.findBySalaryLessThan(15000L));
			System.out.println(usersRepositories.findByStartDateAfter(new Date()));// It shouldn't return any result

			usersRepositories.deleteUserByFirstName("Asad");
		};
	}
}
