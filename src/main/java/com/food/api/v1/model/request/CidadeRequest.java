package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(defaultValue = "Cidade", description = "Representa uma cidade")
public record CidadeRequest(@Schema(example = "São Paulo", required = true)
                            @JsonProperty("nome")
                            @NotBlank
                            String nome,
                            @JsonProperty("estado")
                            @Valid
                            @NotNull
                            EstadoIdRequest estado) {
}
