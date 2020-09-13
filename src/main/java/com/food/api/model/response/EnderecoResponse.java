package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Endereco;

import java.util.Optional;

public record EnderecoResponse(@JsonProperty("cep") String cep,
                               @JsonProperty("logradouro") String logradouro,
                               @JsonProperty("numero") String numero,
                               @JsonProperty("complemento") String complemento,
                               @JsonProperty("bairro") String bairro,
                               @JsonProperty("cidade") CidadeResumoResponse cidade) {

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
