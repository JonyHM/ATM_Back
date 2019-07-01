package com.arm.atm.service.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arm.atm.repository.ProfileRepository;
import com.arm.atm.resource.security.AuthorizationLevel;
import com.arm.atm.resource.security.Profile;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository repository;
	
	@Override
	public Profile getByName(AuthorizationLevel name) {
		return repository.findByName(name);
	}

}
