TABLE OF CONTENTS
- --
- Overview
- Prerequisites
- Introduction
- What is Apache Solr?
    + Installation Guide
        + Steps Post Installation
            + Create Core/collection
- Getting Started
    + Adding Dependencies
    + Creating a Solr Document
    + Creating Solr Repository
    + Configuring DataSource
        + Java Based Configuration
        + Properties based Configuration
    + Performing CRUD Operation
- Querying  
    + Query Methods (Method Name Query Generation)
    + @Query Annotation
    + Named Query
- Features
- Conclusion

# Overview
In this tutorial, we will learn about Spring Data Apache Solr. We will start with the basics and go through the configuration of Apache Solr to work with Spring Data. We will also do some hands-on coding to perform CRUD operation using Spring Data Apache Solr.

# Prerequisites
•	About 30 minutes
•	Basic Spring Data knowledge
•	A Basic understanding of the Apache Solr Database.
•	A java based IDE (Eclipse, STS or IntelliJ IDEA)
•	JDK 1.8 or later
•	Gradle 4+ or Maven 3.2+
•   Apache Solr installed
# Introduction
As we know that, the Spring Data framework is the umbrella project which contains many sub-frameworks. All these sub frameworks deal with data access which is specific to a database. Spring Data Apache Solr is one of the sub-framework of the Spring Data project, that provides easy configuration and access to the Apache Solr a full-text search engine. It offers a familiar interface for those who have worked with other modules of the Spring Data in the past. Before jumping the Spring Data Apache Solr, let's have a basic understanding of Apache Solr.
# What is Apache Solr?
Apache Solr is an open-source enterprise search platform built on Apache Lucene. It is a highly scalable, reliable, and fault-tolerant, that provides distributed indexing, load-balanced querying, replication, centralized configuration, recovery, and automated failover. To achieve the search and navigation features of Apache Solr, most of the world's popular sites use it. To learn more about Apache Solr, visit our tutorials by clicking [here](https://www.tutorialspoint.com/apache_solr/index.htm). 
## Installation Guide
Since this tutorial will be working extensively with Apache Solr. Make sure it is installed on your machine, If not already installed you can download it from official [website](https://lucene.apache.org/solr/downloads.html).
### Steps Post Installation
Once it is installed, We can create a Core. A Core in Apache Solr is equivalent to a database in RDBMS. 
#### Create Core
Let's create a Core named `tutorials_point`, To do so navigate to bin directory and execute below command.

```
solr create -c users
```

# Getting Started with Spring Data Cassandra
Like other Spring-based projects, you can start from scratch by creating a maven or Gradle based project from your favorite IDE. Follow below step by step process or you can bypass the basic setup steps that are already familiar with.
## Adding Dependencies.
If you have created normal Maven or Gradle projects then add below dependencies to your pom.
For Maven

```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-solr</artifactId>
</dependency>
```

For Gradle

```
implementation('org.springframework.boot:spring-boot-starter-data-solr')
```

Above one is the Spring Data `Apache Solr` dependency. If you have created your project as Spring Boot or Starting with Spring Initializr then your final list of dependencies will look like this:
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Apache Solr\Dependencies.png)
Note- The code sample and exapmles used in this tutorial has been created through Spring Initializr. The following is your final pom.xml file that is created when you choose Maven:

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.0.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.tutorialspoint</groupId>
	<artifactId>Spring-Data-Solr</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>Spring-Data-Solr</name>
	<description>Spring Data Apache Solr project using Spring Boot</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-solr</artifactId>
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

## Creating Solr Document
A Solr document in Apache Solr is an actual domain object and it will be created as a POJO. Its maps columns to be persisted into the database. It uses annotation `@SolrDocument` from `org.springframework.data.solr.core.mapping`. Let's define our first table:

```
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "users")
public class Users {

	@Id
	@Indexed
	private Long id;

	@Indexed(name = "name", type = "string")
	private String name;

	public Users() {
	}

	public Users(Long id, String name) {
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + "]";
	}

}

```

`@SolrDocument`: We used to mark our domain object with this annotation to map class Users as Solr document which is indexed to collection name 'users'. 
`@Id` here we mark it for identity purpose which will act as a primary key. This annotation is from `org.springframework.data.annotation.Id`.
`@Indexed`: It is used to indexed the field to users collection, so that it is searchable

## Creating a Repository
Let's define an interface which will be our repository:

```
import java.util.List;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.tutorialspoint.entity.Users;

public interface UsersRepository extends SolrCrudRepository<Users, Long> {
	public List<Users> findByName(String name);
}
```

The process of creating a repository is similar to the repository creation in any Spring data-based project, the only difference here is that it extends `SolrCrudRepository` from `org.springframework.data.solr.repository`, which works on top of `SolrRepository` which again extends `PagingAndSortingRepository`. 
## Configuring DataSource
We can configure Cassandra using `application.properties` file, and Java-based configuration. We can choose either one among them. Let's discuss one by one.

### Java Based Configuration
Create a config file, say `SolrConfig`, 
```
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages = "com.tutorialspoint.repository")
@ComponentScan
public class SolrConfig {
	@Bean
	public SolrClient solrClient() {
		return new HttpSolrClient.Builder("http://localhost:8983/solr").build();
	}

	@Bean
	public SolrTemplate solrTemplate(SolrClient client) throws Exception {
		return new SolrTemplate(client);
	}
}
```

### Using `application.properties` file
Below one is the majorly used configuration.

```
spring.data.solr.host=http://localhost:8983/solr/
```
We can also enable/disable the Solr repository based on the requirement by adding the following property.
`spring.data.solr.repositories.enabled=false`
## Performing CRUD Operation
Now let's perform below some CRUD operation. We will try adding some users to the above document, and retrieve some of them by their id or name. Following is the code for the same.

```
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

				// Creating some entries
				Users user1 = usersRepository.save(new Users(1l, "Kallis"));
				System.out.println(usersRepository.save(user1));
				Users user2 = usersRepository.save(new Users(2l, "Mills"));
				System.out.println(user2);
				Users user3 = usersRepository.save(new Users(3l, "Wilson"));
				System.out.println(user3);

				// Fetching entry
				System.out.println(usersRepository.findById(2l));
				// Find all entry
				usersRepository.findAll().forEach(System.out::println);
				// Update entry
				Optional<Users> usersrOptional = usersRepository.findById(3l);
				if (usersrOptional.isPresent()) {
					Users user = usersrOptional.get();
					user.setName("Wilson Monk");
					usersRepository.save(user);
				}
				System.out.println(usersRepository.findByName("Wilson Monk"));
				// Deleting entry

				usersRepository.delete(user2);

				// fetch all Entry
				usersRepository.findAll().forEach(System.out::println);
			}
		};
	}
}
```

The above code has used CommandLineRunner which will be executed on application startup. We have created three users and saved them in the database and executed the method `findById()` to retrieve the user by id. We also tried updating one of the records and re-retrieved byName() query method to check if it has been updated. Finally we are deleting one of the records. Let's run the application as the Spring Boot App, below is the output.
Creating Entries
> Customer [id=1, name=Kallis]
> Customer [id=2, name=Mills]
> Customer [id=3, name=Wilson]

// Fetching entry by Id
> Optional[Customer [id=2, name=Mills]]

Finding all entry
> Customer [id=1, name=Kallis]
> Customer [id=2, name=Mills]
> Customer [id=3, name=Wilson]

Updating an entry name from Wilson to Wilson Monk and retrieving it byName
> [Customer [id=3, name=Wilson Monk]]

Deleted the entry with id 2, and re-retrived all entry
> Customer [id=1, name=Kallis]
> Customer [id=3, name=Wilson Monk]

# Querying 
The concept Query methods and Custom Query methods are easily accessible here because the repository associated with `SolrCrudRepository` works on top of `PagingAndSortingRepository` which in turns extends `CrudRepository`. Thus by default all the methods like `save()`, `findOne()`, `findById()`, `findAll()`, `count()`, `delete()`, `deleteById()` etc are accesible and can be used. 
Other than this it also has access to all the methods associated with Paging and sorting. Spring Data Apache Solr comes with a rich set of query approaches such as:
1. Method Name Query Generation
2. Custom Query With @Query Annotation
3. Named Query
Let's explore the above query technique defined by the Spring Data Solr API.

## Method Name Query Generation
These are the usual query methods which get generated based on the methods name of the attribute of our domain object, such as:
```
List<Users> findByName(String name);
```

## Custom Query with @Query Annotation
We can also create our search query using `@Query` annotation. Let's define a custom query and use @Query annotation.
```
@Query("id:*?0* OR name:*?0*")
public Page<Users> findByCustomQuery(String searchTerm, Pageable pageable);
```
The above custom query will fetch a record from the Solr database by performing a lookup on the id and name of a user. and it will return the results. Let's invoke this method and try fetching out the result:
```
usersRepository.findByCustomQuery("Kallis", PageRequest.of(0, 5)).forEach(System.out::println);
```
The above statement is saying to fetch a user based on the name 'Kallis', and obtain the first-page result with the size of max 5 records. The output of the above statement will be :

> Customer [id=1, name=Kallis]

## Named Query
This type of query is similar to Custom Query with @Query Annotation, except these queries are declared in a separate properties file. Let's create a properties file named `namedQueries.properties`(We can give any name) in parallel to application.properties file. Now let's add our first named query in that file.
```
Users.findByNamedQuery=id:*?0* OR name:*?0*
```
After adding the above file and query to that file our project structure will look like this.

![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Apache Solr\ProjectStructure.png)
Let's add this file information as class path in SolrConfig file. Add `namedQueriesLocation = "classpath:namedQueries.properties"` as attribute under `@EnableSolrRepositories`. Our updated SolrConfig file will be 
```
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages = "com.tutorialspoint.repository", namedQueriesLocation = "classpath:namedQueries.properties")
@ComponentScan
public class SolrConfig {
	@Bean
	public SolrClient solrClient() {
		return new HttpSolrClient.Builder("http://localhost:8983/solr").build();
	}

	@Bean
	public SolrTemplate solrTemplate(SolrClient client) throws Exception {
		return new SolrTemplate(client);
	}
}
```

Now, let's move to our repository and add a custom query to invoke this named query.
```
@Query(name = "Users.findByNamedQuery")
public Page<Users> findByNamedQuery(String searchTerm, Pageable pageable);
```
Note- @Query annotation is optional here and not required in case the qury method name `findByNamedQuery` matches with the query name used in properties file.
Let's invoke above method and check the result.
```
usersRepository.findByNamedQuery("Wilson", PageRequest.of(0, 5)).forEach(System.out::println);
```
The above statement will return result of first page with at most 5 records, as follows:
> Customer [id=3, name=Wilson Monk]

# Features
1. Familiar and common interface to build repositories. 
2. Object-based and annotation-based mapping.
3. Fluent Query API for Query, custom query, and named query methods.
4. Multi-core support.

# Conclusion
So far we learned, how Spring Data Apache Solr is useful in working with the Solr search engine. We created a project and integrated with the Apache Solr database. We performed some CRUD operations. We also learned about the various query technique provided by Spring Data Apache Solr API.