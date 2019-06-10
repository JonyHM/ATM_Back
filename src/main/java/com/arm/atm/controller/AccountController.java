package com.arm.atm.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;
import com.arm.atm.repository.AccountRepository;
import com.arm.atm.repository.BankRepository;

@Service
public class AccountController {

	public AccountRepository accountRepository;
	
	private BankRepository bankRepository;
	
	private AccountParser accountParser;
	
	static class ACCOUNTForm {
		private String bankName;
		private Long number;
		private Long id;
		private String password;
		
		public String getBankName() {
			return bankName;
		}
		
		public Long getNumber() {
			return number;
		}
		
		public Long getId() {
			return id;
		}
		
		public String getPassword() {
			return password;
		}
		
		public String getValue() {
			return "value";
		}
	}
	
	@RequestMapping(value = "/account", method=RequestMethod.POST)
	public ResponseEntity<Account> createAccount(@RequestBody ACCOUNTForm account) {
		
		validateAccount(account);
		
		Bank bank = bankRepository.findOne(account.getId());
		validateBank(bank);
		Account newAccount = accountParser.parse(account, bank);
		Account accountDb = accountRepository.save(newAccount);
		return new ResponseEntity<Account>(accountDb, OK);
	}

	private void validateBank(Bank bank) {
		Optional.ofNullable(bank).orElseThrow(()-> new RuntimeException("Bank does not exist."));
	}

	private void validateAccount(ACCOUNTForm account) {
		Account existingAccount = accountRepository.find(account.getId(), account.getNumber());
		
		Optional.ofNullable(existingAccount).orElseThrow(()-> new RuntimeException("Account does not exist."));
	}
}
