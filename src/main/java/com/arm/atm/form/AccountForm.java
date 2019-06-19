package com.arm.atm.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	
}
