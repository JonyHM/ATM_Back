package com.arm.atm.service.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.arm.atm.entity.User;
import com.arm.atm.resources.security.UserDS;
import com.arm.atm.service.data.UserServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Value("${atm.jwt.expiration}")
	private String expiration;
	
	@Value("${atm.jwt.secret}")
	private String secret;
	
	@Autowired
	private UserServiceImpl service;

	@Override
	public String generateToken(Authentication auth) {
		UserDS loggedDetails = (UserDS)auth.getPrincipal();
		User logged = (User)service.getUserByEmail(loggedDetails.getUsername()).get();
		
		Date now = new Date();
		Date expDate = new Date(now.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("ATM Web API")
				.setSubject(logged.getId().toString())
				.setIssuedAt(now)
				.setExpiration(expDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

}
