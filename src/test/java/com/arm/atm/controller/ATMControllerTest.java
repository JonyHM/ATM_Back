package com.arm.atm.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.arm.atm.configuration.AccountServiceImplTestConfiguration;
import com.arm.atm.configuration.BankServiceImplTestConfiguration;
import com.arm.atm.configuration.UserServiceImplTestConfiguration;
import com.arm.atm.dto.AtmDTO;
import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;
import com.arm.atm.entity.User;
import com.arm.atm.service.data.AccountService;
import com.arm.atm.service.data.BankService;
import com.arm.atm.service.data.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
/*import configurations for services*/
@Import({
	AccountServiceImplTestConfiguration.class, 
	UserServiceImplTestConfiguration.class, 
	BankServiceImplTestConfiguration.class
})
public class ATMControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BankService bankService;
	
	private User owner;
	private Bank bank;
	private Account account;
	
	/*Mock some new data in order to assert the values for testing*/
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		User.UserBuilder userBuilder = User.builder();
		owner = userBuilder
						.name("Jonathas")
						.password("abc123")
						.build();
		userService.create(owner);
		
		bank = (Bank)bankService.getBank("Santander").get();
		
		Account.AccountBuilder accountBuilder = Account.builder();
		account = accountBuilder
							.number(554321L)
							.owner(owner)
							.password("abc123")
							.balance(BigDecimal.valueOf(520))
							.bank(bank)
							.build();
		accountService.create(account);
	}
    
	@Test
	@Transactional
    public void givenAccounts_whenPostDeposit_thenStatus200_andBalanceMatchesTheGivenOne() throws Exception {
		AtmDTO deposit = new AtmDTO("Santander", 554321L, "abc123", BigDecimal.valueOf(580));
		
		/*Parsing the new deposit to string, in order to post it*/
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String content = ow.writeValueAsString(deposit);
		
		/*Perform a post request to the controller with the new deposit*/
		MockHttpServletResponse postResponse = mockMvc.perform(
				post("/atm/deposit")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(content))
				.andReturn().getResponse();
		
		/*Asserting the status code, content type and if the content value contains the mocked data*/
		assertThat(postResponse.getStatus()).isEqualTo(HttpStatus.OK.value());	
		
		Account existingAccount = (Account)accountService.getAccountByNumber(554321L).get();
		
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/atm/balance/{id}", account.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn().getResponse();
		
		assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("1100");
		assertThat(getResponse.getContentAsString()).contains(existingAccount.getBalance().toString());
    }
	
	@Test
	@Transactional
	public void givenAccounts_whenPostWithdraw_thenStatus200_andBalanceMatchesTheGivenOne() throws Exception {
		AtmDTO withdraw = new AtmDTO("Santander", 554321L, "abc123", BigDecimal.valueOf(520));
		
		/*Parsing the new deposit to string, in order to post it*/
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String content = ow.writeValueAsString(withdraw);
	    
		/*Perform a post request to the controller with the new deposit*/
		MockHttpServletResponse postResponse = mockMvc.perform(
				post("/atm/withdraw")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(content))
				.andReturn().getResponse();
		
		/*Asserting the status code, content type and if the content value contains the mocked data*/
		assertThat(postResponse.getStatus()).isEqualTo(HttpStatus.OK.value());	
		
		Account existingAccount = (Account)accountService.getAccountByNumber(554321L).get();
		
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/atm/balance/{id}", account.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn().getResponse();
		
		assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("0.00");
		assertThat(getResponse.getContentAsString()).contains(existingAccount.getBalance().toString());
	}
	
}
