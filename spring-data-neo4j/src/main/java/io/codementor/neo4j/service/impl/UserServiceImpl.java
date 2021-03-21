package io.codementor.neo4j.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.codementor.neo4j.node.User;
import io.codementor.neo4j.repository.UserRepository;
import io.codementor.neo4j.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public User save(User person) {
		return userRepository.save(person);
	}

	@Transactional
	@Override
	public void delete(Long userId) {
		userRepository.deleteById(userId);
	}

	@Transactional
	@Override
	public User get(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		return userOptional.isPresent() ? userOptional.get() : null;
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

}
