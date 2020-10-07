package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Endereco;
import io.swagger.annotations.ApiModelProperty;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EnderecoResponse(@ApiModelProperty(example = "38400-000")
                               @JsonProperty("cep") String cep,
                               @ApiModelProperty(example = "Rua Floriano Peixoto")
                               @JsonProperty("logradouro") String logradouro,
                               @ApiModelProperty(example = "\"1500\"")
                               @JsonProperty("numero") String numero,
                               @ApiModelProperty(example = "Apto 901")
                               @JsonProperty("complemento") String complemento,
                               @ApiModelProperty(example = "Centro")
                               @JsonProperty("bairro") String bairro,
                               @JsonProperty("cidade") CidadeResumoResponse cidade) {
    public EnderecoResponse(Endereco endereco) {
        this(endereco.cep(), endereco.logradouro(), endereco.numero(), endereco.complemento(), endereco.bairro(),
                new CidadeResumoResponse(endereco.cidade()));
    }

    public EnderecoResponse(Optional<Endereco> enderecoOpt) {
        this(enderecoOpt.map(Endereco::cep).orElse(null),
                enderecoOpt.map(Endereco::logradouro).orElse(null),
                enderecoOpt.map(Endereco::numero).orElse(null),
                enderecoOpt.map(Endereco::complemento).orElse(null),
                enderecoOpt.map(Endereco::bairro).orElse(null),
                enderecoOpt.map(e -> new CidadeResumoResponse(Optional.ofNullable(e.cidade())))
                        .orElse(new CidadeResumoResponse(Optional.empty())));
    }
}
