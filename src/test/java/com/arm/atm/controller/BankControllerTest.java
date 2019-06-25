package com.arm.atm.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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

import com.arm.atm.configuration.UserServiceImplTestConfiguration;
import com.arm.atm.dto.BankDTO;
import com.arm.atm.entity.Bank;
import com.arm.atm.service.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
/*import configurations for services*/
@Import(UserServiceImplTestConfiguration.class)
public class BankControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	
	@Autowired
	private BankService bankService;
	
	private Bank bank, bank2;
	
	/*Mock some new data in order to assert the values for testing*/
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		Bank.BankBuilder bankBuilder = Bank.builder();
		bank = bankBuilder
				.name("Bamerindus")
				.build();
		bankService.create(bank);
		
		bank2 = bankBuilder
				.name("CitiBank")
				.build();
		bankService.create(bank2);
	}
    
	@Test
	@Transactional
    public void givenBanks_whenGetBanks_thenStatus200_andContentMatchesTheGivenOne() throws Exception {
		/*Perform a get request on the controller, looking for all the banks and get its response*/
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/bank")
					.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn().getResponse();
		
		/*Asserting the status code, content type and if the content value contains the mocked data*/
		assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());	
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("Bamerindus");
		assertThat(getResponse.getContentAsString()).contains("CitiBank");
    }
	
	@Test
	@Transactional
	public void givenBanks_whenGetOneBank_thenStatus200_andGivenOneExists() throws Exception {
		/*Perform a get request on the controller, looking for the mocked bank and get its response*/
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/bank/{id}", bank.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn().getResponse();
		
		/*Asserting the content type and if the content value contains the mocked data, either with the explicit name and the one took from the new bank*/
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("Bamerindus");
		assertThat(getResponse.getContentAsString()).contains(bank.getName());
		
		getResponse = mockMvc.perform(
				get("/bank/{id}", bank2.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("CitiBank");
		assertThat(getResponse.getContentAsString()).contains(bank2.getName());
	}
	
	@Test
	@Transactional
	public void whenPutUser_thenStatus201_andDataHasBeenModified() throws Exception {
		/*Getting the bank from the database*/
		Bank existingBank = (Bank)bankService.getBank("Bamerindus").get();
		existingBank = existingBank.toBuilder().name("Bamerindus HSBC").build();
		
		/*Parsing the bank in order to update it*/
		BankDTO changedBank = BankDTO.parse(existingBank);

		/*Parsing the bank form to string, in order to post it*/
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String content = ow.writeValueAsString(changedBank);
		
		/*Updating the bank*/
	    MockHttpServletResponse putResponse = mockMvc.perform(
	    		put("/bank/{id}", existingBank.getId())
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(content))
					.andReturn().getResponse();
		
		assertThat(putResponse.getStatus()).isEqualTo(201);
		
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/bank/{id}", existingBank.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		assertThat(getResponse.getContentAsString()).contains("Bamerindus HSBC");
		assertThat(getResponse.getContentAsString()).contains(changedBank.getName());
	}
	
	@Test
	@Transactional
	public void givenUsers_whenDeleteUser_thenStatus204_andDeletedOneNoLongerExists() throws Exception {
		/*Perform a delete request, deleting the new bank*/
		MockHttpServletResponse deleteResponse = mockMvc.perform(
				delete("/bank/{id}", bank.getId()))
					.andReturn().getResponse();
		
		/*Asserting the status code*/
		assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
		
		/*Performing a new get request and asserting that the bank was deleted*/
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/bank")
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		assertThat(getResponse.getContentAsString()).doesNotContain("Bamerindus HSBC");
	}
	
	@Test
	@Transactional
	public void whenPostAccount_thenStatus201_andGetValueMatchesTheNewOne() throws Exception {
		/*Creating a new BankDTO object for posting*/
		BankDTO newBank = new BankDTO("Caixa");
		
		/*Parsing the new bank to string, in order to post it*/
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String content = ow.writeValueAsString(newBank);
		
		MockHttpServletResponse postResponse = mockMvc.perform(
				post("/bank")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(content))					
					.andReturn().getResponse();
		
		/*Asserting the response status code*/
		assertThat(postResponse.getStatus()).isEqualTo(201);
		
		/*Getting the bank by its name in order to get its ID*/
		Bank bankFound = (Bank)bankService.getBank("Caixa").get();
		
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/bank/{id}", bankFound.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("Caixa");
		assertThat(getResponse.getContentAsString()).contains(newBank.getName());
	}
	
}
