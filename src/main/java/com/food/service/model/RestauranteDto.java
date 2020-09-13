package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.api.model.response.CozinhaResponse;
import com.food.domain.model.Restaurante;
import com.food.service.validation.CozinhaIdGroup;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;

public record RestauranteDto(@JsonProperty("id")
                             Long id,
                             @JsonProperty("nome")
                             @NotBlank
                             String nome,
                             @JsonProperty("taxaFrete")
                             @PositiveOrZero
                             BigDecimal taxaFrete,
                             @JsonProperty("ativo")
                             Boolean ativo,
                             @JsonProperty("cozinha")
                             @Valid
                             @ConvertGroup(from = Default.class, to = CozinhaIdGroup.class)
                             @NotNull
                             @JsonIgnoreProperties(value = "nome", allowGetters = true)
                             CozinhaResponse cozinha) {
    
    public RestauranteDto (Restaurante restaurante) {
        this(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(), restaurante.ativo(),
                new CozinhaResponse(restaurante.cozinha()));
    }
}
