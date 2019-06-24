package com.arm.atm.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.arm.atm.service.BankService;
import com.arm.atm.service.BankServiceImpl;

@TestConfiguration
public class BankServiceImplTestConfiguration {
	
	@Bean
	public BankService bankService() {
		return new BankServiceImpl();
	}
}
