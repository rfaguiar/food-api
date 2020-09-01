package com.food.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionController {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Void> dataIntegrityViolationHandler(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
