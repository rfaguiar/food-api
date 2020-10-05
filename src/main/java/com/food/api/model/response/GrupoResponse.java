package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Grupo;
import io.swagger.annotations.ApiModelProperty;

public record GrupoResponse(@ApiModelProperty(example = "1")
                            @JsonProperty("id") Long id,
                            @ApiModelProperty(example = "Gerente")
                            @JsonProperty("nome") String nome) {

    public GrupoResponse(Grupo grupo) {
        this(grupo.id(), grupo.nome());
    }
}
