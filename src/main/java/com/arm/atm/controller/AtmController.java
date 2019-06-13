package com.arm.atm.controller;

import static org.springframework.http.HttpStatus.OK;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.arm.atm.component.Atm;
import com.arm.atm.dto.AccountDTO;
import com.arm.atm.dto.AtmDTO;

public class AtmController {
	
	@Autowired
	private Atm atm;
	
	@RequestMapping(value = "/deposit", method=RequestMethod.POST)
	public ResponseEntity<String> deposit(@RequestBody AtmDTO depositForm) {
		
		Atm atmSession = atm.authenticate(depositForm.getBankName(), 
												 depositForm.getAccountNumber(), 
												 depositForm.getPassword());
		
		deposit(depositForm.getValue());
		
		return new ResponseEntity<String>("Deposit successfull", OK);
	}
	
	@RequestMapping(value = "/withdraw", method=RequestMethod.POST)
	public ResponseEntity<String> createBank(@RequestBody AccountDTO depositForm) {
		
		Atm atmSession = atm.authenticate(depositForm.getBankName(), 
												 depositForm.getAccountNumber(), 
												 depositForm.getPassword());
		
		
		return new ResponseEntity<String>("Withdraw successfull:", OK);
	}
	
	@RequestMapping(value = "/balance", method=RequestMethod.POST)
	public ResponseEntity<String> balance(@RequestBody AccountDTO depositForm) {
		
		Atm atmSession = atm.authenticate(depositForm.getBankName(), 
												 depositForm.getAccountNumber(), 
												 depositForm.getPassword());
		
		
		return new ResponseEntity<String>("Balance: " + depositForm.getBankName(), OK);
	}
	
	public void deposit(BigInteger value, Long accountNumber) {
		return;
	}
}
