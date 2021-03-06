package com.arm.atm.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtmDTO {
	
	@NotNull @NotEmpty
	private String bankName;
	@NotNull @NotEmpty
	private Long accountNumber;
	@NotNull @NotEmpty
	private String password;
	@NotNull @NotEmpty
	private BigDecimal value;
}
