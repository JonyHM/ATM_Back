package com.arm.atm.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;

@Component
public class DBService implements CommandLineRunner {
	
	@Autowired
	private BankServiceImpl bankService;
	@Autowired
	private AccountServiceImpl accountService;

	@Override
	public void run(String... args) throws Exception {
		Bank bank = new Bank(null, "Santander", null);
		bankService.create(bank);
		
		Account account = new Account(null, 55234L, "José", "123abc", new BigDecimal(250), bank);
		accountService.create(account);
		
		System.out.println("Nova conta criada com sucesso!");
		
	}

}
