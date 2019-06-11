package com.arm.atm.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;

@Component
@Scope("prototype")
public class AccountParser {

	public Account parse(Account account, Bank bank) {
		Account.AccountBuilder accountBuilder = account.toBuilder();
		return accountBuilder.bank(bank).build();
	}

}
