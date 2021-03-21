package io.codementor.neo4j.config;

import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.config.Configuration.Builder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/*@Configuration
@EnableNeo4jRepositories(basePackages = "io.codementor.neo4j.repository")
@ComponentScan(basePackages = { "io.codementor.neo4j.service.impl" })*/
public class Neo4jConfig {

	public static final String URL = System.getenv("NEO4J_URL") != null ? System.getenv("NEO4J_URL")
			: "http://neo4j:codementor@localhost:7474";

	@Bean
	public org.neo4j.ogm.config.Configuration getConfiguration() {
		return new Builder().uri(URL).credentials("codementor", "codementor").build();
	}

	@Bean
	public SessionFactory getSessionFactory() {
		return new SessionFactory(getConfiguration(), "io.codementor.neo4j.node");
	}

}
