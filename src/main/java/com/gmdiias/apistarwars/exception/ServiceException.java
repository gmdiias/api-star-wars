package com.gmdiias.apistarwars.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException(String mensagem) {
		super(mensagem);
	}

}
