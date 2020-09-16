package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Endereco;
import com.food.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
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
                Optional.ofNullable(restaurante.endereco()).map(EnderecoResponse::new).orElse(null),
                new CozinhaResponse(restaurante.cozinha()));
    }
}