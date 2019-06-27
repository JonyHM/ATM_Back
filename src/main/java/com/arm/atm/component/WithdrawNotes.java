package com.arm.atm.component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.arm.atm.resource.withdraw.Fifty;
import com.arm.atm.resource.withdraw.Hundred;
import com.arm.atm.resource.withdraw.Note;
import com.arm.atm.resource.withdraw.Ten;
import com.arm.atm.resource.withdraw.Twenty;

/**
 * Class designed to calculate the number of each note that will be returned to the user 
 * @author jonathasmoraes
 *
 */
@Component
public class WithdrawNotes {

	/**
	 * Method for calculate how many notes from each value will be withdrawn of the ATM. It calls a chain of banknotes that will calculate their number and the remaining amount
	 * @param amount for withdrawal
	 * @return a NoteDTO object with the number of all notes that will be withdrawn
	 */
	public List<Note> withdrawal(BigDecimal amount) {
		Note hundred, fifty, twenty, ten;
		
		ten = new Ten();
		twenty = new Twenty(ten);
		fifty = new Fifty(twenty);
		hundred = new Hundred(fifty);
		hundred.calculate(amount.intValue());
		
		return Arrays.asList(hundred, fifty, twenty, ten);
	}
	
}
