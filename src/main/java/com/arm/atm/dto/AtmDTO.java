package com.arm.atm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AtmDTO {
	
	private String bankName;
	private Long accountNumber;
	private Integer value;
	private String password;
	
//	public void AtmDTO() {
//		
//	}
}
