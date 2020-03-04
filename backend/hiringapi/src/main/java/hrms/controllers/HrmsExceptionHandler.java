package hrms.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hrms.dao.InvalidDataException;

@RestControllerAdvice
public class HrmsExceptionHandler {
	@ExceptionHandler(value = { org.springframework.dao.EmptyResultDataAccessException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleEmptyResult(Exception e) {
		return "No data found";
	}
	
	@ExceptionHandler(value = {NullPointerException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleEmbarrasingNullPointer(Exception e) {
		return "This is embarrasing, but we had a null pointer";
	}
	
	@ExceptionHandler(value = {InvalidDataException.class})
	@ResponseStatus(HttpStatus.CONFLICT)
	public String handleInvalidData(InvalidDataException e) {
		return e.getMessage();
	}
}
