package com.arm.atm.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arm.atm.component.BankParser;
import com.arm.atm.dto.BankDTO;
import com.arm.atm.entity.Bank;
import com.arm.atm.service.data.BankServiceImpl;

@RestController
@RequestMapping(value="/bank")
public class BankController {

	@Autowired
	private BankServiceImpl bankService;
	@Autowired
	private BankParser bankParser;
	
	@PostMapping()
	@Transactional
	@CacheEvict(value = {"bankList", "singleBank"}, allEntries = true)
	public ResponseEntity<?> createBank(@RequestBody @Valid BankDTO bankForm) {
		Bank bank = bankParser.parse(bankForm);
		bankService.create(bank);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/bank/{id}")
                .buildAndExpand(bank.getId()).toUri();

		return ResponseEntity.created(location).body(new BankDTO(bank));		
	}

	@GetMapping()
	@Cacheable(value = "bankList")
	public ResponseEntity<List<BankDTO>> listBanks() {
		return ResponseEntity.ok(BankDTO.parse(bankService.getAll()));
	}
	
	@GetMapping("/{id}")
	@Cacheable(value = "singleBank")
	public ResponseEntity<?> findBank(@PathVariable Long id) {
		Object response = bankService.getBank(id).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		return ResponseEntity.ok(BankDTO.parse((Bank) response));
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = {"bankList", "singleBank"}, allEntries = true)
	public ResponseEntity<?> updateBank(@PathVariable Long id, @RequestBody @Valid BankDTO bankForm) {
		Bank bank = bankParser.parse(bankForm);
		
		Object response = bankService.edit(id, bank).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/bank")
                .buildAndExpand(bank.getId()).toUri();

		return ResponseEntity.created(location).body(new BankDTO(bank));
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = {"bankList", "singleBank"}, allEntries = true)
	public ResponseEntity<?> deleteBank(@PathVariable Long id) {
		Object response = bankService.delete(id).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	
}
