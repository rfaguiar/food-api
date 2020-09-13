package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Cidade;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CidadeResumoResponse(@JsonProperty("id")
                                   Long id,
                                   @JsonProperty("nome")
                                   String nome,
                                   @JsonProperty("estado")
                                   String estado) {

    public CidadeResumoResponse(Cidade cidade) {
        this(cidade.id(), cidade.nome(), cidade.estado().nome());
    }

    public CidadeResumoResponse(Optional<Cidade> cidade) {
        this(cidade.map(Cidade::id).orElse(null),
                cidade.map(Cidade::nome).orElse(null),
                cidade.map(c -> c.estado().nome()).orElse(null));
    }
}
