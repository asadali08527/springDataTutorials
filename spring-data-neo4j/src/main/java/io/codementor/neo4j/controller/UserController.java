package io.codementor.neo4j.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.codementor.neo4j.node.User;
import io.codementor.neo4j.service.UserService;

@RestController
@RequestMapping("/rest/v1")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public Iterable<User> fetchUsers() {
		return userService.findAll();
	}

	@PostMapping("/user/add")
	public void persisUser(@RequestBody User user) {
		userService.save(user);
	}

}
