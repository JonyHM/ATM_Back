package com.arm.atm.service;

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

	@Override
	public Optional<?> getAccount(Long id) {
		Optional<Account> optional = repository.findById(id);
		
		if(optional.isPresent()) {			
			return optional;
		}
		
		return Optional.of("Account with ID: " + id + " does not exist.");
	}

	@Override
	public Account getAccount(String owner) {
		return repository.findByOwnerName(owner);
	}

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
		
		
		return optional;
	}

	@Override
	public Account getAccountByNumber(Long accountNumber) {
		return repository.findByNumber(accountNumber);
	}

}
