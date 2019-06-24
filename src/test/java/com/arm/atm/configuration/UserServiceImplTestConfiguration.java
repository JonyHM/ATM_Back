package com.arm.atm.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.arm.atm.service.UserService;
import com.arm.atm.service.UserServiceImpl;

@TestConfiguration
public class UserServiceImplTestConfiguration {
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
}
