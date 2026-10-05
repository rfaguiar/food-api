package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoRequest(
        @Schema(example = "38400-000", required = true)
        @JsonProperty("cep")
        @NotBlank
        String cep,
        @Schema(example = "Rua Floriano Peixoto", required = true)
        @JsonProperty("logradouro")
        @NotBlank
        String logradouro,
        @Schema(example = "\"1500\"", required = true)
        @JsonProperty("numero")
        @NotBlank
        String numero,
        @Schema(example = "Apto 901")
        @JsonProperty("complemento")
        String complemento,
        @Schema(example = "Centro", required = true)
        @JsonProperty("bairro")
        @NotBlank
        String bairro,
        @JsonProperty("cidade")
        @Valid
        @NotNull
        CidadeIdRequest cidade) {
}
