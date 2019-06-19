package com.arm.atm.form;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserForm {
	
	@NotEmpty
	private String name;
	@NotEmpty
	private String password;
}
