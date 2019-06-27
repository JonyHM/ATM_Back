package com.arm.atm.resource.withdraw;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class Note {
	
	protected Integer value;
	protected Integer quantity;
	@NotNull protected Note nextNote;
	
	public abstract void calculate(Integer amount);
	public abstract boolean isValid();
}
