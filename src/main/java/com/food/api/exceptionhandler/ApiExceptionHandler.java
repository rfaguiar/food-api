package com.food.api.exceptionhandler;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handlerEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handlerEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handlerNegocioException(NegocioException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (null == body) {
            body = new Problema(LocalDateTime.now(), status.getReasonPhrase());
        } else if (body instanceof String) {
            body = new Problema(LocalDateTime.now(), (String) body);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
