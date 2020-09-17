package com.food.domain.exception;

import java.text.MessageFormat;

public class ProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {
    public ProdutoNaoEncontradaException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradaException(Long id) {
        this(MessageFormat.format("Não existe um cadastro de produto com código {0}", id));
    }
}
