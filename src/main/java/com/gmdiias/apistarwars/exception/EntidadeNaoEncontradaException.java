package com.gmdiias.apistarwars.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException() {
		super("Entidade não encontrada");
	}
	
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

}
