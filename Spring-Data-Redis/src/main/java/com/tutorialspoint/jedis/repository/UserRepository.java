package com.tutorialspoint.jedis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tutorialspoint.jedis.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
