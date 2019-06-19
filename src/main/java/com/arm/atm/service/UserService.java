package com.arm.atm.service;

import java.util.List;

import com.arm.atm.entity.User;

public interface UserService {
	
	void create(User user);
	void edit(Long id, User user);
	User getUser(Long id);
	User getUser(String name);
	void delete(Long id);
	List<User> getAll(int page, int limit);
	List<User> getAll();
	Long count();
}
