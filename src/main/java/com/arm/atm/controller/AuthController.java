package com.arm.atm.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arm.atm.dto.LoginDTO;
import com.arm.atm.form.ATMForm;
import com.arm.atm.form.LoginForm;
import com.arm.atm.service.auth.ATMServiceImpl;
import com.arm.atm.service.auth.LoginServiceImpl;

/**
 * Controller for user and account authorization
 * 
 * @author jonathasmoraes
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private LoginServiceImpl loginService;
	
	@Autowired
	private ATMServiceImpl atmService;
	
	/**
	 * Endpoint for access to the API
	 * 
	 * @param form
	 * @return a token for an authorized user
	 */
	@PostMapping
	@Transactional
	public ResponseEntity<?> userLogin(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken login = form.parse();
		
		try {
			Authentication authentication = authManager.authenticate(login);
			String token = loginService.generateToken(authentication);
			
			return ResponseEntity.ok(new LoginDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	/**
	 * Endpoint for ATM operations
	 * @param form
	 * @return a token for ATM operations, once the provided credentials are valid.
	 */
	@PostMapping("/atm")
	@Transactional
	public ResponseEntity<?> atmLogin(@RequestBody @Valid ATMForm form) {
		UsernamePasswordAuthenticationToken login = form.parse();
		
		try {
			Authentication authentication = authManager.authenticate(login);
			String token = atmService.generateToken(authentication);
			
			return ResponseEntity.ok(new LoginDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
