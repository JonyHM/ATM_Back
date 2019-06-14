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
	
	/**
	 * Method to create a new Account with the information given by a form, in frontend, and store it into de database
	 * @param account
	 * @return A message of success for the creation of the new account object
	 */	
	@RequestMapping(value="/account", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createAccount(@RequestBody AccountDTO account) {
		Bank bank = bankService.getBank(account.getBankName());
		Account newAccount = accountParser.parse(account, bank);		
		Account responseAccount = accountService.create(newAccount);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/account")
                .buildAndExpand(responseAccount.getId()).toUri();

		return ResponseEntity.created(location).build();		
	}
	
	/**
	 * 
	 * @return A list of all accounts stored within the database
	 */
	@RequestMapping(value="/accounts", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Account> listAccounts() {
		return accountService.getAll();
	}
	
	/**
	 * Gives the details from certain account 
	 * @param id
	 * @return The details of a certain account by its given ID
	 */
	@RequestMapping(value="/account/{id}", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Account findAccount(@PathVariable Long id) {
		return accountService.getAccount(id);
	}
	
	/**
	 * Updates an account by its given ID
	 * @param id
	 * @param account
	 * @return An message informing the successful update of the account
	 */
	@RequestMapping(value="/account/{id}", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody Account account) {
		Account responseAccount = accountService.edit(id, account);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/account/{id}")
                .buildAndExpand(responseAccount.getId()).toUri();

		return ResponseEntity.created(location).build();	
	}
	
	/**
	 * Deletes the account by its given ID
	 * @param id
	 * @return An message informing the successful deletion of the account
	 */
	@RequestMapping(value="/account/{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
		accountService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
