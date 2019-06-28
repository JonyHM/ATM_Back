package com.arm.atm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arm.atm.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Optional<Account> findByOwnerName(String ownerName);
	Optional<Account> findByNumber(Long number);
	
	@Query("SELECT a FROM Account a JOIN FETCH a.owner o JOIN FETCH a.bank b")
	List<Account> findAll();
}
