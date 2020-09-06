package com.food.domain.exception;

import java.text.MessageFormat;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(Long id) {
        this(MessageFormat.format("Não existe um cadastro de cozinha com código {0}", id));
    }
}
