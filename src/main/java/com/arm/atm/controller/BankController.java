package com.arm.atm.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arm.atm.component.BankParser;
import com.arm.atm.dto.BankDTO;
import com.arm.atm.entity.Bank;
import com.arm.atm.service.BankServiceImpl;

@RestController
@RequestMapping(value="/bank")
public class BankController {

	@Autowired
	private BankServiceImpl bankService;
	@Autowired
	private BankParser bankParser;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createBank(@RequestBody @Valid BankDTO bankForm) {
		Bank bank = bankParser.parse(bankForm);
		bankService.create(bank);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/bank/{id}")
                .buildAndExpand(bank.getId()).toUri();

		return ResponseEntity.created(location).body(new BankDTO(bank));		
	}

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<BankDTO> listBanks() {
		return BankDTO.parse(bankService.getAll());
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public BankDTO findBank(@PathVariable Long id) {
		return BankDTO.parse(bankService.getBank(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBank(@PathVariable Long id, @RequestBody @Valid BankDTO bankForm) {
		Bank bank = bankParser.parse(bankForm);
		bankService.edit(id, bank);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/bank")
                .buildAndExpand(bank.getId()).toUri();

		return ResponseEntity.created(location).body(new BankDTO(bank));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteBank(@PathVariable Long id) {
		bankService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
}
