package com.tutorialspoint.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.tutorialspoint.entity.Users;

public interface UsersRepository extends SolrCrudRepository<Users, Long> {
	public List<Users> findByName(String name);

	@Query("id:*?0* OR name:*?0*")
	public Page<Users> findByCustomQuery(String searchTerm, Pageable pageable);

	@Query(name = "Users.findByNamedQuery")
	public Page<Users> findByNamedQuery(String searchTerm, Pageable pageable);

}
