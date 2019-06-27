package com.arm.atm.resource.withdraw;

public class Hundred extends Note {
	
	public Hundred(Note nextNote) {
		setNextNote(nextNote);
		setValue(100);
	}

	@Override
	public void calculate(Integer amount) {
		setQuantity(amount / this.value);
		
		this.nextNote.calculate(amount % this.value);
	}

	@Override
	public boolean isValid() {
		return this.quantity > 0;
	}
	
	@Override
	public String toString() {
		return "Banknotes of Hundred: " + this.quantity;
	}

}
