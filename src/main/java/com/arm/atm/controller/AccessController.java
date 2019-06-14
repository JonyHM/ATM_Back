package com.arm.atm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.arm.atm.dto.LoginDTO;

/**
 * Controller for Login and Logout requests
 * @author jonathasmoraes
 *
 */
public class AccessController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.OK)
	public void login(LoginDTO login) {
		
	}
}
