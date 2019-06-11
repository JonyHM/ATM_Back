package com.arm.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arm.atm.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Account findByOwner(String owner);

//	List<Account> findByNumberAndPassword(Long number, String password);
//
//	@Query("SELECT a FROM Account a WHERE a.number = :number AND a.password = :password AND a.bank.name = :bank ")
//	Account find(@Param("bank") String bank, @Param("number") Long number, @Param("password")String password);
//	
//	@Query("SELECT a FROM Account a WHERE a.number = :number AND a.bank.id = :bankId ")
//	Account find(@Param("bankId") Long bank, @Param("number") Long number);
//
//	Account save(Account newAccount);
}
