package com.arm.atm.component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arm.atm.entity.Account;
import com.arm.atm.repository.AccountRepository;

@Scope(SCOPE_PROTOTYPE)
public class Atm {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
    private ApplicationContext сontext;
	
	public Atm authenticate(String bank, Long number, String password) {
		
		Account account = findAccount(bank, number, password)
							.orElseThrow(()-> new RuntimeException("Usuário ou conta inválidos"));
		
		return сontext.getBean(Atm.class, account);
	}

	private Optional<Account> findAccount(String bank, Long number, String password) {
		Account account = accountRepository.find(bank, number, password);
		return Optional.ofNullable(account);
	} 
}
