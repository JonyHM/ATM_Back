package com.arm.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arm.atm.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Account findByOwnerName(String ownerName);
	Account findByNumber(Long number);
}
