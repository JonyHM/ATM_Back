package com.arm.atm.filter.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.arm.atm.entity.User;
import com.arm.atm.service.auth.LoginServiceImpl;
import com.arm.atm.service.data.UserServiceImpl;

public class TokenAuthFilter extends OncePerRequestFilter {
	
	private LoginServiceImpl loginService;
	
	private UserServiceImpl userService;

	public TokenAuthFilter(LoginServiceImpl loginService, UserServiceImpl userService) {
		this.loginService = loginService;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = getToken(request);
		boolean isValid = loginService.isValid(token);

		if (isValid) {
			authenticateClient(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void authenticateClient(String token) {
		Long userId = loginService.getUserId(token);
		
		User user = (User)userService.getUser(userId).get();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				user, null, user.getProfiles());	
		
		SecurityContextHolder.getContext()
			.setAuthentication(authentication);
	}

	private String getToken(HttpServletRequest request) {
		String tokenHeader = request.getHeader("Authorization");
		
		if (tokenHeader == null || tokenHeader.isEmpty() || !tokenHeader.startsWith("Bearer")) {
			return null;
		}
		
		return tokenHeader.substring(7, tokenHeader.length());
	}

}
