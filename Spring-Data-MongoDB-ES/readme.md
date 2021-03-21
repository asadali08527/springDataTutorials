
# Guidelines to follow

The project is build using Spring Boot, and all the dependencies has been taken from Spring Data family.

## Package Structure

The source code is a proto-type to fetch data from a SQL or NoSQL data base and push it to elastic search.
The project follow a certain package structure. The stuff(table, repository, etc) related to MongoDB has been injected inside package `com.prototype.md`, and the code related to SQL data base has been embedded inside package `com.prototype.sql`, Similarly the elastic search stuff has been kept inside package `com.prototype.es`.
Each database configuration has been kept in their respective config package.

### application.properties

The `application.properties` file consist of MongoDB credential and SQL data base credentials(MySQL). We can replace the credentials as per our host, user name and password. In case of replacing MySQL to Oracle, just change the database credentials and driver name of oracle.

### Maven Dependencies

All the required dependencies has been already added for Elastic search, MongoDb. In case of using Oracle, just add Oracle database dependencies.

### Source Code(Classes)

Since our requirement is to fetch the data from MongoDb, we need to create respective table structure(POJO) to store the values, this is called document of MongoDB(In this proto-type project `Customer` class inside `com.prototype.md.document`) is the POJO. Similarly to work with MongoDB document we need to create a repository of that document(In this proto-type project interface `CustomerRepository` inside package `com.prototype.md.repository` is the repository). Now we are ready to fetch the data from MongoDb.

Since the fetched data from MongoDb need to be pushed in elastic search then we need to perform same steps for this as well. Create a document and repository. Check the `Customer` class and `CustomerRepository` interface inside packages `com.prototype.es.document`, `com.prototype.es.repository` respectively. The Customer class defined in this package is somewhat similar to the Customer class defined in MongoDb. This is kept intentionally. We have added a column/field called `work` to add an extra data compared to the data fetched from MongoDb. We can keep both the classes of MongoDb and Elastic search either similar or variant, it is completely on us and depends on business requirements.

Now let's create a controller to fetch and push the data. Check `CustomerController` inside package `com.prototype.controller`. Various API calls have been defined to fetch, add , transfer, and delete the data.

### Things to DO

Import this project either as a Spring, or Spring Boot or as a Maven project. Since it is a prototype working on development system. You can replace the credentials in application.properties file and config files. In case using Oracle, replace the driver details and credentials in application.properties file. Uncomment the configuration annotation of class `SQLConfig` under `com.prototype.sql.config` in case using SQL database. 

Certificate related settings can be updated in ElasticsearchConfig. Just replace the class with blow code

```
package com.prototype.es.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.SecureSetting;
import org.elasticsearch.common.settings.SecureSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.prototype.repository")
@ComponentScan(basePackages = { "com.prototype.service.impl" })
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

	@Value("${elasticsearch.home:D:\\PersonalData\\elasticsearch-7.6.2-windows-x86_64\\elasticsearch-7.6.2}")
	private String elasticsearchHome;

	@Value("${elasticsearch.cluster.name:tutorials_Point}")
	private String clusterName;

	private static final String CERT_FILE = "client.p12";
	private static final String CERT_PASSWORD = "topsecret";
	private static final String USER_NAME = "user";
	private static final String USER_PASS = "password";

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

	@Override
	public RestHighLevelClient elasticsearchClient() {
		final ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:443")
				.usingSsl(createSSLContext()) // use the SSLContext with the client cert
				.withBasicAuth(USER_NAME, USER_PASS) // use the headers for authentication
				.build();

		return RestClients.create(clientConfiguration).rest();
	}

	private SSLContext createSSLContext() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");

			KeyManager[] keyManagers = getKeyManagers();

			sslContext.init(keyManagers, null, null);

			return sslContext;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private KeyManager[] getKeyManagers() throws KeyStoreException, NoSuchAlgorithmException, IOException,
			CertificateException, UnrecoverableKeyException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CERT_FILE)) {
			KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
			clientKeyStore.load(inputStream, CERT_PASSWORD.toCharArray());

			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(clientKeyStore, CERT_PASSWORD.toCharArray());
			return kmf.getKeyManagers();
		}
	}

}

```

Note- In above class, update the certificate path, user name and passwoed if any.