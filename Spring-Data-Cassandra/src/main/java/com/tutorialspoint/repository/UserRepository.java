package com.tutorialspoint.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.tutorialspoint.entity.User;
import com.tutorialspoint.entity.UserKey;

public interface UserRepository extends CassandraRepository<User, UserKey> {

	List<User> findByKeyLastName(final String lastName);

	List<User> findByKeyLastNameAndKeySalaryGreaterThan(final String firstName, final Double salary);

	@Query(allowFiltering = true)
	List<User> findByFirstName(final String firstName);

}
