package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Cidade;
import io.swagger.annotations.ApiModelProperty;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CidadeResumoResponse(@ApiModelProperty(example = "1")
                                   @JsonProperty("id")
                                   Long id,
                                   @ApiModelProperty(example = "Uberlândia")
                                   @JsonProperty("nome")
                                   String nome,
                                   @ApiModelProperty(example = "Minas Gerais")
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
