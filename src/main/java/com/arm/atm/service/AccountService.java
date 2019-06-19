package com.arm.atm.service;

import java.util.List;
import java.util.Optional;

import com.arm.atm.entity.Account;

import javassist.NotFoundException;

public interface AccountService {
	
		void create(Account account);
		Optional<?> edit(Long id, Account account) throws NotFoundException;
		Optional<?> getAccount(Long id);
		Account getAccountByNumber(Long accountNumber);
		Account getAccount(String owner);
		Optional<?> delete(Long id);
		List<Account> getAll(int page, int limit);
		List<Account> getAll();
		Long count();
}
