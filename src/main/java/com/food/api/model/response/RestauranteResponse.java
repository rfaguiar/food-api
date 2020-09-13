package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Restaurante;

import java.math.BigDecimal;

public record RestauranteResponse(@JsonProperty("id")
                                  Long id,
                                  @JsonProperty("nome")
                                  String nome,
                                  @JsonProperty("taxaFrete")
                                  BigDecimal taxaFrete,
                                  @JsonProperty("cozinha")
                                  CozinhaResponse cozinha) {
    
    public RestauranteResponse(Restaurante restaurante) {
        this(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(),
                new CozinhaResponse(restaurante.cozinha()));
    }
}
