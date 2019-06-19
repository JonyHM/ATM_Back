package com.arm.atm.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arm.atm.component.UserParser;
import com.arm.atm.dto.UserDTO;
import com.arm.atm.entity.User;
import com.arm.atm.form.UserForm;
import com.arm.atm.service.UserServiceImpl;

@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private UserParser userparser;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createAccount(@RequestBody @Valid UserForm user) {
		User newUser = userparser.parse(user);		
		userService.create(newUser);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/user/{id}")
                .buildAndExpand(newUser.getId()).toUri();

		return ResponseEntity.created(location).body(new UserDTO(newUser));
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO> listAccounts() {
		return UserDTO.parse(userService.getAll());
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO findAccount(@PathVariable Long id) {
		return UserDTO.parse(userService.getUser(id));
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody @Valid UserForm user) {
		User newUser = userparser.parse(user);	
		userService.edit(id, newUser);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/user/{id}")
                .buildAndExpand(newUser.getId()).toUri();

		return ResponseEntity.created(location).body(new UserDTO(newUser));	
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
		userService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
