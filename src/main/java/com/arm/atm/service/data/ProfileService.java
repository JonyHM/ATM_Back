package com.arm.atm.service.data;

import com.arm.atm.resource.security.AuthorizationLevel;
import com.arm.atm.resource.security.Profile;

public interface ProfileService {

	Profile getByName(AuthorizationLevel name);
}
