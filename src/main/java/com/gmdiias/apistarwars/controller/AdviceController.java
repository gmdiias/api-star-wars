package com.gmdiias.apistarwars.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gmdiias.apistarwars.exception.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AdviceController {

	private static final Logger LOG = LogManager.getLogger(AdviceController.class);

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleException(EntityNotFoundException ex) {
		LOG.error(ex.getMessage(), ex);
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
