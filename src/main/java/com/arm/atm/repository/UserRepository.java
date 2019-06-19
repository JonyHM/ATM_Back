package com.arm.atm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arm.atm.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByName(String name);
}
