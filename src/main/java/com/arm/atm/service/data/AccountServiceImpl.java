package com.arm.atm.service.data;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.arm.atm.entity.Account;
import com.arm.atm.repository.AccountRepository;

import javassist.NotFoundException;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository repository;

	@Override
	public void create(Account account) {
		repository.saveAndFlush(account);
	}

	/**
	 * Gets an account by its given ID
	 * @param id
	 * @return an optional of Account type if the given ID is valid on the database. Otherwise, returns an optional of a string, informing that the account does not exist
	 */
	@Override
	public Optional<?> getAccount(Long id) {
		Optional<Account> optional = repository.findById(id);
		
		if(optional.isPresent()) {			
			return optional;
		}
		
		return Optional.of("Account with ID: " + id + " does not exist.");
	}

	/**
	 * Gets an account by its given owner name
	 * @param name
	 * @return an optional of Account type if the given owner name is valid on the database. Otherwise, returns an optional of a string, informing that the account does not exist
	 */
	@Override
	public Optional<?> getAccount(String owner) {
		Optional<Account> optional = repository.findByOwnerName(owner);
		
		if(optional.isPresent()) {
			return optional; 
		}
		
		return Optional.of("Account with owner named " + owner + " does not exist.");
	}

	/**
	 * Deletes an account by its given ID
	 * @param id
	 * @return an optional of Account type if the given ID is valid on the database. Otherwise, returns an optional of a string, informing that the account does not exist
	 */
	@Override
	public Optional<?> delete(Long id) {
		Optional<Account> optional = repository.findById(id);
		
		if(optional.isPresent()) {
			repository.deleteById(id);
			return optional;
		}
		
		return Optional.of("Account with ID: " + id + " does not exist.");
	}

	@Override
	public List<Account> getAll(int page, int limit) {
		return repository.findAll(PageRequest.of(page, limit)).getContent();
	}

	@Override
	public List<Account> getAll() {
		return repository.findAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	/**
	 * Edits an account by its given ID
	 * @param id
	 * @param bank
	 * @return an optional of Account type if the given ID is valid on the database. Otherwise, returns an optional of a string, informing that the account does not exist
	 */
	@Override
	public Optional<?> edit(Long id, Account account) throws NotFoundException {
		Optional<Account> optional = repository.findById(id);
		
		if(!optional.isPresent()) {			
			return Optional.of("Account with ID: " + id + " does not exist.");
		}
		
		Account existingAccount = optional.get();
		
		existingAccount.setBank(account.getBank());
		existingAccount.setPassword(account.getPassword());
		existingAccount.setOwner(account.getOwner());
		
		
		return Optional.of(existingAccount);
	}

	/**
	 * Deletes an account by its given number
	 * @param id
	 * @return an optional of Account type if the given number is valid on the database. Otherwise, returns an optional of a string, informing that the account does not exist
	 */
	@Override
	public Optional<?> getAccountByNumber(Long accountNumber) {
		Optional<Account> optional = repository.findByNumber(accountNumber);
		
		if(optional.isPresent()) {
			return optional; 
		}
		
		return Optional.of("Account with number: " + accountNumber + " does not exist.");
	}

}
