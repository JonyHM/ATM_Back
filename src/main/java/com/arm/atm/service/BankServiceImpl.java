package com.arm.atm.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
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
	public Bank create(Bank bank) {
		return repository.saveAndFlush(bank);
	}

	@Override
	public Bank getBank(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Bank getBank(String name) {
		return repository.findByName(name);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
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
	public Bank edit(Long id, Bank bank) {
		Bank existingBank = repository.getOne(id);
		BeanUtils.copyProperties(existingBank, bank);
		return repository.saveAndFlush(existingBank);		
	}

}
