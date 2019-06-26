package com.arm.atm.form;

import javax.validation.constraints.NotEmpty;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginForm {
	
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	
	public UsernamePasswordAuthenticationToken parse() {
		System.out.println(password);
		return new UsernamePasswordAuthenticationToken(email, password);
	}
	
}
