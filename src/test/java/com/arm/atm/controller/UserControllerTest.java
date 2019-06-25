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
import com.arm.atm.entity.User;
import com.arm.atm.form.UserForm;
import com.arm.atm.service.UserService;
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
public class UserControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	private User owner, user;
	
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
		
		user = userBuilder
						.name("Lucas")
						.password("123abc")
						.build();
		userService.create(user);
	}
    
	@Test
	@Transactional
    public void givenUsers_whenGetUsers_thenStatus200_andContentMatchesTheGivenOne() throws Exception {
		/*Perform a get request on the controller, looking for all the users and get its response*/
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/user")
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		/*Asserting the status code, content type and if the content value contains the mocked data*/
		assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());	
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("Jonathas");
		assertThat(getResponse.getContentAsString()).contains("Lucas");
    }
	
	@Test
	@Transactional
	public void givenUsers_whenGetOneUser_thenStatus200_andGivenOneExists() throws Exception {
		/*Perform a get request on the controller, looking for the mocked user and get its response*/
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/user/{id}", owner.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		/*Asserting the content type and if the content value contains the mocked data, either with the explicit name and the one took from the new user*/
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("Jonathas");
		assertThat(getResponse.getContentAsString()).contains(owner.getName());
		
		getResponse = mockMvc.perform(
				get("/user/{id}", user.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("Lucas");
		assertThat(getResponse.getContentAsString()).contains(user.getName());
	}
	
	@Test
	@Transactional
	public void whenPutUser_thenStatus201_andDataHasBeenModified() throws Exception {
		/*Getting the owner from the database*/
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
	    MockHttpServletResponse putResponse = mockMvc.perform(
	    		put("/user/{id}", otherOwner.getId())
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(content))
					.andReturn().getResponse();
		
		assertThat(putResponse.getStatus()).isEqualTo(201);
		
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/user/{id}", otherOwner.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		assertThat(getResponse.getContentAsString()).contains("Jonathas Henrique");
		assertThat(getResponse.getContentAsString()).contains(changedUser.getName());
	}
	
	@Test
	@Transactional
	public void givenUsers_whenDeleteUser_thenStatus204_andDeletedOneNoLongerExists() throws Exception {
		/*Perform a delete request, deleting the new user*/
		MockHttpServletResponse deleteResponse = mockMvc.perform(
				delete("/user/{id}", owner.getId()))
					.andReturn().getResponse();
		
		/*Asserting the status code*/
		assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
		
		/*Performing a new get request and asserting that the user was deleted*/
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/user")
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		assertThat(getResponse.getContentAsString()).doesNotContain("Jonathas");
	}
	
	@Test
	@Transactional
	public void whenPostAccount_thenStatus201_andGetValueMatchesTheNewOne() throws Exception {
		/*Creating a new UserForm object for posting*/
		UserForm newUser = new UserForm("Lilian", "123456ab");
		
		/*Parsing the new user to string, in order to post it*/
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String content = ow.writeValueAsString(newUser);
		
		MockHttpServletResponse postResponse = mockMvc.perform(
				post("/user")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(content))					
					.andReturn().getResponse();
		
		/*Asserting the response status code*/
		assertThat(postResponse.getStatus()).isEqualTo(201);
		
		/*Getting the user by its name in order to get its ID*/
		User userFound = (User)userService.getUser("Lilian").get();
		
		MockHttpServletResponse getResponse = mockMvc.perform(
				get("/user/{id}", userFound.getId())
					.accept(MediaType.APPLICATION_JSON_UTF8))
					.andReturn().getResponse();
		
		assertThat(getResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
		assertThat(getResponse.getContentAsString()).contains("Lilian");
		assertThat(getResponse.getContentAsString()).contains(newUser.getName());
	}
	
}
