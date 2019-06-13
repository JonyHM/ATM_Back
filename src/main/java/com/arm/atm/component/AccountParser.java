package com.arm.atm.component;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arm.atm.dto.AccountDTO;
import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;

@Component
@Scope("prototype")
public class AccountParser {

	public Account parse(AccountDTO account, Bank bank) {
		Account.AccountBuilder accountBuilder = Account.builder();
		Account newAccount = accountBuilder
				.number(account.getAccountNumber())
				.owner(account.getOwner())
				.password(account.getPassword())
				.balance(new BigDecimal(0))
				.bank(bank)
				.build();
		
		return newAccount;
	}

}
