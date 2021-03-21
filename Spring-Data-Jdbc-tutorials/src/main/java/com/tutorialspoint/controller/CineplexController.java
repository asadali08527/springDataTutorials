package com.tutorialspoint.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorialspoint.entities.Cineplex;
import com.tutorialspoint.repositories.CineplexRepository;

@RestController
@RequestMapping("/cineplex/v1")
public class CineplexController {

	@Autowired
	private CineplexRepository cineplexRepository;

	@PostMapping("/movies")
	public long saveCustomer(@RequestBody List<Cineplex> cineplexes) {
		for (Cineplex cineplex : cineplexes)
			cineplexRepository.save(cineplex);
		return cineplexRepository.count();
	}

	@GetMapping("/movies")
	public Iterable<Cineplex> findAllCustomers() {
		return cineplexRepository.findAll();
	}

}
