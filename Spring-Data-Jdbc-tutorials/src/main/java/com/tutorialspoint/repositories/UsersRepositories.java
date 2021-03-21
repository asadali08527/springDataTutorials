package com.tutorialspoint.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tutorialspoint.entities.Users;

public interface UsersRepositories extends CrudRepository<Users, Long> {

	@Query("select * from Users where full_name=:fullName")
	List<Users> findByName(@Param("fullName") String name);

}
