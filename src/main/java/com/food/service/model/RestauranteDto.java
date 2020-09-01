package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Restaurante;

import java.math.BigDecimal;

public record RestauranteDto(@JsonProperty("id") Long id,
                             @JsonProperty("nome") String nome,
                             @JsonProperty("taxaFrete") BigDecimal taxaFrete,
                             @JsonProperty("cozinha") CozinhaDto cozinha) {
    
    public RestauranteDto (Restaurante restaurante) {
        this(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(),
                new CozinhaDto(restaurante.cozinha()));
    }
}
