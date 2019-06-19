package com.arm.atm.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arm.atm.entity.User;
import com.arm.atm.form.UserForm;

@Component
@Scope("prototype")
public class UserParser {

	public User parse(UserForm userForm) {
		User.UserBuilder userBuilder = User.builder();
		return userBuilder
				.name(userForm.getName())
				.password(userForm.getPassword())
				.build();
	}
}
