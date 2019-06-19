package com.arm.atm.controller;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arm.atm.component.AccountParser;
import com.arm.atm.dto.AccountDTO;
import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;
import com.arm.atm.form.AccountForm;
import com.arm.atm.service.AccountServiceImpl;
import com.arm.atm.service.BankServiceImpl;

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
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createAccount(@RequestBody @Valid AccountForm account) {
		Bank bank = bankService.getBank(account.getBankName());
		Account newAccount = accountParser.parse(account, bank);		
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
	@ResponseStatus(HttpStatus.OK)
	public List<AccountDTO> listAccounts() {
		return AccountDTO.parse(accountService.getAll());
	}
	
	/**
	 * Gives the details from certain account 
	 * @param id
	 * @return The details of a certain account by its given ID
	 */
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public AccountDTO findAccount(@PathVariable Long id) {
		return AccountDTO.parse(accountService.getAccount(id));
	}
	
	/**
	 * Updates an account by its given ID
	 * @param id
	 * @param account
	 * @return An message informing the successful update of the account
	 */
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody @Valid AccountForm account) {
		Bank bank = bankService.getBank(account.getBankName());
		Account newAccount = accountParser.parse(account, bank);	
		accountService.edit(id, newAccount);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/account/{id}")
                .buildAndExpand(newAccount.getId()).toUri();

		return ResponseEntity.created(location).body(new AccountDTO(newAccount));	
	}
	
	/**
	 * Deletes the account by its given ID
	 * @param id
	 * @return An message informing the successful deletion of the account
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
		accountService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
