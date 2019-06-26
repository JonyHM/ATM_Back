package com.arm.atm.service.data;

import java.util.List;
import java.util.Optional;

import com.arm.atm.entity.Bank;

public interface BankService {

	void create(Bank bank);
	Optional<?> edit(Long id, Bank bank);
	Optional<?> getBank(Long id);
	Optional<?> getBank(String name);
	Optional<?> delete(Long id);
	List<Bank> getAll(int page, int limit);
	List<Bank> getAll();
	Long count();
}
