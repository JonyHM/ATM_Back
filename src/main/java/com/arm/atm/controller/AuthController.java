package com.arm.atm.controller;

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
import com.arm.atm.form.LoginForm;
import com.arm.atm.service.auth.LoginServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private LoginServiceImpl loginService;
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken login = form.parse();
		
		try {
			Authentication authentication = authManager.authenticate(login);
			String token = loginService.generateToken(authentication);
			
			return ResponseEntity.ok(new LoginDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
