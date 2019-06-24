package com.arm.atm.resources.withdraw;

public class Ten extends Note {
	
	public Ten() {
		setValue(10);
	}

	@Override
	public void calculate(Integer amount) {
		setQuantity( amount / this.value );
	}

	@Override
	public boolean isValid() {
		return this.quantity > 0;
	}
	
	@Override
	public String toString() {
		return "Banknotes of Ten: " + this.quantity;
	}

}
