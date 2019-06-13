package com.arm.atm.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arm.atm.component.AccountParser;
import com.arm.atm.dto.AccountDTO;
import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;
import com.arm.atm.service.AccountServiceImpl;
import com.arm.atm.service.BankServiceImpl;

@RestController
public class AccountController {

	@Autowired
	private AccountServiceImpl accountService;
	@Autowired
	private BankServiceImpl bankService;
	@Autowired
	private AccountParser accountParser;
	
	@RequestMapping(value="/account", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	// Receberá dados do front no molde do AccountDTO, esses dados serão parseados para um novo objeto de account
	public ResponseEntity<?> createAccount(@RequestBody AccountDTO account) {
		Bank bank = bankService.getBank(account.getBankName());
		Account newAccount = accountParser.parse(account, bank);		
		Account responseAccount = accountService.create(newAccount);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/account")
                .buildAndExpand(responseAccount.getId()).toUri();

		return ResponseEntity.created(location).build();		
	}
	
	@RequestMapping(value="/accounts", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Account> listAccounts() {
		return accountService.getAll();
	}
	
	@RequestMapping(value="/account/{id}", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Account findAccount(@PathVariable Long id) {
		return accountService.getAccount(id);
	}
	
	@RequestMapping(value="/account/{id}", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody Account account) {
		Account responseAccount = accountService.edit(id, account);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/account/{id}")
                .buildAndExpand(responseAccount.getId()).toUri();

		return ResponseEntity.created(location).build();	
	}
	
	@RequestMapping(value="/account/{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
		accountService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
