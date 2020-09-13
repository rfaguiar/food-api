package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.Optional;

public record RestauranteResponse(@JsonProperty("id")
                                  Long id,
                                  @JsonProperty("nome")
                                  String nome,
                                  @JsonProperty("taxaFrete")
                                  BigDecimal taxaFrete,
                                  @JsonProperty("ativo")
                                  Boolean ativo,
                                  @JsonProperty("endereco")
                                  EnderecoResponse endereco,
                                  @JsonProperty("cozinha")
                                  CozinhaResponse cozinha) {
    
    public RestauranteResponse(Restaurante restaurante) {
        this(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(), restaurante.ativo(),
                new EnderecoResponse(Optional.ofNullable(restaurante.endereco())),
                new CozinhaResponse(restaurante.cozinha()));
    }
}
