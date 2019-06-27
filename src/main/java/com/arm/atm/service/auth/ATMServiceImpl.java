package com.arm.atm.service.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.arm.atm.entity.Account;
import com.arm.atm.resource.security.AccountDS;
import com.arm.atm.service.data.AccountServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ATMServiceImpl implements ATMService {
	
	@Value("${atm.jwt.expiration}")
	private String expiration;
	
	@Value("${atm.jwt.secret}")
	private String secret;
	
	@Autowired
	private AccountServiceImpl service;

	@Override
	public String generateToken(Authentication auth) {
		AccountDS loggedDetails = (AccountDS)auth.getPrincipal();
		
		Account logged = (Account)service.getAccountByNumber(Long.parseLong(loggedDetails.getUsername())).get();
		
		Date now = new Date();
		Date expDate = new Date(now.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("ATM Web API - ATM operations login")
				.setSubject(logged.getId().toString())
				.setIssuedAt(now)
				.setExpiration(expDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	@Override
	public boolean isValid(String token) {
		try {
			Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getUserId(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token)
			.getBody();
		
		return Long.parseLong(claims.getSubject());
	}
	
}
