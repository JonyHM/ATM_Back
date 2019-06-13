package com.arm.atm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class AccountDTO {
	
	private String bankName;
	private Long accountNumber;
	private Integer balance;
	private String password;
	private String owner;
	
//	public void AccountDTO() {
//		
//	}
}
