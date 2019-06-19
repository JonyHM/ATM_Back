package com.arm.atm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arm.atm.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Optional<Account> findByOwnerName(String ownerName);
	Optional<Account> findByNumber(Long number);
}
