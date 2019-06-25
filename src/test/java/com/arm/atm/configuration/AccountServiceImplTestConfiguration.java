package com.arm.atm.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.arm.atm.service.AccountService;
import com.arm.atm.service.AccountServiceImpl;

@TestConfiguration
public class AccountServiceImplTestConfiguration {
	
	@Bean
	public AccountService accountService() {
		return new AccountServiceImpl();
	}
}