package com.jeanReb.app1.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jeanReb.app1.entity.Role;
import com.jeanReb.app1.entity.User;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{


}
