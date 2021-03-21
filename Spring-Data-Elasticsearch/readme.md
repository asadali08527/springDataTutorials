TABLE OF CONTENTS
- --
- Overview
- Prerequisites
- Introduction
- What is Elasticsearch?
    + Installation Guide
- Getting Started
    + Adding Dependencies
    + Creating a Customer Entity
    + Creating a Customer Repository 
    + Accessing Data Using ElasticSearch from Customer Repository
        + Persisting an Object
        + Retrieving an Object
        + Deleting an Object
- Querying
    + Query Methods
    + Custom Query Methods
    + Hands on Coding for Query and Custom Query Methods
- Configuring ElasticsearchOperations bean
    + Working with QueryDSL
        + Multi-Match search
        + Multi-Field Search
        + WildCard(Partial) Search
        + Updating an Object
        + Deleting an Object
- Features
- Conclusion

# Overview
In this tutorial we learn about Spring Data Elasticsearch. We will go through the basics of Elasticsearch and integrate it with Spring Data Project. We will code and practice the core and advance concepts used in Spring Data Elasticsearch. 
# Prerequisites
•	About 15 minutes
•	Basic Spring Data framework knowledge
•	Basic understanding of Elasticsearch
•	A java based IDE (Eclipse, STS or IntelliJ IDEA)
•	JDK 1.8 or later
•	Gradle 4+ or Maven 3.2+
# Introduction
Spring Data Elasticsearch, is a part of the Spring Data project. It provides integration with the Elasticsearch engine. It uses POJO centric model to interact with Elasticsearch documents. Before jumping in the depth of the tutorial, let's have a basic understanding of Elasticsearch.
# What is Elasticsearch?
Elasticsearch is a search engine, based on Apache Lucene library. It is an open-source distributed full-text search engine. It is accessible using the HTTP web interface and uses schema-free JSON documents. It is developed in Java and license under the Apache license. To learn more about ElasticSearch, read our Elasticsearch tutorial by clicking [here.](https://www.tutorialspoint.com/elasticsearch/index.htm)
## Installation Guide
This tutorial assumes that Elastic search is installed on the machine and its running. If not, you can download it from [here.](https://www.elastic.co/downloads/elasticsearch) Once it is downloaded navigate to the config folder and change the cluster name and path data. However it is optional. Finally start/run the elastic search server. You can also follow our installation guide by clicking [here.](https://www.tutorialspoint.com/elasticsearch/elasticsearch_installation.htm)
# Getting Started
We can create a Spring project in our favorite IDE. You can create a Spring or Spring Boot based project through IDE. The code sample and examples used in this tutorial has been created through Spring Initializr. If you have created normal Maven or Gradle projects then add below dependencies(i.e. `Spring Web` and `Spring Data Elasticsearch (Access+Driver)` to your pom or Gradle file.
For Maven

```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

Above are two Spring `Web` and Spring Data `Elasticsearch` dependences. If you have created your project as Spring Boot or Starting with Spring Initializr then your final list of dependencies wil look like this:

![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Elasticsearch\Dependencies.png)

The following is our final pom.xml file that is created when you choose Maven:

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.2.7.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.tutorialspoint</groupId>
	<artifactId>Spring-Data-Elasticsearch</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>Spring-Data-Elasticsearch</name>
	<description>Spring Data Elasticsearchproject using Spring Boot</description>
	<properties>
		<java.version>1.8</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-elasticsearch</artifactId>
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

## Creating a Customer Entity
An entity in Elasticsearch context is called Document. Le's create it using annotation `@Document` from `org.springframework.data.elasticsearch.annotations`.

```
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "tutorials_point", type = "customer", shards = 3)
public class Customer {
	@Id
	private Long id;
	private String name;
	private String email;
	private Double salary;
	@Field(type = FieldType.Nested, includeInParent = true)
	private List<Address> addresses;

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

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", salary=" + salary + ", addresses="
				+ addresses + "]";
	}
}
```

We can observe that the annotation Document contains some other properties such as an index, type and shard. It simply means that the instance of Customer class will be stored in the Elasticsearch under an index called tutorials_point, and document type will be customer with sharding value 3.
Coming to the field of Customer class, it has @Id from `org.springframework.data.annotation` and a new annotation called `@Field` from `org.springframework.data.elasticsearch.annotations`. It has two attributes defined first one is `type` and other one is `includeInParent`. which means, at the time of indexing in elastic search, the object of the  associated class `Address` will be embedded in `Customer`.
## Creating a Customer Repository
Repostory creation is similar to other Spring Data projects, the only difference here is that, we need to extend `ElasticsearchRepository` which works on top of `ElasticsearchCrudRepository` which in turn works on top of `PagingAndSortingRepository`.

```
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.tutorialspoint.es.document.Customer;

@Repository
public interface CustomerRepository extends ElasticsearchRepository<Customer, Long> {

}
```

## Accessing Data Using ElasticSearch from Customer Repository
Let's define a controller to perform some read and write operation to the Elastic search through REST APIs.

```
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorialspoint.es.document.Customer;
import com.tutorialspoint.es.service.CustomerService;

@RestController
@RequestMapping("/rest")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	// Persisting a customer to ElasticSearch
	@PostMapping("/customer/save")
	public Customer persistCustomer(@RequestBody Customer customer) {
		return customerService.save(customer);
	}

	// Retrieving a customer from ElasticSearch
	@GetMapping("/customer/find-by-id/{id}")
	public Customer fetchCustomer(@PathVariable Long id) {
		Optional<Customer> customerOpt = customerService.findById(id);
		return customerOpt.isPresent() ? customerOpt.get() : null;

	}

	// Deleting a customer from elasticsearch
	@DeleteMapping("/customer/delete/{id}")
	public void deleteObject(@PathVariable Long id) {
		customerService.deleteById(id);
	}
}	
```

Let's Define a Service for this.

```
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorialspoint.es.document.Customer;
import com.tutorialspoint.es.repository.CustomerRepository;
import com.tutorialspoint.es.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer save(Customer customer) {
		return customer = customerRepository.save(customer);
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}
	
	@Override
	public void deleteById(Long id) {
		customerRepository.deleteById(id);		
	}
}
```

### Persisting an Obect
Since our code is ready, we can launch our application, make sure elasticsearch server is configured, installed and running on machine. Let's try pushing some customer details into the elastic search through rest end points `http://localhost:8080/rest/customer/save`. It will be a POST call with below body:
```
    {
        "id": 1,
        "name": "Jack",
        "email": "jack@yahoo.com",
        "salary": 18100.0,
        "addresses": [
            {
                "city": "Mumbai",
                "country": "India",
                "zipCode": 111111
            }
        ]
    }
```

Congratulation, it has been pushed and we received `200[OK]` as response. Lets add one more:

```
    {
        "id": 2,
        "name": "Ma",
        "email": "ma@yahoo.com",
        "salary": 38100.0,
        "addresses": [
            {
                "city": "Chennai",
                "country": "India",
                "zipCode": 111111
            }
        ]
    }
```

and it has been pushed.
### Retrieving an Object
Let's fetch one of the above customer and check, Our API end point will be `http://localhost:8080/rest/customer/find-by-id/1`, Here wwe are fetching by ID and the customer with id 1.  
RESPONSE:

```
{
    "id": 1,
    "name": "Jack",
    "email": "jack@yahoo.com",
    "salary": 18100.0,
    "addresses": [
        {
            "city": "Mumbai",
            "country": "India",
            "zipCode": 111111
        }
    ]
}
```

### Deleting an Object
Let's delete a customer with id `2`, Our end point will be `http://localhost:8080/rest/customer/delete/2`. It will be a DELETE call. 
RESPONSE: `200 [OK]`. If we try fetching it out through GET, we won't be gettting any data.
# Querying
Since Spring Data ElasticSearch repostory works on top of `PagingAndSortingRepository`. It allows both type of query, i.e., Standard query methods as well as custome query methods. Let's work with both one by one.
## Query Methods
Query methods are also called as method name based queries. It allows us to look for the data based on the fields methods name. 
Let's suppose we want to find the Customer from Elasticsearch based on his name. To do so we can add below Query method to our repository.

```
	Page<Customer> findByName(String name, Pageable pageable);
```

## Custom QueryMethods
There may be a situation where we couldn't fetch the data from Elasticsearch based on the Query methods, In that case we can use custom queries. 
Let's say we want to fetch the customer details based on his City name, where city is the part of Address and Address is nested in Customer class as list. No worry it too simple. In this case we will use `@Query` annotation from `org.springframework.data.elasticsearch.annotations` over our custom query. 

```
@Query("{\"bool\": {\"must\": [{\"match\": {\"addresses.city\": \"?0\"}}]}}")
Page<Customer> findByAddressCityUsingCustomQuery(String name, Pageable pageable);
```

Above query returns the customer having city equivalent to passed city in the parameter.
## Hands on Coding for Query and Custom Query Methods
Let's add above two methods in our repository. Now our repository will look like:

```
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.tutorialspoint.es.document.Customer;

@Repository
public interface CustomerRepository extends ElasticsearchRepository<Customer, Long> {

Page<Customer> findByName(String name, Pageable pageable);

@Query("{\"bool\": {\"must\": [{\"match\": {\"addresses.city\": \"?0\"}}]}}")
Page<Customer> findByAddressCityUsingCustomQuery(String name, Pageable pageable);
}
```

Let's write create some end point to fetch out these two operations. Add below two end points in the controller.

```
// Retrieving a customer from ElasticSearch by Name
	@GetMapping("/customer/find-by-name/{name}")
	public List<Customer> fetchCustomerByNAme(@PathVariable String name) {
		Page<Customer> customerOpt = customerService.findByName(name);
		return customerOpt.get().collect(Collectors.toList());

	}

	// Retrieving a customer from ElasticSearch by City name
	@GetMapping("/customer/find-by-address-city/{city}")
	public List<Customer> fetchCustomerByCity(@PathVariable String city) {
		Page<Customer> customerOpt = customerService.findByAddressCity(city);
		return customerOpt.get().collect(Collectors.toList());

	}
```


Let's add below two methods to our services, 

```
@Override
	public Page<Customer> findByName(String name) {
		return customerRepository.findByName(name, PageRequest.of(0, 2));

	}

	@Override
	public Page<Customer> findByAddressCity(String city) {
		return customerRepository.findByAddressCityUsingCustomQuery(city, PageRequest.of(0, 2));
	}
```
Let's perfom below two GET operation for fetching customer based on his name and based on city name.
GET API: http://localhost:8080/rest/customer/find-by-name/Jack
RESPONSE:

```
[
    {
        "id": 1,
        "name": "Jack",
        "email": "jack@yahoo.com",
        "salary": 18100.0,
        "addresses": [
            {
                "city": "Mumbai",
                "country": "India",
                "zipCode": 111111
            }
        ]
    }
]
```

GET API: http://localhost:8080/rest/customer/find-by-address-city/Mumbai
RESPONSE:

```
[
    {
        "id": 1,
        "name": "Jack",
        "email": "jack@yahoo.com",
        "salary": 18100.0,
        "addresses": [
            {
                "city": "Mumbai",
                "country": "India",
                "zipCode": 111111
            }
        ]
    }
]
```

We will learn more about fetching complex data from elastic search in next section
# Configuring ElasticsearchOperations bean
So far, we were performing operations on CustomerRepository to fetch, update or delete the data. Spring Data Elasticsearch provides an interface called `ElasticsearchOperations` through which we can perform some more complex operaion in a simple way. `ElasticsearchTemplate` is an implementation of ElasticsearchOperations, Let's configure this ElasticsearchTemplate bean, so that we can effectively use it.
Create a class with name ElasticsearchConfig(probably we can give any name) and annotate it with `@Configuartion`. Use below code sample:

```
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.tutorialspoint.es.repository")
@ComponentScan(basePackages = { "com.tutorialspoint.es.service.impl" })
public class ElasticsearchConfig {

	@Value("${elasticsearch.home:D:\\PersonalData\\elasticsearch-7.6.2-windows-x86_64\\elasticsearch-7.6.2}")
	private String elasticsearchHome;

	@Value("${elasticsearch.cluster.name:tutorials_Point}")
	private String clusterName;

	@Bean
	public Client client() throws UnknownHostException {
		TransportClient client = null;
		try {
			final Settings elasticsearchSettings = Settings.builder().put("client.transport.sniff", true)
					.put("path.home", elasticsearchHome).put("cluster.name", clusterName).build();
			client = new PreBuiltTransportClient(elasticsearchSettings);
			client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return client;
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException {
		return new ElasticsearchTemplate(client());
	}
}
```

All the annotations used above are Spring enable standard annotation. 
`@EnableElasticsearchRepositories`, is used to scan the repository package
In the above configuration we are using [Transport Client](https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/transport-client.html). This transport client requires below settings:
1. `client.transport.sniff`, to be marked as true, to enable sniffing feature.
2. `path.home` is a path to the installation directory of Elastic search.
3. `cluster.name` is used to provide cluster name, in case we are not using default cluster name i.e, `elasticsearch`.
Finally, To get TransportClient, we are passing the bind address and port.
In the second bean, we are passing this transport client and getting ElasticsearchTemplate of ElasticsearchOperations to work with Elastic search server. How this will help in interacting with the server, let's understand in the next section.

## Working with QueryDSL
A Query DSL could be considered as an AST(Abstract syntax Tree), Query DSL i sused to define a query. To do so we wil be using a Query Builder called `NativeSearchQueryBuilder`. Let's define a class called `QueryDSLBuilder`, and autowire the ElasticsearchTemplate.

```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueryDSLBuilder {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
}
```

### Multi-Match search
Let's say we want to search a keyword/text in name or email, if the given keyword matches in either name or email, it will return those customer list. Let's have a look on Code:

```
    //Find all customer whose name or email is as per given text
	public List<Customer> fetchCustomerWithMultiTextMatch(String text) {
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.multiMatchQuery(text, "name", "email"));
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
		return elasticsearchTemplate.queryForList(searchQuery, Customer.class);
	}
```

The above operation can be considered as `OR` operation between two or more fields.
### Multi-Field search
Let's say we want to serach two keyword in the field `name` and `email` . If the keyword is availeble in both of the field, then it wil return the result with Customer list. Let's have a look on code;

```
    // Find all the customer whose name and salary is matched
	public List<Customer> fetchCustomerByMultiFieldMatch(String text, Double salary) {
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", text))
				.must(QueryBuilders.matchQuery("salary", salary));
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
		return elasticsearchTemplate.queryForList(searchQuery, Customer.class);
	}
```

The above operation can be considered as `AND` operation between two or more fields.
### Searching a part of word/data(Wildcard/Partial)
Let's say we want to search a partial text and want to check any String matching with this text in the field name should return that customer details. Let's have a look on the code:

```
// Fetch all the customer whose name matches with fully or partially with given text
	public List<Customer> fetchCustomerByPartilaMatch(String text) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withFilter(QueryBuilders.regexpQuery("name", ".*" + text + ".*")).build();
		return elasticsearchTemplate.queryForList(searchQuery, Customer.class);
	}
```

We can consider above operation as `LIKE` in RDBMS or contains() method in Java.
### Updating an Object
Let's say we want to update a customer name from X to Y. This can be done by fethcing the customer details by name and then updating his name. Have a look on the code.

```
   // Update a customer whose name is ? to ?
	@Transactional
	public void updateHavingName(String from, String to) {
		// First find the customer whose name is ?
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", from));
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
		List<Customer> customers = elasticsearchTemplate.queryForList(searchQuery, Customer.class);
		// Iterate through each customer whose name is ?
		for (Customer customer : customers) {
			// Update Customer name
			customer.setName(to);
			customerRepository.save(customer);
		}
	}
```

In the above code first we are findng the customer and then updating the name.
### Deleting an Object
Let's say we want to delete a customer whose name is X, This operation also similar to above one, and requires finding the customer and then deleting it. Let's have a look on code.

```
    // Delete a customer whose name is ?
	public void deleteByName(String name) {
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", name));
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
		List<Customer> customers = elasticsearchTemplate.queryForList(searchQuery, Customer.class);
		for (Customer customer : customers) {
			customerRepository.delete(customer);
		}
	}
```

In the above code, first we are finding the customer with name X and then deleting it. Add above all methods to  the QueryDSLBuilder and then, we can call the above code from our controller class by adding respective end points. Here is the final controller code.

```
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorialspoint.es.document.Customer;
import com.tutorialspoint.es.service.CustomerService;
import com.tutorialspoint.es.service.impl.QueryDSLBuilder;

@RestController
@RequestMapping("/rest")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@Autowired
	private QueryDSLBuilder queryDSLBuilder;

	// Persisting a customer to ElasticSearch
	@PostMapping("/customer/save")
	public Customer persistCustomer(@RequestBody Customer customer) {
		return customerService.save(customer);
	}

	// Retrieving a customer from ElasticSearch
	@GetMapping("/customer/find-by-id/{id}")
	public Customer fetchCustomer(@PathVariable Long id) {
		Optional<Customer> customerOpt = customerService.findById(id);
		return customerOpt.isPresent() ? customerOpt.get() : null;

	}

	// Deleting a customer from elasticsearch
	@DeleteMapping("/customer/delete/{id}")
	public void deleteObject(@PathVariable Long id) {
		customerService.deleteById(id);
	}

	// Retrieving a customer from ElasticSearch by Name
	@GetMapping("/customer/find-by-name/{name}")
	public List<Customer> fetchCustomerByNAme(@PathVariable String name) {
		Page<Customer> customerOpt = customerService.findByName(name);
		return customerOpt.get().collect(Collectors.toList());

	}

	// Retrieving a customer from ElasticSearch by City name
	@GetMapping("/customer/find-by-address-city/{city}")
	public List<Customer> fetchCustomerByCity(@PathVariable String city) {
		Page<Customer> customerOpt = customerService.findByAddressCity(city);
		return customerOpt.get().collect(Collectors.toList());

	}

	// Retrieving a customer from ElasticSearch whose name or email matches with given keywords
	@GetMapping("/customer/multi-match/{text}")
	public List<Customer> fetchCustomerWithTextMatch(@PathVariable String text) {
		return queryDSLBuilder.fetchCustomerWithMultiTextMatch(text);

	}

	// Retrieving a customer from ElasticSearch whose name and salary are as per given search  keywords
	@GetMapping("/customer/multi-field/{name}/{salary}")
	public List<Customer> fetchCustomerByFieldMatch(@PathVariable String name, @PathVariable Double salary) {
		return queryDSLBuilder.fetchCustomerByMultiFieldMatch(name, salary);

	}

	/*
	 * Retrieving a customer from ElasticSearch whose name matches fully or
	 * partially with given text
	 */
	@GetMapping("/customer/partial-match/{text}")
	public List<Customer> fetchCustomerByPartilaMatch(@PathVariable String text) {
		return queryDSLBuilder.fetchCustomerByPartilaMatch(text);

	}

	// Updating a customer name from X to Y
	@PostMapping("/customer/update/{from}/{to}")
	public void updateCustomerHavingTextInName(@PathVariable String from, @PathVariable String to) {
		queryDSLBuilder.updateHavingName(from, to);
	}

	// Deleting a customer from elasticsearch by name
	@DeleteMapping("/customer/delete/by-name/{name}")
	public void deleteCustometHavingName(@PathVariable String name) {
		queryDSLBuilder.deleteByName(name);
	}
}
```

# Features
1. Java nd XML based configuration support. 
2. ElasticsearchTemplate an implementation of ElasticsearchOperations interface helps us to perform Elasticsearch related operations.
3. Annotaion based nested mapping between two classes.
4. Query and Custom Query method support.
5. Mapping supoort between documents and POJO.

# Conclusion
So far we learned, What is Spring Data Elasticsearch and how it works, We understood the concepts and performed hands-on coding as well.