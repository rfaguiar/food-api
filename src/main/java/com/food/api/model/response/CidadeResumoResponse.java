package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Cidade;

import java.util.Optional;

public record CidadeResumoResponse(@JsonProperty("id")
                                   Long id,
                                   @JsonProperty("nome")
                                   String nome,
                                   @JsonProperty("estado")
                                   String estado) {

    public CidadeResumoResponse(Optional<Cidade> cidade) {
        this(cidade.map(Cidade::id).orElse(null),
                cidade.map(Cidade::nome).orElse(null),
                cidade.map(c -> c.estado().nome()).orElse(null));
    }
}
