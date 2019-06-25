package com.arm.atm.form;

import javax.validation.constraints.NotEmpty;

import com.arm.atm.entity.User;

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
	
	public static UserForm parse(User user) {
		return new UserForm(user.getName(), user.getPassword());
	}
}
