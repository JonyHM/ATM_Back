package com.arm.atm.service;

import java.util.List;

import com.arm.atm.entity.Bank;

public interface BankService {

	void create(Bank bank);
	void edit(Long id, Bank bank);
	Bank getBank(Long id);
	Bank getBank(String name);
	void delete(Long id);
	List<Bank> getAll(int page, int limit);
	List<Bank> getAll();
	Long count();
}
