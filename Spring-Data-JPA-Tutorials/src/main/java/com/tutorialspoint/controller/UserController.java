package com.tutorialspoint.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tutorialspoint.entities.User;
import com.tutorialspoint.repositories.UserRepositories;

@RestController
public class UserController {
	@Autowired
	private UserRepositories userRepository;

	// Persisting an object
	@PostMapping("/user/add")
	public void persistObject(@RequestBody User user) {
		userRepository.save(user);
	}

	// Retrieving an object
	@GetMapping("/user/find/{id}")
	public Optional<User> fetchObject(@PathVariable Long id) {
		return userRepository.findById(id);
	}

	// Deleting an object
	@DeleteMapping("/user/delete/{id}")
	public void deleteObject(@PathVariable Long id) {
		userRepository.deleteById(id);
	}

	// Retrieving all object from the list

	@GetMapping("/user/find/all")
	public Iterable<User> fetchObjectList() {
		return userRepository.findAll();
	}

}
