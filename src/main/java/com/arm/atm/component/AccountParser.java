package com.arm.atm.component;

import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;

public class AccountParser {

	public Account parse(Account account, Bank bank) {
		account.setBank(bank);
		return null;
	}

}
