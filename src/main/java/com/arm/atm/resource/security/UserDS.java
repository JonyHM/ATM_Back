package com.arm.atm.resource.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.arm.atm.entity.User;

public class UserDS implements UserDetails {
	
	private static final long serialVersionUID = 578879477375415474L;

	private String email;
	private String password;
	private Profile profile;
	
	public UserDS(User user) {
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.profile = user.getProfile();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(profile);
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
