package io.codementor.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import io.codementor.neo4j.node.User;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

}
