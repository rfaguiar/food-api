package com.food.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.FieldError;

public record Field(@Schema(example = "preco")
                    @JsonProperty("name") String name,
                    @Schema(example = "O preço é obrigatório")
                    @JsonProperty("userMessage") String userMessage) {
    public Field(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
