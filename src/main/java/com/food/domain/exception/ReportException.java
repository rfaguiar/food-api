package com.food.domain.exception;

public class ReportException extends RuntimeException {

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportException(String message) {
        super(message);
    }
}
