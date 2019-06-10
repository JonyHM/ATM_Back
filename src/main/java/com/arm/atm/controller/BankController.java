package com.arm.atm.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.arm.atm.component.BankParser;
import com.arm.atm.entity.Bank;
import com.arm.atm.repository.BankRepository;
import com.google.common.collect.Lists;

@Component
public class BankController {

	private BankRepository bankRepository;
	
	private BankParser bankParser;
	
	@RequestMapping(value = "/bank", method=RequestMethod.POST)
	public ResponseEntity<Bank> createBank(@RequestBody Bank bankForm) {
		
		
		Bank bank 	= bankParser.parse(bankForm);
		Bank bankDb = bankRepository.save(bank);
		
		return new ResponseEntity<Bank>(bankDb, OK);
	}
	
	@RequestMapping(value = "/banks", method=RequestMethod.GET)
	public ResponseEntity<List<Bank>> listBanks() {
		List<Bank> bankDb = Lists.newArrayList(bankRepository.findAll());
		
		return new ResponseEntity<List<Bank>>(bankDb, OK);
	}
	
}
