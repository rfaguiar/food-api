package com.food.domain.exception;

import java.text.MessageFormat;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {
    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradaException(Long id) {
        this(MessageFormat.format("Não existe um cadastro de forma de pagamento com código {0}", id));
    }
}
