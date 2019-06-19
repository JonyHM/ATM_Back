package com.arm.atm.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.arm.atm.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDTO {
	
	private String bankName;
	private Long accountNumber;
	private String owner;
	
	public AccountDTO(Account account) {
		this.bankName = account.getBank().getName();
		this.accountNumber = account.getNumber();
		this.owner = account.getOwner().getName();
	}
	
	public static List<AccountDTO> parse(List<Account> accounts) {
		return accounts.stream().map(AccountDTO::new).collect(Collectors.toList());
	}

	public static AccountDTO parse(Account account) {
		return new AccountDTO(account);
	}
}
