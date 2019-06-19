package com.arm.atm.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.arm.atm.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

	private String name;
	
	public UserDTO(User user) {
		this.name = user.getName();
	}

	public static List<UserDTO> parse(List<User> users) {
		return users.stream().map(UserDTO::new).collect(Collectors.toList());
	}

	public static UserDTO parse(User user) {
		return new UserDTO(user);
	}
}
