package com.arm.atm.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountForm {
	
	@NotNull @NotEmpty
	private String bankName;
	@NotNull @NotEmpty @Length(max = 6) @Length(min = 6)
	private Long accountNumber;
	@NotNull @NotEmpty
	private String password;
	@NotNull @NotEmpty
	private String owner;
	
}
