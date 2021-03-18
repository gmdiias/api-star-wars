package com.gmdiias.apistarwars.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gmdiias.apistarwars.exception.EntityNotFoundException;
import com.gmdiias.apistarwars.exception.ServiceException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AdviceController {

	private static final Logger LOG = LogManager.getLogger(AdviceController.class);

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleException(EntityNotFoundException ex) {
		LOG.error(ex.getMessage(), ex);
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<String> handleException(ServiceException ex) {
		LOG.error(ex.getMessage(), ex);
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<String> handleException(SQLIntegrityConstraintViolationException ex) {
		LOG.error(ex.getMessage(), ex);
		return ResponseEntity.badRequest().body("Já existe um Planeta cadastrado com esse nome.");
	}
}
