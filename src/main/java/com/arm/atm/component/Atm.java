package com.arm.atm.component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.arm.atm.entity.Account;
import com.arm.atm.service.AccountServiceImpl;

@Scope(SCOPE_PROTOTYPE)
public class Atm {
	
	@Autowired
	private AccountServiceImpl accountService;
	
	public Account authenticate(String bank, Long number, String password) {
		
		return findAccount(bank, number, password)
							.orElseThrow(()-> new RuntimeException("Account number or password are incorrect!"));
	}

	private Optional<Account> findAccount(String bank, Long number, String password) {
		Account existingAccount = accountService.getAccountByNumber(number);
		
		return Optional.ofNullable(existingAccount);
	} 
}
