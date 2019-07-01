package com.arm.atm.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arm.atm.component.WithdrawNotes;
import com.arm.atm.dto.AtmDTO;
import com.arm.atm.entity.Account;
import com.arm.atm.resource.withdraw.Note;
import com.arm.atm.service.data.AccountServiceImpl;

import javassist.NotFoundException;

@RestController
@RequestMapping("/atm")
public class ATMController {
	
	@Autowired
	private AccountServiceImpl accountService;
	
	@Autowired
	private WithdrawNotes withdrawNotes;
	
	/**
	 * Deposits the desirable amount of cash under your account
	 * @param depositForm with bank name, account number, password and value for deposit
	 * @return a ResposeEntity with a string informing that the deposit has been executed successfully
	 * @throws NotFoundException 
	 */
	@PostMapping("/deposit")
	@Transactional
	public ResponseEntity<?> deposit(@RequestBody AtmDTO depositForm) throws NotFoundException {
		
		return deposit(depositForm.getValue(), depositForm.getAccountNumber());
	}
	
	/**
	 * Withdraws the desirable amount of cash from your account
	 * @param withdrawForm with bank name, account number, password and value for withdrawal 
	 * @return a ResposeEntity with a string informing that the withdrawal has been executed 
	 * successfully and how much notes from each value you can get from your account
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/withdraw")
	@Transactional
	public ResponseEntity<String> withdraw(@RequestBody AtmDTO withdrawForm) throws Exception {
		
		Object notesNumberObject = withdraw(withdrawForm.getValue().setScale(2, RoundingMode.HALF_EVEN), withdrawForm.getAccountNumber()).get();		
		List<Note> cash = (List<Note>) notesNumberObject;
		List<String> response = new ArrayList<>();
		
		cash.forEach(banknote -> {
			if(banknote.isValid())
				response.add(banknote.toString() + "\n");
		});

		return ResponseEntity.ok("Requested value has been withdrawn successfully \n\nWithdrawn Notes:\n" + response);
	}
	
	/**
	 * Method to get the total balance of your account
	 * @param accountNumber
	 * @return a new ResponseEntity carrying the total balance
	 */
	@GetMapping("/balance/{id}")
	@Transactional
	public ResponseEntity<?> balance(@PathVariable Long id) {
		
		Object response = accountService.getAccount(id).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		return ResponseEntity.ok("Balance: " + ((Account) response).getBalance());
	}
	
	/**
	 * Method for account deposits. It sets the balance for the account and edit it on the Database.
	 * @param value
	 * @param accountNumber
	 * @throws NotFoundException 
	 */
	@Transactional
	private ResponseEntity<?> deposit(BigDecimal value, Long accountNumber) throws NotFoundException {
		Object responseAccount = accountService.getAccountByNumber(accountNumber).get();
		
		if(responseAccount instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseAccount);
		}
		
		Account account = (Account) responseAccount; 
		
		account.setBalance(account.getBalance().add(value));
		
		Object response = accountService.edit(account.getId(), account).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		Account editedAccount = (Account)response;
		
		return ResponseEntity.ok("Your deposit has been successful\n\nNew balance: " + editedAccount.getBalance());
	}
	
	/**
	 * Method for account withdrawal. It sets the balance for the account and edit it on the Database.
	 * @param value
	 * @param accountNumber
	 * @return a NoteDTO object, which contains the number of each available note on the ATM, according to the given value.
	 * @throws Exception
	 */
	@Transactional
	private Optional<?> withdraw(BigDecimal value, Long accountNumber) throws Exception {
		Object response = accountService.getAccountByNumber(accountNumber).get();
		
		if(response instanceof String) {
			return Optional.of(response);
		}
		
		Account account = (Account)response;
		
		if(value.compareTo(account.getBalance()) > 0) {
			throw new RuntimeException("Insufficient balance for withdrawal");
		} else {
			if(value.intValue() % 10 != 0) {
				throw new RuntimeException("Must be a multiple of ten");
			} else {
				account.setBalance(account.getBalance().subtract(value));
				accountService.edit(account.getId(), account);
			}
		}
		
		return Optional.of(withdrawNotes.withdrawal(value));
	}
	
}
