package com.gmdiias.apistarwars.shared;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gmdiias.apistarwars.exception.EntidadeNaoEncontradaException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AdviceController {

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<String> handleException(EntidadeNaoEncontradaException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
