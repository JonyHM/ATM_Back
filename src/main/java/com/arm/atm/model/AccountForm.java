package com.arm.atm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountForm {
	private String bankName;
	private Long number;
	private Long id;
	private String password;
}
