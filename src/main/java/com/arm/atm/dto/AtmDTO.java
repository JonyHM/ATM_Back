package com.arm.atm.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AtmDTO {
	
	@NotEmpty
	private String bankName;
	@NotNull
	private Long accountNumber;
	@NotEmpty
	private String password;
	@NotNull
	private BigDecimal value;
}
