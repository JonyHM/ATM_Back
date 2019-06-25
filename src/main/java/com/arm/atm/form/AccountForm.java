package com.arm.atm.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.arm.atm.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountForm {
	
	@NotEmpty
	private String bankName;
	@NotNull
	private Long accountNumber;
	@NotEmpty
	private String password;
	@NotEmpty
	private String owner;
	
	public static AccountForm parse(Account account) {
		return new AccountForm(
				account.getBank().getName(), 
				account.getNumber(), 
				account.getPassword(), 
				account.getOwner().getName());
	}
	
}
