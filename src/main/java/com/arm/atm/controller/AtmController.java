package com.arm.atm.controller;

import static org.springframework.http.HttpStatus.OK;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.arm.atm.component.WithdrawNotes;
import com.arm.atm.dto.AtmDTO;
import com.arm.atm.dto.NotesDTO;
import com.arm.atm.entity.Account;
import com.arm.atm.service.AccountServiceImpl;

@RestController
public class AtmController {
	
//	@Autowired
//	private Atm atm;
	
	@Autowired
	private AccountServiceImpl accountService;
	
	@Autowired
	private WithdrawNotes withdrawNotes;
	
	/**
	 * Deposits the desirable amount of cash under your account
	 * @param depositForm with bank name, account number, password and value for deposit
	 * @return a ResposeEntity with a string informing that the deposit has been executed successfully
	 */
	
	@RequestMapping(value = "/deposit", method=RequestMethod.POST)
	public ResponseEntity<String> deposit(@RequestBody AtmDTO depositForm) {
		
//		Account atmSession = atm.authenticate(depositForm.getBankName(), 
//												 depositForm.getAccountNumber(), 
//												 depositForm.getPassword());
		
		deposit(depositForm.getValue(), depositForm.getAccountNumber());
		
		return new ResponseEntity<String>("Deposit successfull", OK);
	}
	
	/**
	 * Withdraws the desirable amount of cash from your account
	 * @param withdrawForm with bank name, account number, password and value for withdrawal 
	 * @return a ResposeEntity with a string informing that the withdrawal has been executed 
	 * successfully and how much notes from each value you can get from your account
	 * @throws Exception
	 */
	@RequestMapping(value = "/withdraw", method=RequestMethod.POST)
	public ResponseEntity<String> withdraw(@RequestBody AtmDTO withdrawForm) throws Exception {
		
//		Atm atmSession = atm.authenticate(depositForm.getBankName(), 
//												 depositForm.getAccountNumber(), 
//												 depositForm.getPassword());
		withdraw(withdrawForm.getValue(), withdrawForm.getAccountNumber());
		
		// Criar método no withdrawNotes para montar uma string com o número de notas a serem sacadas
		return new ResponseEntity<String>("Withdraw successfull:", OK);
	}
	
	/**
	 * Method to get the total balance of your account
	 * @param accountNumber
	 * @return a new ResponseEntity carrying the total balance
	 */
	@RequestMapping(value = "/balance", method=RequestMethod.POST)
	public ResponseEntity<String> balance(@RequestBody Long accountNumber) {
		
//		Atm atmSession = atm.authenticate(depositForm.getBankName(), 
//												 depositForm.getAccountNumber(), 
//												 depositForm.getPassword());
		
		Account account = accountService.getAccountByNumber(accountNumber);
		
		return new ResponseEntity<String>("Balance: " + account.getBalance(), OK);
	}
	
	public void deposit(BigDecimal value, Long accountNumber) {
		Account account = accountService.getAccountByNumber(accountNumber);
		
		account.setBalance(account.getBalance().add(value));
		accountService.edit(account.getId(), account);
	}
	
	public NotesDTO withdraw(BigDecimal value, Long accountNumber) throws Exception {
		Account account = accountService.getAccountByNumber(accountNumber);
		
		if(value.compareTo(account.getBalance()) > 0) {
			throw new RuntimeException("Insufficient balance for withdrawal");
		} else {
			account.setBalance(account.getBalance().subtract(value));
		}
		
		return withdrawNotes.withdrawal(value);
	}
}
