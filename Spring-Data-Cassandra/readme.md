TABLE OF CONTENTS
- --
- Overview
- Prerequisites
- Introduction
- What is Cassandra?
    + Installation Guide
        + Steps Post Installation
            + Create KEYSPACE
            + Create table
- Getting Started
    + Adding Dependencies
    + Creating Table
    + Creating Repository
    + Configuring DataSource
        + XML Based Configuration
        + Java Based Configuration
        + Properties based Configuration
    + Performing CRUD Operation
- Annotation `@AllowFiltering` with Query Methods 
- Partition and Clustering
    + Partitioning Column
    + Clustering Column
- Coding hands-on on Partitioning and Clustering
    + Create Entity
        + Create `PrimaryKeyClass`
    + Create Repository
    + Perform CRUD Operations
- Features
- Conclusion

# Overview
In this tutorial, we will learn about Spring Data Cassandra. We will start with the basics and go through the configuration of Cassandra databases to work with Spring Data. We will also do some hands-on coding to perform CRUD operation using Spring Data Cassandra.

# Prerequisites
•	About 30 minutes
•	Basic Spring Data knowledge
•	A Basic understanding of the Cassandra Database.
•	A java based IDE (Eclipse, STS or IntelliJ IDEA)
•	JDK 1.8 or later
•	Gradle 4+ or Maven 3.2+
•   Cassandra installed
# Introduction
As we know that, the Spring Data framework is the umbrella project which contains many sub-frameworks. All these sub frameworks deal with data access which is specific to a database. Spring Data Cassandra is one of the sub-framework of the Spring Data project, that provides access to the Cassandra a Column based NoSql database. It offers a familiar interface for those who have worked with other modules of the Spring Data in the past. Before jumping the Spring Data Cassandra, let's have a basic understanding of Cassandra.
# What is Cassandra?
Cassandra is an open-source column-oriented NoSQL database. It is a distributed database management system that can manage large amounts of data across multiple servers. It provides high availability through data replication across multiple data-centers, which guarantees for no single point of failure. Cassandra Query Language(`CQL`) is its query language which is similar to `SQL`. To learn more about Cassandra, visit our tutorials by clicking [here](https://www.tutorialspoint.com/cassandra/index.htm). 
## Installation Guide
Since this tutorial will be working extensively with Cassandra. Make sure it is installed on your machine, If not already installed follow our installation guide by clicking [here](https://www.tutorialspoint.com/cassandra/cassandra_installation.htm). 
### Steps Post Installation
Once it is installed, We can create Keyspace. A keyspace in Cassandra is equivalent to a database in RDBMS. 
#### Create KEYSPACE
Let's create a KESPACE named `tutorials_point`, using below syntax.

```
CREATE KEYSPACE IF NOT EXISTS tutorials_point WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};
```

#### Create table
The syntax and process of creating a table in Cassandra are also similar to the RDBMS table. a table in Cassandra can be created in a KEYSAPCE. Let's move into KEYSPACE.

```
use tutorials_point ;
```

Now, create a table `customer` which has two fields 'id', and 'name'. Use below syntax.

```
CREATE TABLE customer(
   id INT PRIMARY KEY,
   name text
);
```

# Getting Started with Spring Data Cassandra
Like other Spring-based projects, you can start from scratch by creating a maven or Gradle based project from your favorite IDE. Follow below step by step process or you can bypass the basic setup steps that are already familiar with.
## Adding Dependencies.
If you have created normal Maven or Gradle projects then add below dependencies to your pom.
For Maven

```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-cassandra</artifactId>
</dependency>
```

Above one is the Spring Data `Cassandra` dependency. If you have created your project as Spring Boot or Starting with Spring Initializr then your final list of dependencies wil look like this:
![](D:\PersonalData\Blogs and Articles\TutorialsPoint\Spring Data Cassandra\Dependencies.png)
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
		<version>2.2.7.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.tutorialspoint</groupId>
	<artifactId>Spring-Data-Cassandra</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>Spring-Data-Cassandra</name>
	<description>Spring Data Cassandra project using Spring Boot</description>
	<properties>
		<java.version>1.8</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-cassandra</artifactId>
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

## Creating Table
A tabel for Cassandra data base is equivalent to a model/entity, it is an actual domain object and it will be created as a POJO. Its maps columns to be persisted into the database. It uses annotation `@Table` from `org.springframework.data.cassandra.core.mapping`. Let's define our first table:

```
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
@Table("customer")
public class Customer {

	@PrimaryKey
	private Long id;

	private String name;

	public Customer() {
	}

	public Customer(Long id, String name) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + "]";
	}
}
```

`@Table`: We used to mark our domain object with this annotation to map Cassandra table. 
`@PrimaryKey` here we mark it for idenitity purpose. This annotation is from `org.springframework.data.cassandra.core.mapping`. We van aslo use `@Id` for identity purposes, which is from  `import org.springframework.data.annotation` from Spring Data. We use `@PrimaryKey` annotation against a field if that field consist of partition and clustering columns. We will understand this concept in details in later section of this article
We can also use other anotations like `@Column` to name our column if it we don't want the name used in the class field and it is optional. 
## Creating a Repository
Let's define an interface which will be our repository:

```
import org.springframework.data.cassandra.repository.CassandraRepository;
import com.tutorialspoint.entity.Customer;

public interface CustomerRepository extends CassandraRepository<Customer, Long> {

}
```

The process of creating a repository is similar to the repository creation in any Spring data-based project, the only difference here is that it extends `CassandraRepository` from `org.springframework.data.cassandra.repository`, which works on top of `CrudRepository`. 
## Configuring DataSource
We can configure Cassandra using `application.properties` file, using XML and using Java based configuration. We can choose either one among them. Let's discuss one by one.
### XML Based Configurations
Below one is the equivalent XML configuration.

```
<beans:beans xmlns:beans="http://www.springframework.org/schema/beans"
    xmlns:cassandra="http://www.springframework.org/schema/data/cassandra"
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/cql http://www.springframework.org/schema/cql/spring-cql-1.0.xsd
    http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/data/cassandra http://www.springframework.org/schema/data/cassandra/spring-cassandra.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
 
    <cassandra:cluster id="cassandraCluster"
        contact-points="127.0.0.1" port="9042" />
         <cassandra:converter />
     <cassandra:session id="cassandraSession" cluster-ref="cassandraCluster"
        keyspace-name="tutorials_point" />
     <cassandra:template id="cqlTemplate" />

    <cassandra:repositories base-package="com.tutorialspoint.repository" />
    <cassandra:mapping entity-base-packages="com.tutorialspoint.entity" />
</beans:beans>
```

Replace the cluster info, bucket and repositories details.
### Java Based Configuration
Create a config file, say `CassandraConfig`, and extend `AbstractCassandraConfiguration`, this will ask to implement necessary methods which will be used for passing credentials as follows:

```
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.tutorialspoint.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Override
	protected String getKeyspaceName() {
		return "tutorials_point";
	}

	@Bean
	public CassandraClusterFactoryBean cluster() {
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints("127.0.0.1");
		cluster.setPort(9042);
		cluster.setJmxReportingEnabled(false);
		return cluster;
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}
}
```

### Using `application.properties` file
Below one is the majorly used configuration, just replace the credentials.

```
spring.data.cassandra.keyspace-name=tutorials_point
spring.data.cassandra.contact-points=127.0.0.1
spring.data.cassandra.port=9042
```

## Performing CRUD Operation
Now let's perform below some CRUD operation. We will try adding some customers to the above document, and retrieve some of them by their id or name. Following is the code for the same.

```
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tutorialspoint.entity.Customer;
import com.tutorialspoint.repository.CustomerRepository;

@SpringBootApplication
public class SpringDataCassandraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataCassandraApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {

				// Creating some entries
				Customer customer1 = customerRepository.save(new Customer(1l, "Asad"));
				System.out.println(customerRepository.save(customer1));
				Customer customer2 = customerRepository.save(new Customer(2l, "Ali"));
				System.out.println(customer2);
				Customer customer3 = customerRepository.save(new Customer(3l, "John"));
				System.out.println(customer3);
				// Fetching entry
				System.out.println(customerRepository.findById(1l));
				// Find all entry
				System.out.println(customerRepository.findAll());
				// Update entry
				Optional<Customer> customerOptional = customerRepository.findById(3l);
				if (customerOptional.isPresent()) {
					Customer customer = customerOptional.get();
					customer.setName("John Montek");
					System.out.println(customerRepository.save(customer));
				}
				// Deleting entry
				customerRepository.delete(customer2);
				// fetch all Entry
				System.out.println(customerRepository.findAll());
			}
		};
	}
}
```

The above code has used CommandLineRunner which will be executed on application startup. We have created four customers and saved them in the database and executed the method `findById()` to retrieve the customers. We also tried updating one of the records and re-retrieved to check if it has been updated. Finally we are deleting one of the records. Let's run the application as the Spring Boot App, below is the output.
Creating Entries
> Customer [id=1, name=Asad]
> Customer [id=2, name=Ali]
> Customer [id=3, name=John]

Finding one of the entry
> Optional[Customer [id=1, name=Asad]]

Finding all entry
> [Customer [id=1, name=Asad], Customer [id=2, name=Ali], Customer [id=3, name=John]]

Updating an entry
> Customer [id=3, name=John Montek]

Deleted one of the entry and re-retrived all entry
> [Customer [id=1, name=Asad], Customer [id=3, name=John Montek]]

# Annotation `@AllowFiltering` with Query Methods 
The concept Query methods and Custom Query methods are easily accessible here because the repository associated with `CassandraRepository` works on top of `CrudRepository`. Thus by default all the methods like `save()`, `findOne()`, `findById()`, `findAll()`, `count()`, `delete()`, `deleteById()` etc are accesible and can be used. 
The columns in Cassandra can't be used for query conditions unless Cassandra allows. For such query conditions and filtering related operations, we need to use an annotation on our queries called `@AllowFiltering` such as :

```
@AllowFiltering
Customer findByName(String string);
```

If we don't use this annotation we will get below error:

```
Cannot execute this query as it might involve data filtering and thus may have unpredictable performance. If you want to execute this query despite the performance unpredictability, use ALLOW FILTERING
```
# Partitioning and Clustering
If you are well versed with Cassandra, then you might know how partitioning and clustering takes place. Here we will focus on a basic understanding of these concepts so that it is easy for us to perform some hands-on on this concept.
Partitioning and Clustering in Cassandra can be used with the help of primary keys. Below are the two aspects of primary keys in the context of Cassandra.
## Partitioning Column
In Cassandra, a partition is a section or segment where the data is persisted. These partitions are useful in distinguishing the location where data is stored. Due to this it enables Cassandra to read the data faster because all the similar data is packed and stored together in these partitions. When queries these data retrieved together. The query containing equality conditions (`= or !=`) are queried over these partitioned columns.
## Clustering Column
To achieve the uniqueness and ordering of records, Cassandra cluster the columns. The clustered columns help us to query the record using equality and conditional operators such as `>=`, `<=` etc.
# Hands-on using Partition and Clustering
To illustrate the above Partitioning and Clustering concept, we will create an entity/table as a POJO, which will consist of a primary key, which will again be a reference to another POJO. Thus here one table will be created using two POJO where one will act as a primary key of another one.
## Create Entity/Table
Let's create both the classes which will be our table.

```
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("user")
public class User {
	@PrimaryKey
	private UserKey key;
	@Column("first_name")
	private String firstName;
	private String email;

	public User() {

	}

	public User(UserKey key, String firstName, String email) {
		this.key = key;
		this.firstName = firstName;
		this.email = email;
	}

	public UserKey getKey() {
		return key;
	}

	public void setKey(UserKey key) {
		this.key = key;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [key=" + key + ", firstName=" + firstName + ", email=" + email + "]";
	}
}
```

Above one is the `User` table which consist of a primary key called `UserKey` which is again an another Java class and it is called as `PrimaryKeyClass`. let's create this as well.
### Create PrimaryKeyClass
```
import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class UserKey implements Serializable {

	@PrimaryKeyColumn(name = "last_name", type = PrimaryKeyType.PARTITIONED)
	private String lastName;
	@PrimaryKeyColumn(name = "salary", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
	private Double salary;
	@PrimaryKeyColumn(name = "user_id", ordinal = 1, ordering = Ordering.DESCENDING)
	private UUID id;

	public UserKey(String lastName, Double salary, UUID id) {
		this.lastName = lastName;
		this.salary = salary;
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserKey other = (UserKey) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserKey [lastName=" + lastName + ", salary=" + salary + ", id=" + id + "]";
	}
}
```

In the above class we can see the annotation called `@PrimaryKeyClass` which states that this class will act as a Primary key. The fields of this class is annotated with annotation `@PrimaryKeyColumn` which simply means the column are the part of Primary Key. The attribute used with these annotations defines the partitioning, clustering and ordering of the records.
For above POJO's we need to write equivalent CQL to create table in Cassandra.

```
CREATE TABLE user(
  last_name TEXT,
  salary DOUBLE,
  user_id UUID,
  first_name TEXT,
  email TEXT,
  PRIMARY KEY ((last_name), salary, user_id)
) WITH CLUSTERING ORDER BY (salary ASC, user_id DESC);
```

## Create Repository
Now, Let's create repository for above table.

```
import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.tutorialspoint.entity.User;
import com.tutorialspoint.entity.UserKey;

public interface UserRepository extends CassandraRepository<User, UserKey> {

	List<User> findByKeyLastName(final String lastName);

	List<User> findByKeyLastNameAndKeySalaryGreaterThan(final String firstName, final Double salary);

	@Query(allowFiltering = true)
	List<User> findByFirstName(final String firstName);
}
```

If we observe the above interface, the generic used with `CassandraRepository<User, UserKey>` is the table name `User` and the primary key `UserKey`.  Other than this, the repository consists of some of the derived query methods.

Let's have a look on some CRUD operations using `CommandLineRunner`.

```
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
	
```

> User [key=UserKey [lastName=Miller, salary=81000.0, id=c6344f55-06f2-4edf-8bdb-e55269a0c73a], firstName=John, email=john@tutorialspoint.com]

> User [key=UserKey [lastName=Monty, salary=85000.0, id=e8d9100f-1fad-4c93-b735-93fe27d067a7], firstName=Carlos, email=carlos@tutorialspoint.com]

> User [key=UserKey [lastName=Benjamin, salary=95000.0, id=fef72f65-ce91-482f-8ed8-e0e39330241f], firstName=Franklin, email=franklin@tutorialspoint.com]

# Features
1. Familiar and common interface to build repositories. 
2. JavaConfig and XML based support for Keyspace and table creation.
3. Object-based and annotation-based mapping.
4. Support for Query, custom methods, and derived query methods.
5. Partitioning and clustering support using PrimaryKey.

# Conclusion
So far we learned, how Spring Data Cassandra is useful in working with Cassandra. We have created a project which connected with the Cassandra database, and performed some CRUD operation. We also learned about the PrimaryKey concept used in Cassandra and did some hands-on coding using this concept.