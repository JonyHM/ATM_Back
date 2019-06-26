package com.arm.atm.resources.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.arm.atm.entity.User;
import com.arm.atm.service.data.UserServiceImpl;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserServiceImpl userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Object response = userService.getUser(username).get();
		
		if(response instanceof String) {
			throw new UsernameNotFoundException((String) response);
		}
		UserDS user = new UserDS((User) response);
		return user;
	}

}
