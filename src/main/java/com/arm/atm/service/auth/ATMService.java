package com.arm.atm.service.auth;

import org.springframework.security.core.Authentication;

public interface ATMService {

	public String generateToken(Authentication auth);
	public boolean isValid(String token);
}
