package com.food.domain.exception;

import org.springframework.validation.BindingResult;

public class ValidacaoException extends RuntimeException {

    private BindingResult bindingResult;

    public ValidacaoException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}
