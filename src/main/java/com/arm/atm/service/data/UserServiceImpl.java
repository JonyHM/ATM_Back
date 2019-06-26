package com.arm.atm.service.data;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.arm.atm.entity.User;
import com.arm.atm.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public void create(User user) {
		repository.saveAndFlush(user);
	}

	@Override
	public Optional<?> edit(Long id, User user) {
		Optional<User> optional = repository.findById(id);
		
		if(!optional.isPresent()) {
			return Optional.of("User with ID: " + id + " does not exist.");
		}
		
		User existingUser = optional.get();
		
		existingUser.setName(user.getName());
		existingUser.setPassword(user.getPassword());

		return optional;
	}

	@Override
	public Optional<?> getUser(Long id) {
		Optional<User> optional = repository.findById(id);
		
		if(optional.isPresent()) {
			return optional; 
		}
		
		return Optional.of("User with ID: " + id + " does not exist.");
	}

	@Override
	public Optional<?> getUser(String name) {
		Optional<User> optional = repository.findByName(name);
		
		if(optional.isPresent()) {
			return optional;
		}
		
		return Optional.of("User " + name + " does not exist."); 
	}

	@Override
	public Optional<?> delete(Long id) {
		Optional<User> optional = repository.findById(id);
		
		if(optional.isPresent()) {
			repository.deleteById(id);
			return optional;
		}
		
		return Optional.of("User with ID: " + id + " does not exist.");
	}

	@Override
	public List<User> getAll(int page, int limit) {
		return repository.findAll(PageRequest.of(page, limit)).getContent();
	}

	@Override
	public List<User> getAll() {
		return repository.findAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

}
