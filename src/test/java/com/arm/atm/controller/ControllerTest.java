package com.arm.atm.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.arm.atm.AtmBackApplication;
import com.arm.atm.configuration.AccountServiceImplTestConfiguration;
import com.arm.atm.configuration.BankServiceImplTestConfiguration;
import com.arm.atm.configuration.UserServiceImplTestConfiguration;
import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;
import com.arm.atm.entity.User;
import com.arm.atm.service.AccountService;
import com.arm.atm.service.BankService;
import com.arm.atm.service.UserService;

@Profile("test")
@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK, 
		classes = AtmBackApplication.class
)
@Import({
	AccountServiceImplTestConfiguration.class, 
	UserServiceImplTestConfiguration.class, 
	BankServiceImplTestConfiguration.class
})
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
    private MockMvc mvc;
	 
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
    public void givenAccounts_whenGetAccounts_thenStatus200() throws Exception {
    	
    	mvc.perform(get("/account")
    		.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
    			.andExpect((ResultMatcher) jsonPath("$[0].owner", is("Jonathas")));
    }
	
}
