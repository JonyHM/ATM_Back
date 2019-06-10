package com.arm.atm.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.arm.atm.component.AccountParser;
import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;
import com.arm.atm.repository.AccountRepository;
import com.arm.atm.repository.BankRepository;

@Service
public class AccountController {

	public AccountRepository accountRepository;
	
	private BankRepository bankRepository;
	
	private AccountParser accountParser;
	
	@RequestMapping(value = "/account", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		
		validateAccount(account);
		
		Bank bank = bankRepository.findOne(account.getBank().getId());
		validateBank(bank);
		Account newAccount = accountParser.parse(account, bank);
		Account accountDb = accountRepository.save(newAccount);
		return new ResponseEntity<Account>(accountDb, OK);
	}

	private void validateBank(Bank bank) {
		Optional.ofNullable(bank).orElseThrow(()-> new RuntimeException("Bank does not exist."));
	}

	private void validateAccount(Account account) {
		Account existingAccount = accountRepository.find(account.getId(), account.getNumber());
		
		Optional.ofNullable(existingAccount).orElseThrow(()-> new RuntimeException("Account does not exist."));
	}
}
