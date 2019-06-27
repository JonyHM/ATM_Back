package com.arm.atm.service.data;

import java.util.List;
import java.util.Optional;

import com.arm.atm.entity.User;

public interface UserService {
	
	void create(User user);
	Optional<?> edit(Long id, User user);
	User getOne(Long id);
	Optional<?> getUser(Long id);
	Optional<?> getUser(String name);
	Optional<?> getUserByEmail(String email);
	Optional<?> delete(Long id);
	List<User> getAll(int page, int limit);
	List<User> getAll();
	Long count();
}
