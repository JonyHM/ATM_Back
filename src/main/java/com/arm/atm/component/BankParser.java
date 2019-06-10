package com.arm.atm.component;

import org.springframework.stereotype.Component;

import com.arm.atm.entity.Bank;
@Component
public class BankParser {

	public Bank parse(Bank bank) {
		return Bank.builder().id(1l).build();
	}
	
}
