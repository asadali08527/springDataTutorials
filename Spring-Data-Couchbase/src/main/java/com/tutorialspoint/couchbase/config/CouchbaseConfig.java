package com.tutorialspoint.couchbase.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

/*
@Configuration
@EnableCouchbaseRepositories(basePackages = { "com.tutorialspoint.couchbase.repository" })*/
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
