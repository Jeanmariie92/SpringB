package com.jeanReb.app1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeanReb.app1.entity.User;
import com.jeanReb.app1.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	@Override
	public Iterable<User> getAllUsers() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	
	public User createUser(User formUser) throws Exception {
		
		if(checkUsernameAvailable(formUser)
				&& checkPasswordMatch(formUser)) {
			
			
			formUser = repository.save(formUser);
			
			
		}
		return formUser;
	}
	
	private boolean checkUsernameAvailable(User user) throws Exception {
		
		Optional<User> userFound = repository.findByUsername(user.getUsername());
		if(userFound.isPresent()) {
			
			throw new Exception("Username already exists");
		}
		return true;
		
	}
	
	private boolean checkPasswordMatch(User user) throws Exception{
		
		if ( !user.getPassword().equals(user.getConfirmPassword())
				) {
			
			throw new Exception("Password doesn't match");
		}
		
		return true;
	}


	@Override
	public User getUserById(Long id) throws Exception {
		
		return repository.findById(id).orElseThrow(() -> new Exception("El usuario para editar no existe"));
	
	}


	@Override
	public User updateUser(User fromUser) throws Exception {
		
		User toUser  = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		return  repository.save(toUser);
		
		
	}
	
	protected void mapUser(User from, User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
		
		
	}
	
	
	
	
	
	
}
