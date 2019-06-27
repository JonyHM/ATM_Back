package com.arm.atm.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ATMForm {
	
	@NotNull
	private Long number;
	@NotEmpty
	private String password;
	
	public UsernamePasswordAuthenticationToken parse() {
		return new UsernamePasswordAuthenticationToken(number.toString(), password);
	}
	
}
