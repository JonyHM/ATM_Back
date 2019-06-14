package com.arm.atm.component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.arm.atm.dto.NotesDTO;

/**
 * Class designed to calculate the number of each note that will be returned to the user 
 * @author jonathasmoraes
 *
 */
@Component
public class WithdrawNotes {

	private NotesDTO notes;
	
	private Map<String, BigDecimal> notesMap;
	
	public WithdrawNotes() {
		this.notesMap = new HashMap<>();
		this.notesMap = getMapa();
		this.notes = new NotesDTO();
	}
	
	/**
	 * Method for calculate how many notes from each value will be withdrawn on our ATM
	 * @param amount for withdrawal
	 * @return a NotesDTO object with the number of all notes that will be withdrawn
	 */
	public NotesDTO withdrawal(BigDecimal amount) {		
		for (Entry<String, BigDecimal> note : notesMap.entrySet()) {
			amount = setNoteNumber(amount, note);
		}
		
		return notes;
	}
	
	/**
	 * Gets the total amount in order to calculate how much notes he is going to set up to the NotesDTO object.
	 * 
	 * Sets the notes number under the notes.setNotes() method and the remainder amount value is returned
	 * @param amount for withdrawal
	 * @param note map with note name as @key and note value as @value
	 * @return remainder amount, after calculating the higher note value
	 */
	private BigDecimal setNoteNumber (BigDecimal amount, Entry<String, BigDecimal> note) {
		if(amount.compareTo(note.getValue()) >= 0) {
			notes.setNotes(note.getKey() , amount.divide(note.getValue(), 2, RoundingMode.DOWN));
			amount = amount.remainder(note.getValue());
		}
		
		return amount;
	}
	
	/**
	 * Populate the map object with note name as key and note value as value; 
	 * @return a new HashMap with all the notes names and values in order to calculate their values
	 */
	private HashMap<String, BigDecimal> getMapa() {
		HashMap<String, BigDecimal> mapa = new HashMap<>();
		
		mapa.put("hundred", new BigDecimal(100));
		mapa.put("fifty", new BigDecimal(50));
		mapa.put("twenty", new BigDecimal(20));
		mapa.put("ten", new BigDecimal(10));
		
		return mapa;
	}
}
