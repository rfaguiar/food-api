package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record RestauranteDto(@JsonProperty("id") Long id,
                             @JsonProperty("nome") String nome,
                             @JsonProperty("taxaFrete") BigDecimal taxaFrete,
                             @JsonProperty("cozinha") CozinhaDto cozinha,
                             @JsonProperty("formasPagamento") List<FormaPagamentoDto> formasPagamento) {
    
    public RestauranteDto (Restaurante restaurante) {
        this(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(),
                new CozinhaDto(restaurante.cozinha()),
                restaurante.formasPagamento().stream().map(FormaPagamentoDto::new).collect(Collectors.toList()));
    }
}
