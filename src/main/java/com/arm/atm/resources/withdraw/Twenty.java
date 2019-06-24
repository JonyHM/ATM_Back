package com.arm.atm.resources.withdraw;

public class Twenty extends Note {
	
	public Twenty(Note nextNote) {
		setNextNote(nextNote);
		setValue(20);
	}

	@Override
	public void calculate(Integer amount) {
		setQuantity( amount / this.value );
		
		this.nextNote.calculate(amount % this.value);
	}

	@Override
	public boolean isValid() {
		return this.quantity > 0;
	}
	
	@Override
	public String toString() {
		return "Banknotes of Twenty: " + this.quantity;
	}

}
