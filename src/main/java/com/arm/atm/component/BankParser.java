package com.arm.atm.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arm.atm.entity.Bank;

@Component
@Scope("prototype")
public class BankParser {

	public Bank parse(Bank bank) {
		Bank.BankBuilder bankBuilder = bank.toBuilder();
		return bankBuilder.build();
	}
	
}
