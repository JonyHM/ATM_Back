package com.arm.atm.service.auth;

import org.springframework.security.core.Authentication;

public interface LoginService {

	public String generateToken(Authentication auth);
}
