package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public record FormaPagamentoRequest(@Schema(example = "Cartão de crédito", required = true)
                                    @JsonProperty("descricao")
                                    @NotBlank
                                    String descricao) {
}
