package com.arm.atm.resources.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.arm.atm.service.UserServiceImpl;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserServiceImpl userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDS user = new UserDS(userService.getUser(username));
		return user;
	}

}
