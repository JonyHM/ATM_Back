package com.arm.atm.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
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
	public User create(User user) {
		return repository.saveAndFlush(user);
	}

	@Override
	public User edit(Long id, User user) {
		User existingUser = repository.getOne(id);
		BeanUtils.copyProperties(existingUser, user);
		return repository.saveAndFlush(existingUser);
	}

	@Override
	public User getUser(Long id) {
		return repository.getOne(id);
	}

	@Override
	public User getUser(String name) {
		return repository.findByName(name);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
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
