package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EnderecoRequest(
        @ApiModelProperty(example = "38400-000", required = true)
        @JsonProperty("cep")
        @NotBlank
        String cep,
        @ApiModelProperty(example = "Rua Floriano Peixoto", required = true)
        @JsonProperty("logradouro")
        @NotBlank
        String logradouro,
        @ApiModelProperty(example = "\"1500\"", required = true)
        @JsonProperty("numero")
        @NotBlank
        String numero,
        @ApiModelProperty(example = "Apto 901")
        @JsonProperty("complemento")
        String complemento,
        @ApiModelProperty(example = "Centro", required = true)
        @JsonProperty("bairro")
        @NotBlank
        String bairro,
        @JsonProperty("cidade")
        @Valid
        @NotNull
        CidadeIdRequest cidade) {
}
