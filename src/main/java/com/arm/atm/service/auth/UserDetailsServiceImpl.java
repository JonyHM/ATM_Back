package com.arm.atm.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arm.atm.entity.User;
import com.arm.atm.resources.security.UserDS;
import com.arm.atm.service.data.UserServiceImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserServiceImpl userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Object response = userService.getUserByEmail(username).get();
		
		if(response instanceof String) {
			throw new UsernameNotFoundException((String) response);
		}
		
		return new UserDS((User) response);
	}

}
