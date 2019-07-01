package com.arm.atm.form;

import javax.validation.constraints.NotEmpty;

import com.arm.atm.entity.User;
import com.arm.atm.resource.security.AuthorizationLevel;

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
	private String email;
	@NotEmpty
	private String password;
	
	private AuthorizationLevel profile;
	
	public static UserForm parse(User user) {
		return new UserForm(user.getName(), user.getEmail(), user.getPassword(), user.getProfile().getName());
	}
}
