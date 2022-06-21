package com.trainingapps.playerapp.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trainingapps.playerapp.exceptions.FavoritePlayerListEmptyException;
import com.trainingapps.playerapp.exceptions.PlayerAlreadyExistsException;
import com.trainingapps.playerapp.exceptions.PlayerNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class CentralizedExceptionHandler {
	
	
	 	@ResponseStatus(HttpStatus.NOT_FOUND)
	    @ExceptionHandler({PlayerNotFoundException.class,FavoritePlayerListEmptyException.class})
	    public String handlePlayerNotFound(Exception e) {
	        
	 		String msg = e.getMessage();
	        
	        return msg;
	    }
	    @ResponseStatus(HttpStatus.CONFLICT)
	    @ExceptionHandler(PlayerAlreadyExistsException.class)
	    public String handelPlayerAlreadyExists(Exception e) {
	        String msg = e.getMessage();
	        return msg;
	    }


}
