package com.arm.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arm.atm.resource.security.AuthorizationLevel;
import com.arm.atm.resource.security.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Profile findByName(AuthorizationLevel levelName);
}
