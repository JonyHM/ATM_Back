package com.arm.atm.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arm.atm.entity.Account;
import com.arm.atm.entity.User;
import com.arm.atm.resource.security.AccountDS;
import com.arm.atm.resource.security.UserDS;
import com.arm.atm.service.data.AccountServiceImpl;
import com.arm.atm.service.data.UserServiceImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private AccountServiceImpl accountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Object response = userService.getUserByEmail(username).get();
		
		if (response instanceof String) {
			
			Object accountResponse = accountService.getAccountByNumber(Long.parseLong(username)).get();
			if (!(accountResponse instanceof String)) {
				return new AccountDS((Account)accountResponse);
			}
			
			throw new UsernameNotFoundException((String) response);
		}
		
		return new UserDS((User) response);
	}

}
