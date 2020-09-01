package com.food.api;

import com.food.domain.exception.EntidadeEmUsoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionController {

    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Void> dataIntegrityViolationHandler(EntidadeEmUsoException e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
