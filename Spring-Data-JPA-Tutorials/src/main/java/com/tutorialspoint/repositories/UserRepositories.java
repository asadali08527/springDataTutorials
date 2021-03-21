package com.tutorialspoint.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tutorialspoint.entities.User;
import java.lang.String;
import java.util.List;

import javax.transaction.Transactional;

import java.lang.Long;
import java.util.Date;

@Repository
public interface UserRepositories extends CrudRepository<User, Long> {

	List<User> findByFirstName(String firstname);

	List<User> findByLastName(String lastname);

	List<User> findByEmail(String email);

	List<User> findByFirstNameAndLastName(String firstName, String lastName);

	List<User> findBySalaryLessThan(Long salary);

	List<User> findByStartDateAfter(Date startdate);

	List<User> findByStartDateBefore(Date startdate);

	@Query("from User")
	List<User> findAllUsers();

	@Query("select u from User u where u.firstName=:name")
	List<User> findAllUsersbyName(String name);

	@Query("select u.firstName,u.lastName from User u")
	List<Object[]> findUserPartialData();

	@Modifying
	@Transactional
	@Query("delete from User where firstName=:fName")
	void deleteUserByFirstName(String fName);
	
	
}
