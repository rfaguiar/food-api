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

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handlerEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        Problem problem = createProblemaBuilder(status,
                ProblemType.ENTIDADE_EM_USO,
                e.getMessage());
        return handleExceptionInternal(e, problem,
                new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handlerEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problem problem = createProblemaBuilder(status,
                ProblemType.ENTIDADE_NAO_ENCONTRADA,
                e.getMessage());
        return handleExceptionInternal(e, problem,
                new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handlerNegocioException(NegocioException e, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Problem problem = createProblemaBuilder(status,
                ProblemType.ERRO_NEGOCIO,
                e.getMessage());
        return handleExceptionInternal(e, problem,
                new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (null == body) {
            body = new Problem(status.value(), null, status.getReasonPhrase(), null);
        } else if (body instanceof String) {
            body = new Problem(status.value(),null, (String) body, null);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem createProblemaBuilder(HttpStatus status, ProblemType type, String detail) {
        return new Problem(status.value(), type.getUri(), type.getTitle(), detail);
    }
}
