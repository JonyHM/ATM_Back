package com.arm.atm.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.arm.atm.component.Atm;

public class AtmController {
	
	private Atm atm;
	
	@RequestMapping(value = "/deposit", method=RequestMethod.POST)
	public ResponseEntity<String> deposit(@RequestBody DepositForm depositForm) {
		
		Atm atmSession = atm.authenticate(depositForm.getBankName(), 
												 depositForm.getAccountNumber(), 
												 depositForm.getPassword());
		
		deposit(depositForm.getValue());
		
		return new ResponseEntity<String>("Deposit successfull", OK);
	}
	
	@RequestMapping(value = "/withdraw", method=RequestMethod.POST)
	public ResponseEntity<String> createBank(@RequestBody DepositForm depositForm) {
		
		Atm atmSession = atm.authenticate(depositForm.getBankName(), 
												 depositForm.getAccountNumber(), 
												 depositForm.getPassword());
		
		
		return new ResponseEntity<String>("Withdraw successfull:", OK);
	}
	
	@RequestMapping(value = "/balance", method=RequestMethod.POST)
	public ResponseEntity<String> balance(@RequestBody DepositForm depositForm) {
		
		Atm atmSession = atm.authenticate(depositForm.getBankName(), 
												 depositForm.getAccountNumber(), 
												 depositForm.getPassword());
		
		
		return new ResponseEntity<String>("Balance: " + depositForm.getBankName(), OK);
	}
	
	public void deposit(String value) {
		return;
	}
}
