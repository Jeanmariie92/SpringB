package com.jeanReb.app1.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.jeanReb.app1.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{

	
	public Optional<User> findByUsername(String username);
		
	
}
