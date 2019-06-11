package com.arm.atm.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arm.atm.component.BankParser;
import com.arm.atm.entity.Bank;
import com.arm.atm.service.BankServiceImpl;

@Component
public class BankController {

	@Autowired
	private BankServiceImpl bankService;
	@Autowired
	private BankParser bankParser;
	
	@RequestMapping(value="/bank", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createBank(@RequestBody Bank bankForm) {
		Bank bank 	= bankParser.parse(bankForm);
		Bank responseBank = bankService.create(bank);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/bank")
                .buildAndExpand(responseBank.getId()).toUri();

		return ResponseEntity.created(location).build();		
	}
	
	@RequestMapping(value="/banks", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Bank> listBanks() {
		return bankService.getAll();
	}
	
	@RequestMapping(value="/bank/{id}", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Bank findBank(@PathVariable Long id) {
		return bankService.getBank(id);
	}
	
	@RequestMapping(value="/bank/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> updateBank(@PathVariable Long id, @RequestBody Bank bank) {
		Bank responseBank = bankService.edit(id, bank);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/bank")
                .buildAndExpand(responseBank.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@RequestMapping(value="/bank/{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteBank(@PathVariable Long id) {
		bankService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
}
