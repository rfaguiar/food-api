package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Restaurante;
import com.food.service.validation.CadastroRestauranteGroup;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record RestauranteDto(@JsonProperty("id")
                             Long id,
                             @JsonProperty("nome")
                             @NotBlank(groups = CadastroRestauranteGroup.class)
                             String nome,
                             @JsonProperty("taxaFrete")
                             @PositiveOrZero(groups = CadastroRestauranteGroup.class)
                             BigDecimal taxaFrete,
                             @JsonProperty("cozinha")
                             @Valid
                             @NotNull(groups = CadastroRestauranteGroup.class)
                             CozinhaDto cozinha,
                             @JsonProperty("formasPagamento")
                             List<FormaPagamentoDto> formasPagamento) {
    
    public RestauranteDto (Restaurante restaurante) {
        this(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(),
                new CozinhaDto(restaurante.cozinha()),
                Optional.ofNullable(restaurante.formasPagamento())
                        .orElse(new HashSet<>())
                        .stream()
                        .map(FormaPagamentoDto::new)
                        .collect(Collectors.toList())
        );
    }
}
