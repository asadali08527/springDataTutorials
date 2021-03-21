package com.tutorialspoint.repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tutorialspoint.entities.User;

@RepositoryRestResource(collectionResourceRel = "app-users", path = "app-users")
public interface UserRepositories extends PagingAndSortingRepository<User, Long> {

	List<User> findByFirstName(String firstname);

	List<User> findByLastName(String lastname);

	List<User> findByEmail(@Param("email") String email);

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
