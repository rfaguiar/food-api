package com.food.domain.exception;

import java.text.MessageFormat;

public class RestauranteNaoEncontradaException extends EntidadeNaoEncontradaException {
    public RestauranteNaoEncontradaException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradaException(Long id) {
        this(MessageFormat.format("Não existe um cadastro de restaurante com código {0}", id));
    }
}
