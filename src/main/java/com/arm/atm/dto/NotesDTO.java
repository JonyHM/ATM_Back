package com.arm.atm.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotesDTO {

	private int hundred = 0;
	private int fifty = 0;
	private int twenty = 0;
	private int ten = 0;
	
	/**
	 * Verifies if the given key string is related of one of the available notes at this ATM
	 * @param note key string
	 * @param noteValue number of notes to be withdrawn
	 */
	public void setNotes(String note, BigDecimal noteValue) {
		if(note == "hundred") {
			setHundred(noteValue.setScale(2, RoundingMode.DOWN).intValue());
		} else if(note == "fifty") {
			setFifty(noteValue.setScale(2, RoundingMode.DOWN).intValue());
		} else if(note == "twenty") {
			setTwenty(noteValue.setScale(2, RoundingMode.DOWN).intValue());
		} else {
			setTen(noteValue.setScale(2, RoundingMode.DOWN).intValue());
		}		
	}
}
