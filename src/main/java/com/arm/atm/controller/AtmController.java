package com.arm.atm.controller;

import static org.springframework.http.HttpStatus.OK;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
		
		return new ResponseEntity<String>("Your deposit has been successful", OK);
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
		NotesDTO notesNumberObject = withdraw(withdrawForm.getValue().setScale(2, RoundingMode.HALF_EVEN), withdrawForm.getAccountNumber());
		
		String notesNumber = notesNumber(notesNumberObject);

		return new ResponseEntity<String>("Requested value has been withdrawn successfully \n\nNotes:\n" + notesNumber, OK);
	}
	
	/**
	 * Method to get the total balance of your account
	 * @param accountNumber
	 * @return a new ResponseEntity carrying the total balance
	 */
	@RequestMapping(value = "/balance/{id}", method=RequestMethod.GET)
	public ResponseEntity<String> balance(@PathVariable Long id) {
		
//		Atm atmSession = atm.authenticate(depositForm.getBankName(), 
//												 depositForm.getAccountNumber(), 
//												 depositForm.getPassword());
		
		Account account = accountService.getAccount(id);
		
		return new ResponseEntity<String>("Balance: " + account.getBalance(), OK);
	}
	
	/**
	 * Method for account deposits. It sets the balance for the account and edit it on the Database.
	 * @param value
	 * @param accountNumber
	 */
	private void deposit(BigDecimal value, Long accountNumber) {
		Account account = accountService.getAccountByNumber(accountNumber);
		
		account.setBalance(account.getBalance().add(value));
		accountService.edit(account.getId(), account);
	}
	
	/**
	 * Method for account withdrawal. It sets the balance for the account and edit it on the Database.
	 * @param value
	 * @param accountNumber
	 * @return a NotesDTO object, which contains the number of each available note on the ATM, according to the given value.
	 * @throws Exception
	 */
	private NotesDTO withdraw(BigDecimal value, Long accountNumber) throws Exception {
		Account account = accountService.getAccountByNumber(accountNumber);
		
		if(value.compareTo(account.getBalance()) > 0) {
			throw new RuntimeException("Insufficient balance for withdrawal");
		} else {
			account.setBalance(account.getBalance().subtract(value));
			accountService.edit(account.getId(), account);
		}
		
		return withdrawNotes.withdrawal(value);
	}
	
	/**
	 * Method to build a string with the number of each note that will be returned to the user
	 * @param notes
	 * @return
	 */
	private String notesNumber(NotesDTO notes) {
		StringBuilder builder = new StringBuilder();
		
		if(notes.getHundred() > 0) {
			builder.append("Hundred: " + notes.getHundred() + "\n");
		}
		
		if(notes.getFifty() > 0) {
			builder.append("Fifty: " + notes.getFifty() + "\n");
		} 
		
		if(notes.getTwenty() > 0) {
			builder.append("Twenty: " + notes.getTwenty() + "\n");
		} 
		
		if(notes.getTen() > 0) {
			builder.append("Ten: " + notes.getTen() + "\n");
		}
		
		return builder.toString();
	}
}
