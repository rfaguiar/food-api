package com.food.domain.exception;

import java.text.MessageFormat;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(Long id) {
        this(MessageFormat.format("Não existe cadastro de cidade com código {0}", id));
    }
}
