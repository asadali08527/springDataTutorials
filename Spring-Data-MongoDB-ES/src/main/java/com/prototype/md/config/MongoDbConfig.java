package com.prototype.md.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoDbConfig {

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create("mongodb://localhost:27017");
	}

	/*
	 * @Bean public MongoClientFactoryBean mongo() { MongoClientFactoryBean mongo =
	 * new MongoClientFactoryBean(); mongo.setHost("localhost"); return mongo; }
	 */
}
