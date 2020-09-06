package com.food.api.exceptionhandler;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.exception.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Problema> handlerEntidadeEmUsoException(EntidadeEmUsoException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new Problema(LocalDateTime.now(), e.getMessage()));
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Problema> handlerEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Problema(LocalDateTime.now(), e.getMessage()));
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Problema> handlerNegocioException(NegocioException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Problema(LocalDateTime.now(), e.getMessage()));
    }

}
