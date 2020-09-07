package com.food.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.FieldError;

public record Field(@JsonProperty("name") String name,
                    @JsonProperty("userMessage") String userMessage) {
    public Field(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
