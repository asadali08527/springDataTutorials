package com.prototype.es.config;

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
@EnableElasticsearchRepositories(basePackages = "com.prototype.es.repository")
@ComponentScan(basePackages = { "com.prototype.service.impl" })
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
