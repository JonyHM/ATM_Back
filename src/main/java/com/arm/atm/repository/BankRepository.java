package com.arm.atm.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.arm.atm.entity.Bank;

@Repository
public interface BankRepository {

	Bank findByName(String name);
	
	Bank save(Bank bank);
	
	List<Bank> findAll();
	
	Bank findOne(Long id);
	
}
