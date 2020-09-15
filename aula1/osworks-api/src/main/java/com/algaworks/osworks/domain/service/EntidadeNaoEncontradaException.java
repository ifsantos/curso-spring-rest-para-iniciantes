package com.algaworks.osworks.domain.service;

import com.algaworks.osworks.domain.exception.NegocioException;

public class EntidadeNaoEncontradaException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String message) {
		super(message);
	}
}
