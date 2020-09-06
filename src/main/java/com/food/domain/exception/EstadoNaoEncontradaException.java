package com.food.domain.exception;

import java.text.MessageFormat;

public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {
    public EstadoNaoEncontradaException(String message) {
        super(message);
    }

    public EstadoNaoEncontradaException(Long id) {
        this(MessageFormat.format("Não existe um cadastro de estado com código {0}", id));
    }
}
