package com.arm.atm.configuration.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.arm.atm.dto.FieldErrorDTO;

/**
 * Handler for validation errors within the API
 * 
 * @author jonathasmoraes
 *
 */
@RestControllerAdvice
public class ValidationErrorHandler {
	
	@Autowired
	private MessageSource messageSource; 

	/**
	 * Handles thrown MethodArgumentNotValidException, decreasing the size of exception messages  
	 * 
	 * @param exception
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FieldErrorDTO> handle(MethodArgumentNotValidException exception) {
		List<FieldErrorDTO> dto = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(err -> {
			String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
			FieldErrorDTO error = new FieldErrorDTO(err.getField(), message);
			dto.add(error);
		});
		
		return dto;
	}
}
