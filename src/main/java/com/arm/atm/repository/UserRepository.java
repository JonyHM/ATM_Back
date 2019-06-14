package com.arm.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arm.atm.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByName(String name);
}
