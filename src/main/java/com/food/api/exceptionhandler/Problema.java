package com.food.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record Problema(@JsonProperty("dataHora") LocalDateTime dataHora,
                       @JsonProperty("mensagem") String mensagem) {
}
