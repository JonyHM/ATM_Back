package com.arm.atm.resources.withdraw;

public class Fifty extends Note {
	
	public Fifty(Note nextNote) {
		setNextNote(nextNote);
		setValue(50);
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
		return "Banknotes of Fifty: " + this.quantity;
	}

}
