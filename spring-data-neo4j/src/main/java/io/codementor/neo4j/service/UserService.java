package io.codementor.neo4j.service;

import io.codementor.neo4j.node.User;

public interface UserService {

	User save(User person);

	void delete(Long userId);

	User get(Long userId);

	Iterable<User> findAll();

}
