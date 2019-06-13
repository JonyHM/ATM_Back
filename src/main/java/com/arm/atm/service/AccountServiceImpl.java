package com.arm.atm.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.arm.atm.entity.Account;
import com.arm.atm.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository repository;

	@Override
	public Account create(Account account) {
		return repository.saveAndFlush(account);
	}

	@Override
	public Account getAccount(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Account getAccount(String owner) {
		return repository.findByOwner(owner);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);;
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
	public Account edit(Long id, Account account) {
		Account existingAccount = repository.getOne(id);
		BeanUtils.copyProperties(existingAccount, account);
		return repository.saveAndFlush(existingAccount);		
	}

	@Override
	public Account getAccountByNumber(Long accountNumber) {
		return repository.findByNumber(accountNumber);
	}

}
