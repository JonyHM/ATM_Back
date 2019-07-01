package com.arm.atm.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arm.atm.component.UserParser;
import com.arm.atm.dto.UserDTO;
import com.arm.atm.entity.User;
import com.arm.atm.form.UserForm;
import com.arm.atm.service.data.UserServiceImpl;

@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private UserParser userparser;
	
	@PostMapping()
	@Transactional
	@CacheEvict(value = {"userList", "singleUser"}, allEntries = true)
	public ResponseEntity<?> createUser(@RequestBody @Valid UserForm user) {
		User newUser = userparser.parse(user);		
		userService.create(newUser);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();

		return ResponseEntity.created(location).body(new UserDTO(newUser));
	}
	
	@GetMapping()
	@Cacheable(value = "userList")
	@Transactional
	public ResponseEntity<List<UserDTO>> listUsers() {
		return ResponseEntity.ok(UserDTO.parse(userService.getAll()));
	}
	
	@GetMapping("/{id}")
	@Cacheable(value = "singleUser")
	@Transactional
	public ResponseEntity<?> findUser(@PathVariable Long id) {
		Object response = userService.getUser(id).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		return ResponseEntity.ok(UserDTO.parse((User) response));
	}
	
	@PutMapping("/{id}")
	@CacheEvict(value = {"userList", "singleUser"}, allEntries = true)
	@Transactional
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserForm user) {
		User newUser = userparser.parse(user);	
		Object response = userService.edit(id, newUser).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();

		return ResponseEntity.created(location).body(new UserDTO(newUser));	
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = {"userList", "singleUser"}, allEntries = true)
	@Transactional
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		Object response = userService.delete(id).get();
		
		if(response instanceof String) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		return ResponseEntity.noContent().build();
	}
}
