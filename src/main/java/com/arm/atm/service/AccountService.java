package com.arm.atm.service;

import java.util.List;

import com.arm.atm.entity.Account;

public interface AccountService {
	
		void create(Account account);
		void edit(Long id, Account account);
		Account getAccount(Long id);
		Account getAccountByNumber(Long accountNumber);
		Account getAccount(String owner);
		void delete(Long id);
		List<Account> getAll(int page, int limit);
		List<Account> getAll();
		Long count();
}
