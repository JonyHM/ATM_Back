package com.arm.atm.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotesDTO {

	private int hundred;
	private int fifty;
	private int twenty;
	private int ten;
	
	/**
	 * Verifies if the given key string is related of one of the available notes at this ATM
	 * @param note key string
	 * @param noteValue number of notes to be withdrawn
	 */
	public void setNotes(String note, BigDecimal noteValue) {
		if(note == "hundred") {
			setHundred(noteValue.intValueExact());
		} else if(note == "fifty") {
			setFifty(noteValue.intValueExact());
		} else if(note == "twenty") {
			setTwenty(noteValue.intValueExact());
		} else {
			setTen(noteValue.intValueExact());
		}		
	}
}
