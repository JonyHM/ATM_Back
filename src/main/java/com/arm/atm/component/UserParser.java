package com.arm.atm.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.arm.atm.entity.User;
import com.arm.atm.form.UserForm;

/**
 * Parses the given information into a User object
 * 
 * @author jonathasmoraes
 *
 */
@Component
@Scope("prototype")
public class UserParser {

	/**
	 * Parses a UserForm object into a new User object
	 * 
	 * @param userForm
	 * @return a new User object for storage on Database
	 */
	public User parse(UserForm userForm) {
		User.UserBuilder userBuilder = User.builder();
		return userBuilder
				.name(userForm.getName())
				.password(userForm.getPassword())
				.build();
	}
}
