package com.arm.atm.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtmDTO {
	
	private String bankName;
	private Long accountNumber;
	private String password;
	private BigDecimal value;
}
