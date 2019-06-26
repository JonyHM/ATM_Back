package com.arm.atm.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
import com.arm.atm.entity.Account;
import com.arm.atm.entity.Bank;
import com.arm.atm.entity.User;
import com.arm.atm.form.AccountForm;
import com.arm.atm.form.UserForm;
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
public class AccountControllerTest {

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
						.email("jonathas.moraes@hotmail.com")
						.password("$2a$10$3i3ljybXRcal0FW0HTrkyOBh.XoWbu2f7PXuVxVw7fBPnXKGmhRJW")
						.build();
		userService.create(owner);
		
		bank = (Bank)bankService.getBank("Santander").get();
		
		Account.AccountBuilder accountBuilder = Account.builder();
		account = accountBuilder
							.number(554321L)
							.owner(owner)
							.password("$2a$10$3i3ljybXRcal0FW0HTrkyOBh.XoWbu2f7PXuVxVw7fBPnXKGmhRJW")
							.balance(BigDecimal.valueOf(520))
							.bank(bank)
							.build();
		accountService.create(account);
	}
    
	@Test
	@Transactional
    public void givenAccounts_whenGetAccounts_thenStatus200_andContentMatchesTheGivenOne() throws Exception {
		/*Perform a get request on the controller, looking for all the accounts and get its response*/
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/account")
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		/*Asserting the status code, content type and if the content value contains the mocked data*/
		assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());	
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("Jonathas");
    }
	
	@Test
	@Transactional
	public void givenAccounts_whenGetOneAccount_thenStatus200_andGivenOneExists() throws Exception {
		/*Perform a get request on the controller, looking for the mocked account and get its response*/
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/account/{id}", account.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		/*Asserting the content type and if the content value contains the mocked data, either with the explicit name and the one took from the new account owner*/
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("Jonathas");
		assertThat(getResponse.getContentAsString()).contains(account.getOwner().getName());
	}
	
	@Test
	@Transactional
	public void whenPutAccount_thenStatus201_andDataHasBeenModified() throws Exception {
		/*Getting the account and other owner from the database*/
		Account accountToUpdate = (Account)accountService.getAccount(account.getId()).get();
		User otherOwner = (User)userService.getUser("Jonathas").get();
		otherOwner = otherOwner.toBuilder().name("Jonathas Henrique").build();
		
		/*Parsing the user in order to update it*/
		UserForm changedUser = UserForm.parse(otherOwner);

		/*Parsing the user form to string, in order to post it*/
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String content = ow.writeValueAsString(changedUser);
		
		/*Updating the user*/
		mockMvc.perform(put("/user/{id}", otherOwner.getId())
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(content))
					.andReturn().getResponse();
		
		
		/*Building the existing objects with new values*/
		accountToUpdate = accountToUpdate.toBuilder().owner(otherOwner).build();

		/*Parsing the account to a form object*/
		AccountForm updatedForm = AccountForm.parse(accountToUpdate);
		
		/*Parsing the updated form to string, in order to post it*/
	    content = ow.writeValueAsString(updatedForm);
		
		MockHttpServletResponse putResponse = mockMvc.perform(
				put("/account/{id}", accountToUpdate.getId())
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(content))
					.andReturn().getResponse();
		
		assertThat(putResponse.getStatus()).isEqualTo(201);
		
		Account updated = (Account)accountService.getAccount(accountToUpdate.getId()).get();
		
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/account/{id}", updated.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		assertThat(getResponse.getContentAsString()).contains("Jonathas Henrique");
		assertThat(getResponse.getContentAsString()).contains(updatedForm.getOwner());
	}
	
	@Test
	@Transactional
	public void givenAccounts_whenDeleteAccount_thenStatus204_andDeletedOneNoLongerExists() throws Exception {
		/*Perform a delete request, deleting the new account*/
		MockHttpServletResponse deleteResponse = mockMvc.perform(
				delete("/account/{id}", account.getId()))
					.andReturn().getResponse();
		
		/*Asserting the status code*/
		assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
		
		/*Performing a new get request and asserting that the account was deleted*/
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/account")
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		assertThat(getResponse.getContentAsString()).doesNotContain("Jonathas");
	}
	
	@Test
	@Transactional
	public void whenPostAccount_thenStatus201_andGetValueMatchesTheNewOne() throws Exception {
		/*Creating a new AccountForm object for posting*/
		AccountForm newAccount = new AccountForm("Santander", 523417L, "$2a$10$Wfzshe5u9G4J4ziQbN7HFeOikxxk2jDgIWeYOkfD14E7kBXSRjYpS", "Jose");
		
		/*Parsing the new account to string, in order to post it*/
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String content = ow.writeValueAsString(newAccount);
		
		MockHttpServletResponse postResponse = mockMvc.perform(
				post("/account")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(content))					
					.andReturn().getResponse();
		
		/*Asserting the response status code*/
		assertThat(postResponse.getStatus()).isEqualTo(201);
		
		/*Getting the account by its number in order to get its ID*/
		Account accountFound = (Account)accountService.getAccountByNumber(474219L).get();
		
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/account/{id}", accountFound.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("Jose");
		assertThat(getResponse.getContentAsString()).contains(newAccount.getOwner());
	}
	
}
