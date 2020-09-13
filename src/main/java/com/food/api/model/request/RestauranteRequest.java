package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record RestauranteRequest(@JsonProperty("nome")
                                 @NotBlank
                                 String nome,
                                 @JsonProperty("taxaFrete")
                                 @PositiveOrZero
                                 BigDecimal taxaFrete,
                                 @JsonProperty("cozinha")
                                 @Valid
                                 @NotNull
                                 CozinhaIdRequest cozinha,
                                 @JsonProperty("endereco")
                                 @Valid
                                 @NotNull
                                 EnderecoRequest endereco) {
}
