package com.arm.atm.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.arm.atm.entity.User;
import com.arm.atm.form.UserForm;
import com.arm.atm.service.data.ProfileServiceImpl;

/**
 * Parses the given information into a User object
 * 
 * @author jonathasmoraes
 *
 */
@Component
@Scope("prototype")
public class UserParser {
	
	@Autowired
	private ProfileServiceImpl profileService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Parses a UserForm object into a new User object
	 * 
	 * @param userForm
	 * @return a new User object for storage on Database
	 */
	public User parse(UserForm userForm) {
		User.UserBuilder userBuilder = User.builder();
		return userBuilder
				.email(userForm.getEmail())
				.name(userForm.getName())
				.password(bCryptPasswordEncoder.encode(userForm.getPassword()))
				.profile(profileService.getByName(userForm.getProfile()))
				.build();
	}
}
