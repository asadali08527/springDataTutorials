package com.tutorialspoint.repositories;


import org.springframework.data.repository.CrudRepository;

import com.tutorialspoint.entities.Cineplex;

public interface CineplexRepository extends CrudRepository<Cineplex, Long> {

}
