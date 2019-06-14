package com.arm.atm.component;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arm.atm.dto.AccountDTO;
import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;
import com.arm.atm.service.UserServiceImpl;

/**
 * Parses the given information into a Account object
 * @author jonathasmoraes
 *
 */
@Component
@Scope("prototype")
public class AccountParser {

	@Autowired
	private UserServiceImpl userService;
	
	/**
	 * Parses a AccountDTO object and a Bank object into a new Account object
	 * @param account
	 * @param bank
	 * @return a new Account object for storage on Database
	 */
	public Account parse(AccountDTO account, Bank bank) {
		Account.AccountBuilder accountBuilder = Account.builder();
		Account newAccount = accountBuilder
				.number(account.getAccountNumber())
				.owner(userService.getUser(account.getOwner()))
				.password(account.getPassword())
				.balance(new BigDecimal(0))
				.bank(bank)
				.build();
		
		return newAccount;
	}

}
