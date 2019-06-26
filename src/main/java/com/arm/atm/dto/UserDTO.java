package com.arm.atm.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.arm.atm.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

	private String name;
	private String email;
	
	public UserDTO(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
	}

	public static List<UserDTO> parse(List<User> users) {
		return users.stream().map(UserDTO::new).collect(Collectors.toList());
	}

	public static UserDTO parse(User user) {
		return new UserDTO(user);
	}
}
