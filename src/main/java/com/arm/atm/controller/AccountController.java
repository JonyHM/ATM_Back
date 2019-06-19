package com.arm.atm.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arm.atm.component.AccountParser;
import com.arm.atm.dto.AccountDTO;
import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;
import com.arm.atm.form.AccountForm;
import com.arm.atm.service.AccountServiceImpl;
import com.arm.atm.service.BankServiceImpl;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="/account")
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
	@PostMapping()
	@Transactional
	public ResponseEntity<?> createAccount(@RequestBody @Valid AccountForm account) {
		Object bank = bankService.getBank(account.getBankName()).get();
		
		if(bank instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bank);
		}
		
		Object response = accountParser.parse(account, (Bank) bank).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		Account newAccount = (Account) response;
		
		accountService.create(newAccount);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/account/{id}")
                .buildAndExpand(newAccount.getId()).toUri();

		return ResponseEntity.created(location).body(new AccountDTO(newAccount));
	}
	
	/**
	 * 
	 * @return A list of all accounts stored within the database
	 */
	@GetMapping()
	public ResponseEntity<List<AccountDTO>> listAccounts() {
		return ResponseEntity.ok(AccountDTO.parse(accountService.getAll()));
	}
	
	/**
	 * Gives the details from certain account 
	 * @param id
	 * @return The details of a certain account by its given ID
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> findAccount(@PathVariable Long id) {
		Object response = accountService.getAccount(id).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		return ResponseEntity.ok(AccountDTO.parse((Account) response));
	}
	
	/**
	 * Updates an account by its given ID
	 * @param id
	 * @param account
	 * @return An message informing the successful update of the account
	 * @throws NotFoundException 
	 */
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody @Valid AccountForm account) throws NotFoundException {
		Object bank = bankService.getBank(account.getBankName()).get();
		
		if(bank instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bank);
		}
		
		Object parse = accountParser.parse(account, (Bank) bank).get();
		
		if(parse instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(parse);
		}
		
		Account newAccount = (Account) parse;
		
		Object response = accountService.edit(id, newAccount).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/account/{id}")
                .buildAndExpand(newAccount.getId()).toUri();

		return ResponseEntity.created(location).body(new AccountDTO((Account) response));	
	}
	
	/**
	 * Deletes the account by its given ID
	 * @param id
	 * @return An message informing the successful deletion of the account
	 */
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
		Object response = accountService.delete(id).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		return ResponseEntity.noContent().build();
	}
}
