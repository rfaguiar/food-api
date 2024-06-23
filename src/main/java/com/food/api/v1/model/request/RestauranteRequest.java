package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record RestauranteRequest(@Schema(example = "Thai Gourmet", required = true)
                                 @JsonProperty("nome")
                                 @NotBlank
                                 String nome,
                                 @Schema(example = "12.00", required = true)
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
