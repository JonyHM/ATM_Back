package com.arm.atm.component;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;
import com.arm.atm.entity.User;
import com.arm.atm.form.AccountForm;
import com.arm.atm.service.data.UserServiceImpl;

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
	 * @return an optional of Account object for storage on Database. If some information given from AccountForm is invalid, returns a optional of String informing which information is incorrect or invalid
	 */
	public Optional<?> parse(AccountForm account, Bank bank) {
		Account.AccountBuilder accountBuilder = Account.builder();
		
		Object response = userService.getUser(account.getOwner()).get();
		
		if(response instanceof String) {
			return Optional.of(response);
		}
		
		return Optional.of(accountBuilder
				.number(account.getAccountNumber())
				.owner((User) response)
				.password(account.getPassword())
				.balance(new BigDecimal(0))
				.bank(bank)
				.build());
	}

}
