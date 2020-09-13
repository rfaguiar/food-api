package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EnderecoRequest(
        @JsonProperty("cep")
        @NotBlank
        String cep,
        @JsonProperty("logradouro")
        @NotBlank
        String logradouro,
        @JsonProperty("numero")
        @NotBlank
        String numero,
        @JsonProperty("complemento")
        String complemento,
        @JsonProperty("bairro")
        @NotBlank
        String bairro,
        @JsonProperty("cidade")
        @Valid
        @NotNull
        CidadeIdRequest cidade) {
}
