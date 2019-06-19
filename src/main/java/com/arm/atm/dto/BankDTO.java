package com.arm.atm.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.arm.atm.entity.Bank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankDTO {
	
	@NotNull @NotEmpty
	private String name;
	
	public BankDTO(Bank bank) {
		this.name = bank.getName();
	}

	public static List<BankDTO> parse(List<Bank> banks) {
		return banks.stream().map(BankDTO::new).collect(Collectors.toList());
	}

	public static BankDTO parse(Bank bank) {
		return new BankDTO(bank);
	}
}
