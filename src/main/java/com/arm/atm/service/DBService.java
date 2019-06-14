package com.arm.atm.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;
import com.arm.atm.entity.User;

@Component
public class DBService implements CommandLineRunner {
	
	@Autowired
	private BankServiceImpl bankService;
	
	@Autowired
	private AccountServiceImpl accountService;
	
	@Autowired
	private UserServiceImpl userService;

	@Override
	public void run(String... args) throws Exception {
		Bank bank = new Bank(null, "Santander", null);
		bankService.create(bank);
		
		User user = new User(null, "José", "123abc", null);
		userService.create(user);
		
		Account account = new Account(null, 55234L, user, "123abc", new BigDecimal(250), bank);
		accountService.create(account);
	}

}
