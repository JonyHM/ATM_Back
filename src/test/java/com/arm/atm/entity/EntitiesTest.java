package com.arm.atm.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.arm.atm.configuration.AccountServiceImplTestConfiguration;
import com.arm.atm.configuration.BankServiceImplTestConfiguration;
import com.arm.atm.configuration.UserServiceImplTestConfiguration;
import com.arm.atm.service.AccountService;
import com.arm.atm.service.BankService;
import com.arm.atm.service.UserService;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({
		AccountServiceImplTestConfiguration.class, 
		UserServiceImplTestConfiguration.class, 
		BankServiceImplTestConfiguration.class
})
public class EntitiesTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BankService bankService;
	
	private User owner;
	private Bank bank;
	private Account account;
	
	@Before
	public void setUp() {
		User.UserBuilder userBuilder = User.builder();
		owner = userBuilder
						.name("Jonathas")
						.password("abc123")
						.build();
		
		Bank.BankBuilder bankBuilder = Bank.builder();
		bank = bankBuilder
						.name("santander")
						.build();
		
		Account.AccountBuilder accountBuilder = Account.builder();
		account = accountBuilder
							.number(554321L)
							.owner(owner)
							.password("abc123")
							.balance(BigDecimal.valueOf(520))
							.bank(bank)
							.build();
		
		entityManager.persist(owner);
		entityManager.persist(bank);
		entityManager.persist(account);
	}
	
	
	@Test
	public void whenGetAccountById_thenReturnAccount() {		
		Account found = (Account)accountService.getAccount(account.getId()).get();
		assertThat(found.getNumber()).isEqualTo(account.getNumber());
	}

	@Test
	public void whenGetAccountByOwnerName_thenReturnAccount() {
		Account found = (Account)accountService.getAccount(account.getOwner().getName()).get();
		assertThat(found.getNumber()).isEqualTo(account.getNumber());
	}
	
	@Test
	public void whenGetOwnerByName_thenReturnUser() {
		User found = (User)userService.getUser(owner.getName()).get();
		assertThat(found.getId()).isEqualTo(owner.getId());
	}
	
	@Test
	public void whenGetBankByName_thenReturnBank() {
		Bank found = (Bank)bankService.getBank(bank.getName()).get();
		assertThat(found.getId()).isEqualTo(bank.getId());
	}
}