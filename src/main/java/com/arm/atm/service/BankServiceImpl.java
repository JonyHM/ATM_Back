package com.arm.atm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.arm.atm.entity.Bank;
import com.arm.atm.repository.BankRepository;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private BankRepository repository;
	
	@Override
	public void create(Bank bank) {
		repository.saveAndFlush(bank);
	}

	@Override
	public Optional<?> getBank(Long id) {
		Optional<Bank> bank = repository.findById(id);
		
		if(bank.isPresent()) {
			return bank;
		}
		
		return Optional.of("Bank with ID: " + id + " does not exist.");
	}

	@Override
	public Optional<?> getBank(String name) {
		Optional<Bank> bank = repository.findByName(name);
		
		if(bank.isPresent()) {			
			return bank; 
		}
		return Optional.of("Bank " + name + " does not exist.");
	}

	@Override
	public Optional<?> delete(Long id) {
		Optional<Bank> optional = repository.findById(id);
		
		if(optional.isPresent()) {
			repository.deleteById(id);
			return optional;
		}
		
		return Optional.of("Bank with ID: " + id + " does not exist.");
	}

	@Override
	public List<Bank> getAll(int page, int limit) {
		return repository.findAll(PageRequest.of(page, limit)).getContent();
	}

	@Override
	public List<Bank> getAll() {
		return repository.findAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	@Override
	public Optional<?> edit(Long id, Bank bank) {		
		Optional<Bank> optional = repository.findById(id);
		
		if(!optional.isPresent()) {
			return Optional.of("Bank with ID: " + id + " does not exist.");
		}
		
		Bank existingBank = optional.get();
		
		existingBank.setName(bank.getName());

		return optional;
	}

}
