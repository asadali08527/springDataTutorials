TABLE OF CONTENTS
- --
- Overview
- Prerequisites
- Introduction
- What is MongoDB?
    + Installation Guide
- Getting Started
    + Adding Dependencies
    + Creating Collection
    + Creating Repository
    + Configuring the DataSource 
    + Performing CRUD Operation
- Query Methods
- Annotations
- Exposing REST end points
- Relationship
- Conclusion

# Overview
In this tutorial we will learn to integrate our Spring Data-based application with MongoDB i.e., a document database. We will perform the CRUD operations using MongoRepository. We will also focus some light on Query methods used by MongoRepository and we will see how the custom query methods used in MongoRepository are different from our JPA based repositories. Not only this, but we will also expose some of the rest endpoints to access data through it.
# Prerequisites
•	About 30 minutes
•	Basic Spring Data JPA knowledge
•	A java based IDE (Eclipse, STS or IntelliJ IDEA)
•	JDK 1.8 or later
•	Gradle 4+ or Maven 3.2+
# Introduction
As we know that, the Spring Data framework is the umbrella project which contains many sub-frameworks. All these sub frameworks deal with data access which is specific to a database. Spring Data MongoDB is one of the sub-framework of the Spring Data project, that provides access to the MongoDB document database. Before jumping the Spring Data MongoDb, let's have a basic understanding of MongoDB.
# What is MongoDB?
MongoDB is a document-based NoSQL database. It stores the information in key-value format, more specifically like a JSON document. We use MongoDB for storing a high volume of data. On a high-level, MongoDB uses terms like `Database`, `Collection`, `Document`, and `Field`.
#### Database: 
It is equivalent to the RDBMS database which acts as a container for collections.
#### Collection: 
It exists within a database and itis equivalent to the tables in RDBMS.
#### Document: 
It is a equivalent to a row in RDBMS. A document in MongoDB collection is a single record.
#### Field: 
It is a equivalent to a column in RDBMS having a key-value pair. A document could have zero or more fields. 
To learn more about MongoDB, visit our tutorials by clicking [here](https://www.tutorialspoint.com/mongodb/index.htm). 
## Installation Guide
Since this tutorial will be working extensively with MongoDB. Make sure it is installed on your machine, If not you can install it from [here](https://www.mongodb.com/download-center/community) based on your system configuration. While installing make sure to install the `Compass` tool. It is a client with a nice GUI to interact with MongoDB server. Once it is installed you can create a database(say `tutorials_point`), by providing a collection name(say `customers`) through Compass GUI, finally it will look like:
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data MongoDB\Compass.png)
# Getting Started
Like other Spring-based projects, you can start from scratch by creating a maven or Gradle based project from your favorite IDE. Follow below step by step process or you can bypass the basic setup steps that are already familiar with.
## Adding Dependencies.
If you have created normal Maven or Gradle projects then add below dependencies to your pom.
For Maven
```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```
Above are two Spring `Web` and Spring Data `MongoDB` dependences. If you have created your project as Spring Boot or Starting with Spring Initializr then your final list of dependencies wil look like this:
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data MongoDB\Dependencies.png)

Note- The code sample and exapmles used in this tutorial has been created through Spring Initializr. The following is your final pom.xml file that is created when you choose Maven:
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.2.6.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.tutorialspoint</groupId>
	<artifactId>Spring-Data-MongoDB</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>Spring-Data-MongoDB</name>
	<description>Spring Data MongoDB project using Spring Boot</description>
	<properties>
		<java.version>1.8</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>
```
## Creating Collection
A collection is equivakent to a model, it will be created as a POJO. Let's define our first collection:
```
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer {
	@Id
	private Long id;
	private String name;
    @Indexed(unique = true)
	private String email;
    @Indexed(direction = IndexDirection.DESCENDING)
	private Double salary;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", salary=" + salary + "]";
	}

}
```
`@Document`: Unlike JPA where we used to mark our domain object with annotation `@Entity`, here we mark it with `@Document`. If we do not specify a collection attribute, by default, a collection with the class name((first character lowercased) will get created i.e, customer. If we want to specify some other name we can add attribute `collection` to the @Document annotation.
`@Id`: This is for identity purposes.
`@Indexed(unique = true)`: @Indexed annotation is used to mark the field as Indexed in the MongoDB. Here, it will index the email with a unique attribute.
`@Indexed(direction = IndexDirection.DESCENDING)`: This is applied to the field that will be indexed in a descending order. Similarly we can use `IndexDirection.ASCENDING` for ascending order.
## Creating a Repository
Let's define an interface which will be our repository:
```
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tutorialspoint.document.Customer;

public interface CustomerRepository extends MongoRepository<Customer, Long> {

}
```
The process of creating a repository is similar to the repository creation in any Spring data-based project, the only difference here is that it extends `MongoRepository` from `org.springframework.data.mongodb.repository`, which works on top of `CrudRepository`. 
## Configuring the DataSource
Let's define our MongoDB related details in `application.properties` file.
If our MongoDB server is clustered then we can specify its url as follows
```
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster-name>-<instance-id>/<database-name>?retryWrites=true
```
If it is on our local machine the configuration will go like this.
```
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=tutorials_point
spring.data.mongodb.username=test
spring.data.mongodb.password=test
```
## Performing CRUD Operation
Now let's perform below some CRUD operation. We will try adding some customers to the above document, and retrieve some of them by their id or name. Following is the code for the same.
```
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tutorialspoint.document.Customer;
import com.tutorialspoint.repository.CustomerRepository;

@SpringBootApplication
public class SpringDataMongoDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataMongoDbApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
			    //Persist customers
				System.out.println(customerRepository.save(new Customer(1l, "Johnson", "john@yahoo.co", 10000d)));
				System.out.println(customerRepository.save(new Customer(2l, "Kallis", "kallis@yahoo.co", 20000d)));
				// Fetch customer by Id
				System.out.println(customerRepository.findById(1l));
			}
		};
	}
}
```
The above code has used CommandLineRunner which will be executed on application startup. We have created two customers and saved them in the database and executed the method `findById()` to see if it is actually finding the correct one. Let's run the application as the Spring Boot App, below is the output.
> Customer [id=1, name=Johnson, email=john@yahoo.co, salary=10000.0]
Customer [id=2, name=Kallis, email=kallis@yahoo.co, salary=20000.0]
Optional[Customer [id=1, name=Johnson, email=john@yahoo.co, salary=10000.0]]

# Query Methods
Since MongoDBRepository works on top of CrudRepository, so we have access to all the query methods as well as custom query methods. As per business requirements, we can add some custom query methods to our customer repository:
```
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tutorialspoint.document.Customer;

public interface CustomerRepository extends MongoRepository<Customer, Long> {

	Optional<Customer> findByEmail(String email);

	List<Customer> findBySalaryGreaterThanEqual(double salary);

	List<Customer> findBysalaryBetween(double from, double to);

}
```
We have added three query methods, which helps in finding the customers based on email and salary. Let's test these query methods by adding some code to `CommandLineRunner`.
```
@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
			    // Fetch by email
				System.out.println(customerRepository.findByEmail("john@yahoo.co"));
			    // fetch whose salary is =>20000
				System.out.println(customerRepository.findBySalaryGreaterThanEqual(20000));
			   // fetch whose salary is in between 1000 to 12000
				System.out.println(customerRepository.findBysalaryBetween(1000, 12000));
			}
		};
	}
```
In the above code, we are trying to fetch a customer by email and salary, it will print the result as follows:
```
Optional[Customer [id=1, name=Johnson, email=john@yahoo.co, salary=10000.0]]
[Customer [id=2, name=Kallis, email=kallis@yahoo.co, salary=20000.0]]
[Customer [id=1, name=Johnson, email=john@yahoo.co, salary=10000.0]]
```
# Annotations
Spring Data MongoDB uses various annotations, let's discuss them in details
## Indexes related Annotations
This type of annotation talks about indexing the fields
1. `@Indexed`
We have used this annotation above in our Customer document, 
2. `@CompoundIndexes`
Spring Data MongoDb also supports `compound indexes`. It s used at Documents level to hold references to multiple fields using single index.
```
@Document
@CompoundIndexes({ @CompoundIndex(name = "name_salary", def = "{'name.id':1, 'salary':1}") })
public class Customer {
}
```
In the above code we have created the compound index with name and salary fields.
## Common Annotations
1. `@Transient`
As we can guess, The fields which are annotated as `@Transient` are not part of persistence and they are excluded from being pushed into the database. For instance
```
public class Customer {
     
    @Transient
    private String gender;
}
```
The gender will not be persisted into the database.
2. `@Field`
It is used to give a name to the field in the JSON document, We can treat this as the name attribute of `@Column` annotation of JPA. If we want a JSON document field to be saved other than our java field name, then we use this annotation, For Instance.
```
@Field("dob")
private Date dateOfBirth;
```
Here, our Java attribute is `dateOfBirth` but in the database it will be dob
# Exposing REST endpoints
To do so, let's define a controller and some mapping of the endpoints.
```
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorialspoint.document.Customer;
import com.tutorialspoint.repository.CustomerRepository;

@RestController
@RequestMapping("/rest")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customers")
	public List<Customer> fetchCustomers() {
		return customerRepository.findAll();
	}

	@PostMapping("/customer/add")
	public void persisDocument(@RequestBody Customer customer) {
		customerRepository.save(customer);
	}

	@GetMapping("/customer/find/{id}")
	public Optional<Customer> fetchDocument(@PathVariable Long id) {
		return customerRepository.findById(id);
	}

	@DeleteMapping("/customer/{id}")
	public void deleteDocument(@PathVariable Long id) {
		customerRepository.deleteById(id);
	}
}
```
The above controller have different mapping methods, responsible for fetching[GET], adding[POST], finding[GET] and deleting[DELETE] the customer from data source. 
Let's add a customer to the data source, It will be a post call with Customer details in the body. Our endpoint url will be `http://localhost:8080/rest/customer/add` and pass the below data in the body.
```
{
	"id":3,
	"name":"Asad",
	"email":"asad@gmail.com",
	"salary":28500
}
```
The api will return response code `200[OK]`.
Now, let's first find out all the customers availble in the document. To do so we can use Postman client. Let's execute rest end point `http://localhost:8080/rest/customers`
through postman:
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data MongoDB\Postman.png)
```
[
    {
        "id": 1,
        "name": "Johnson",
        "email": "john@yahoo.co",
        "salary": 10000.0
    },
    {
        "id": 2,
        "name": "Kallis",
        "email": "kallis@yahoo.co",
        "salary": 20000.0
    },
    {
        "id": 3,
        "name": "Asad",
        "email": "asad@gmail.com",
        "salary": 28500.0
    }
]
```
Similarly we can find a customer by passing an id of customer, let's find the customer with id=2, our rest end point will be `http://localhost:8080/rest/customer/find/2`, and the output will be:
```
{
    "id": 2,
    "name": "Kallis",
    "email": "kallis@yahoo.co",
    "salary": 20000.0
}
```
Now, let's try deleting a resource by passing customer id and invikoing DELETE method with rest end point `http://localhost:8080/rest/customer/3` and it has been deleted.
# Relationships
Let's discuss a use case where a customer can purchase multiple products, based on review of a product. Also in this case, consider a customer has only one address to ship the product. Below is the ER diagram for this use case, We will discuss this relationship use case through Spring Data MongoDB.
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data MongoDB\Customer Product Review.png)

Let's re-write the Customer document with other depenedent class, which will be as follows.
## Root Document Customer
```
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer {
	@Id
	private String id;
	private String name;
	@Indexed(unique = true)
	private String email;
	private Address address;
	private List<Product> products;

	public Customer() {

		this.products = new ArrayList<Product>();
	}

	public Customer(String name, String email, Address address, List<Product> products) {
		this.name = name;
		this.email = email;
		this.address = address;
		this.products = products;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + ", products="
				+ products + "]";
	}
}
```
### Address class
```
public class Address {
	private String city;
	private String country;
	private Integer zipCode;

	public Address() {
	}

	public Address(String city, String country, Integer zipCode) {
		this.city = city;
		this.country = country;
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Address [city=" + city + ", country=" + country + ", zipCode=" + zipCode + "]";
	}
}
```
## Product Document
```
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {
	@Id
	private String id;
	private String name;
	private Double price;
	private List<Review> reviews;

	public Product() {
	}

	public Product(String name, Double price, List<Review> reviews) {
		this.name = name;
		this.price = price;
		this.reviews = reviews;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", reviews=" + reviews + "]";
	}
}
```
### Review Class
```
public class Review {

	private String customerName;
	private Integer rating;
	private boolean approved;

	public Review() {
	}

	public Review(String customerName, Integer rating, boolean approved) {
		this.customerName = customerName;
		this.rating = rating;
		this.approved = approved;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "Review [ customerName=" + customerName + ", rating=" + rating + ", approved=" + approved + "]";
	}

}
```
## Customer Repository
```
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tutorialspoint.document.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	Optional<Customer> findByEmail(String email);

}
```
## Product Repository
```
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tutorialspoint.document.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	Product findByName(String string);
}
```
If you observe, we have not created a repository for Address and Reviews, because Spring Data MongoDb is a part of the Spring Data project and the complete Spring data project work on the aggregate root principle of DDD.
Since the address is the part of the Customer document, and Review is part of the Product document, so we are required to create one repository per aggregate root. If we want we can skip the Product repository as well, and it still works.

In the above long list of documents, unlike our JPA based application we have not used any annotation for representing a relationship like @OneToOne or @OneToMany. Spring Data MongoDB doesn't entertain such annotations, and it understand the relationship based on context.
We can find in the above documents that a customer can have an address and many products. Also a product could have many reviews. Let's try persisting some data and fetching it out. To do so we will be using `CommandLineRunner`.
```
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
		    	// Persist a product with review
				Product product = new Product("IpHone", 60000.0d, Arrays.asList(new Review("asad", 4, true)));
				product = productRepository.save(product);
				// Persist a customer with address and product
				Customer customer = new Customer("Asad Ali", "asad@gmail.com", new Address("Hyd", "India", 122001),
						Arrays.asList(product));
				customer = customerRepository.save(customer);
				// Fetch the customer
				System.out.println(customer);
			}
		};
	}
}
```
OUTPUT
> Customer [id=5eb327768442b8385bd4bb1a, name=Asad Ali, email=asad@gmail.com, address=Address [city=Hyd, country=India, zipCode=122001], products=[Product [id=5eb327768442b8385bd4bb19, name=IpHone, price=60000.0, reviews=[Review [ customerName=asad, rating=4, approved=true]]]]]
Let's perform one more insert and retrieve operation to the above documents:

```
@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository, ProductRepository productRepository) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				Product samsung = new Product("Samsung", 50000.0d, Arrays.asList(new Review("ali", 5, true)));
				samsung = productRepository.save(samsung);
				//Fetch Old record with name Iphone
				Product product = productRepository.findByName("IpHone");
				// Save multiple two products for this user
				Customer customer = new Customer("Ali", "ali@gmail.com", new Address("Gurgaon", "India", 122018),
						Arrays.asList(samsung, product));
				customer = customerRepository.save(customer);
				System.out.println(customer);
				// Find by Email
				System.out.println(customerRepository.findByEmail("asad@gmail.com"));
			}
		};
	}
```
OUTPUT
> Customer [id=5eb32bd590ac0c3e200677ea, name=Ali, email=ali@gmail.com, address=Address [city=Gurgaon, country=India, zipCode=122018], products=[Product [id=5eb32bd490ac0c3e200677e9, name=Samsung, price=50000.0, reviews=[Review [ customerName=ali, rating=5, approved=true]]], Product [id=5eb327768442b8385bd4bb19, name=IpHone, price=60000.0, reviews=[Review [ customerName=asad, rating=4, approved=true]]]]]

> Optional[Customer [id=5eb327768442b8385bd4bb1a, name=Asad Ali, email=asad@gmail.com, address=Address [city=Hyd, country=India, zipCode=122001], products=[Product [id=5eb327768442b8385bd4bb19, name=IpHone, price=60000.0, reviews=[Review [ customerName=asad, rating=4, approved=true]]]]]]

# Conclusion
So far we learned, how does Spring Data MongoDB is useful in working with MongoDB. We have created a project which connected with the MongoDB server, and performed some CRUD operation. We also exposed some of the rest endpoints and performed restful operations.