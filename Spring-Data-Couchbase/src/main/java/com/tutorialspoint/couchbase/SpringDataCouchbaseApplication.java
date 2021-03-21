package com.tutorialspoint.couchbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import com.couchbase.client.java.view.ViewQuery;
import com.tutorialspoint.couchbase.document.Customer;
import com.tutorialspoint.couchbase.repository.CustomerRepository;

@SpringBootApplication
public class SpringDataCouchbaseApplication {

	private static final String DESIGN_DOC = "customer";

	@Autowired
	private CouchbaseTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(SpringDataCouchbaseApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {

				// Creating some documents
				template.insert(new Customer(105l, "John", "john@yahoo.com", 12000d));
				template.insert(new Customer(106l, "Franklin", "franklin@yahoo.com", 32000d));
				template.insert(new Customer(103l, "Simon", "simon@yahoo.com", 34000d));
				template.insert(new Customer(107l, "Cark", "clark@yahoo.com", 54000d));

				// Fetching documents
				System.out.println(template.findByView(ViewQuery.from(DESIGN_DOC, "all"), Customer.class));
				System.out.println(template.findByView(ViewQuery.from(DESIGN_DOC, "byEmail"), Customer.class));

				// Update document
				Customer customer = template.findById("103", Customer.class);
				customer.setSalary(55000d);
				customer.setName("Simon Ford");
				template.save(customer);

				// Deleting Document
				Customer customer7 = template.findById("107", Customer.class);
				template.remove(customer7);

			}
		};

	}
}
