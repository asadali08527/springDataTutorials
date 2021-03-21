TABLE OF CONTENTS
- --
- Overview
- Prerequisites
- Introduction
- What is Couchbase?
    + Installation Guide
        + Steps Post Installation
            + Create Cluster
            + Create Bucket
    + What is Bucket in the Couchbase?
        + Create Primary Index
- Getting Started
    + Adding Dependencies
    + Creating Document/Entity
    + Creating Repository
    + Configuring DataSource
        + XML Based Configuration
        + Java Based Configuration
        + Properties based Configuration
    + Performing CRUD Operation
- Views and CouchbaseTemplate 
- Hands-on using CouchbaseTemplate
- Features
- Conclusion

# Overview
In this tutorial, we will learn about Spring Data Couchbase. We will code and integrate the Spring Data with Couchbase. This tutorial will also focus some light on creating views and working with CouchbaseTemplate.

# Prerequisites
•	About 30 minutes
•	Basic Spring Data knowledge
•	A Basic understanding of the Couchbase Database.
•	A java based IDE (Eclipse, STS or IntelliJ IDEA)
•	JDK 1.8 or later
•	Gradle 4+ or Maven 3.2+
•   Couchbase installed
# Introduction
As we know that, the Spring Data framework is the umbrella project which contains many sub-frameworks. All these sub frameworks deal with data access which is specific to a database. Spring Data Couchbase is one of the sub-framework of the Spring Data project, that provides access to the Couchbase document database. Before jumping the Spring Data Couchbase, let's have a basic understanding of Couchbase.
# What is Couchbase?
Couchbase is an open-source document-oriented NoSQL database. It stores the information in key-value format, more specifically like a JSON document. We use Couchbase for horizontal scaling. To learn more about Couchbase, visit the official site by clicking [here](https://www.couchbase.com/). 
## Installation Guide
Since this tutorial will be working extensively with Couchbase. Make sure it is installed on your machine, If not you can install it from [here](https://www.couchbase.com/downloads) based on your system configuration. 
### Steps Post Installation
Once it is installed, it will navigate us to the URL- http://localhost:8091/index.html
#### Create Cluster
It will ask to set up the cluster, Click to create a new one, It will look like:
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Couchbase\ClusterSetup.png)
Now, It will ask to accept Terms and condition:
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Couchbase\ClusterSetupAcceptTAndC.png)
Accept it and click on `Configure Disk, Memory, Services` (You can also click on `Finish with defaults, if don't want to configure`).
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Couchbase\ClusterSetupConfigure3.png)
See if configurations like disk and memory need to be changed, Once done click on `Save and Finish`. The final screen will be :
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Couchbase\ClusterSetup4.png)
#### Create Bucket
Once the cluster setup is done we need to create a bucket. Click on `Buckets` and then click on `ADD BUCKET`. Enter a name for Bucket, Here we have given `Tutorials_Point`
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Couchbase\CreateBucket5.png)
## What is Bucket in the Couchbase?
A Bucket in the Couchbase is similar to the `Table` in RDBMS, or equivalent to a `Collection` in the MongoDB database. The bucket keeps holds of documents.
### Create Primary Index
By default custom queries will be processed using the N1QL engine, If we are using Couchbase 4.0 or later. We must need to create a primary index on the bucket, to add support for N1QL. To create primary index Click on `Query` and then type below command in `Query Editor`
> `CREATE PRIMARY INDEX ON Tutorials_Point USING GSI;`

In the above command, `Tutorials_Point` is our Bucket name, `GSI` is a global secondary index, GSI is majorly used for optimizing Adhoc N1QL queries. 
# Getting Started
Like other Spring-based projects, you can start from scratch by creating a maven or Gradle based project from your favorite IDE. Follow below step by step process or you can bypass the basic setup steps that are already familiar with.
## Adding Dependencies.
If you have created normal Maven or Gradle projects then add below dependencies to your pom.
For Maven
```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-couchbase</artifactId>
</dependency>
```
Above one is the Spring Data `Couchbase` dependency. If you have created your project as Spring Boot or Starting with Spring Initializr then your final list of dependencies wil look like this:
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Couchbase\Dependencies.png)
Note- The code sample and exapmles used in this tutorial has been created through Spring Initializr. The following is your final pom.xml file that is created when you choose Maven:
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
	<artifactId>Spring-Data-Couchbase</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>Spring-Data-Couchbase</name>
	<description>Spring Data Couchbase project using Spring Boot</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-couchbase</artifactId>
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
## Creating Document
A Document is equivalent to a model/entity, it is an actual domain object and it will be created as a POJO. Let's define our first document:
```
import javax.validation.constraints.NotNull;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document(expiry = 0)
public class Customer {
	@Id
	private Long id;
	@NotNull
	private String name;
	private String email;
	@Field("income")
	private Double salary;

	public Customer() {
	}

	public Customer(Long id, String name, String email, Double salary) {
		this.id=id;
		this.name = name;
		this.email = email;
		this.salary = salary;
	}

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
`@Document`: Unlike JPA where we used to mark our domain object with annotation `@Entity`, here we mark it with `@Document` from package `org.springframework.data.couchbase.core.mapping`, which represents it is a Couchbase document. It has an attribute called `expiry`, whcih is TTL of the document.
`@Id`: This is for identity purposes and it is from the native Couchbase SDK packege ` com.couchbase.client.java.repository.annotation`. We can also use @Id from Spring Data.
> If we use both @Id annotation (from Spring Data and native Couchbase SDK) on two different fields of the same class, the the annotation from Spring Data will take precendence and that field will be used as the document key.

`@Field("income")`: @Field annotation is from native Couchbase SDK, it is used to mark the field. WE can consider this as a `@Column` annotation from JPA. If we want to give some other name to our column then we can pass it to this annotation. 

## Creating a Repository
Let's define an interface which will be our repository:
import java.util.List;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.tutorialspoint.couchbase.document.Customer;

@Repository
public interface CustomerRepository extends CouchbaseRepository<Customer, Long> {
	List<Customer> findByEmail(String string);

}

The process of creating a repository is similar to the repository creation in any Spring data-based project, the only difference here is that it extends `CouchbaseRepository` from `org.springframework.data.couchbase.repository`, which works on top of `CrudRepository`. 
## Configuring DataSource
We can configure Couchbase using `application.properties` file, using XML and using Java based configuration. We can choose either one among them. Let's discuss one by one.
### XML Based Configurations
Below one is the equivalent XML configuration.
```
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns:beans="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns="http://www.springframework.org/schema/data/couchbase
  xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/data/couchbase
    http://www.springframework.org/schema/data/couchbase/spring-couchbase.xsd">
 
    <couchbase:cluster>
        <couchbase:node>localhost</couchbase:node>
    </couchbase:cluster>
 
    <couchbase:clusterInfo login="tutorials_point" password="qwerty" />
 
    <couchbase:bucket bucketName="Tutorials_Point" bucketPassword="123456"/>
 
    <couchbase:repositories base-package="com.tutorialspoint.couchbase.repository"/>
</beans:beans>
```
Replace the cluster info, bucket and repositories details.
### Java Based Configuration
Create a config file, say `CouchbaseConfig`, and extend `AbstractCouchbaseConfiguration`, this will ask to implement necessary methods which will be used for passing credentials as follows:
```
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Configuration
@EnableCouchbaseRepositories(basePackages = { "com.tutorialspoint.couchbase.repository" })
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

	@Override
	protected List<String> getBootstrapHosts() {
		return Arrays.asList("127.0.0.1");
	}

	@Override
	protected String getBucketName() {
		return "Tutorials_Point";
	}

	@Override
	protected String getBucketPassword() {
		return "";
	}
}
```
### Using `application.properties` file
Below one is the majorly used configuration, just replace the credentials.
```
spring.couchbase.bootstrap-hosts=127.0.0.1
spring.couchbase.bucket.name=Tutorials_Point
spring.couchbase.bucket.password=123456
spring.data.couchbase.auto-index=true
```
## Performing CRUD Operation
Now let's perform below some CRUD operation. We will try adding some customers to the above document, and retrieve some of them by their id or name. Following is the code for the same.
```
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tutorialspoint.couchbase.document.Customer;
import com.tutorialspoint.couchbase.repository.CustomerRepository;

@SpringBootApplication
public class SpringDataCouchbaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataCouchbaseApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
			
				// Persisting some documents
				customerRepository.save(new Customer(101l, "Duke", "duke@yahoo.com", 11000d));
				customerRepository.save(new Customer(102l, "Monty", "monty@yahoo.com", 22000d));
				customerRepository.save(new Customer(103l, "Carlos", "carlos@yahoo.com", 33000d));
				customerRepository.save(new Customer(104l, "Benjamin", "benjamin@yahoo.com", 44000d));

				// Fetching documents
				System.out.println(customerRepository.findById(101l));
				System.out.println(customerRepository.findByEmail("carlos@yahoo.com"));

				// Update record
				Customer customer = customerRepository.findById(101l).get();
				customer.setSalary(55000d);
				customer.setName("Duke Daniel");
				customerRepository.save(customer);
				System.out.println(customerRepository.findById(101l));

				// Delete Record
				customerRepository.deleteById(103l);
				System.out.println(customerRepository.findById(103l));
			}
		};
	}
}
```
The above code has used CommandLineRunner which will be executed on application startup. We have created four customers and saved them in the database and executed the method `findById()` and `findByEmail` to retrieve the customers. We also tried updating one of the record and re- retrived to check if it has been updated. Finally we are deleting one of the record. Let's run the application as the Spring Boot App, below is the output.

> Optional[Customer [id=101, name=Duke, email=duke@yahoo.com, salary=11000.0]]

> [Customer [id=103, name=Carlos, email=carlos@yahoo.com, salary=33000.0]]

> Optional[Customer [id=101, name=Duke Daniel, email=duke@yahoo.com, salary=55000.0]]

> Optional.empty

If we navigate to the Couchbase server dashboard, we can see that customer with id `103` is missing and rest three customer are available. Have a look at the image below.

![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Couchbase\ProgramOutput.png)

# Views 
We need to create a Couchbase design document and Views in our bucket. Our document class name will be a design document name but in lowerCamelCase format( Here its customer). To support the `findAll` repository method we need to create a view named all. To create design documents and views, Go to the Couchbase server dashboard, and click on `Views`, followed by `ADD VIEW`. 
1. Enter the details as mentioned in the below image.
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Couchbase\View_All.png)
2. If we want to create a view for any custom method such as `findBySalary` or `findByEmail` then it needs to be created in the same way, as follows.
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Couchbase\View_ByEmail.png)
3. Similarly, we can create views for all other custom methods of our repository. Finally, it will look like this
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Couchbase\Views.png)

If we want, we can create or modify the views usng Map functions, To do this click on edit, and enter map function. Let's say we want to create views for `findByName`, then our equivalent map function will be :
```sh
function (doc, meta) {
    if(doc._class == "com.tutorialspoint.couchbase.document.Customer"
      && doc.name) {
        emit(doc.name, null);
    }
}
```
for field salary and method `findBySalary` it will be:
```
function (doc, meta) {
    if(doc._class == "com.tutorialspoint.couchbase.document.Customer"
      && doc.salary) {
        emit(doc.salary, null);
    }
}
```
Views based custom methods inside repository must be annotated with the annotation `@View` as given below:
```
@View
List<Customer> findByName(String name);
```
> Creation of view are optional if we are using Couchbase server 4.0 or later, otherwise it is mandatory.

# CouchbaseTemplate
To work with CouchbaseTemplate, we must create Views as mentioned in the previous section.The `CouchbaseTemplate` is from native Couchbase SDK from package `org.springframework.data.couchbase.core`. It also provides us a way to perform CRUD operation what we did earlier with the help of a repository. 
# Hands-on using CouchbaseTemplate
Let's have a look on some CRUD operations using `CommandLineRunner`.
```
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
```
> [Customer [id=103, name=Simon Ford, email=simon@yahoo.com, salary=55000.0], Customer [id=101, name=Duke Daniel, email=duke@yahoo.com, salary=55000.0], Customer [id=102, name=Monty, email=monty@yahoo.com, salary=22000.0], Customer [id=104, name=Benjamin, email=benjamin@yahoo.com, salary=44000.0], Customer [id=105, name=John, email=john@yahoo.com, salary=12000.0], Customer [id=106, name=Franklin, email=franklin@yahoo.com, salary=32000.0]]

> [Customer [id=104, name=Benjamin, email=benjamin@yahoo.com, salary=44000.0], Customer [id=101, name=Duke Daniel, email=duke@yahoo.com, salary=55000.0], Customer [id=106, name=Franklin, email=franklin@yahoo.com, salary=32000.0], Customer [id=105, name=John, email=john@yahoo.com, salary=12000.0], Customer [id=102, name=Monty, email=monty@yahoo.com, salary=22000.0], Customer [id=103, name=Simon Ford, email=simon@yahoo.com, salary=55000.0]]

# Features
1. Support for configuring DataSource using XML, Java, and properties file. 
2. CouchbaseTemplate helper to perform Couchbase operations.
3. Object-based and annotation-based mapping.
4. Support for Query methods and Derived methods.

# Conclusion
So far we learned, how Spring Data Couchbase is useful in working with Couchbase. We have created a project which connected with the Couchbase server, and performed some CRUD operation. We also learned about CouchbaseTemplate and did some hands-on coding using this concept