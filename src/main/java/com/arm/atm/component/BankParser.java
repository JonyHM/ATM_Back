package com.arm.atm.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arm.atm.dto.BankDTO;
import com.arm.atm.entity.Bank;

@Component
@Scope("prototype")
public class BankParser {

	public Bank parse(BankDTO bank) {
		Bank.BankBuilder bankBuilder = Bank.builder();
		Bank newBank = bankBuilder
				.name(bank.getName())
				.build();
		
		return newBank;
	}
	
}
