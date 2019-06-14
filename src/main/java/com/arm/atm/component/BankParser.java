package com.arm.atm.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arm.atm.dto.BankDTO;
import com.arm.atm.entity.Bank;

/**
 * Parses the given information into a Bank object
 * @author jonathasmoraes
 *
 */
@Component
@Scope("prototype")
public class BankParser {
	
	/**
	 * Parses a BankDTO object into a new Bank object
	 * 
	 * @param bank
	 * @return a new Bank object for storage on Database
	 */
	public Bank parse(BankDTO bank) {
		Bank.BankBuilder bankBuilder = Bank.builder();
		Bank newBank = bankBuilder
				.name(bank.getName())
				.build();
		
		return newBank;
	}
	
}
