package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Estado;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Estado", description = "Representa um estado")
public record EstadoResponse(@ApiModelProperty(example = "1")
                             @JsonProperty("id")
                             Long id,
                             @ApiModelProperty(example = "São Paulo")
                             @JsonProperty("nome")
                             String nome) {
    public EstadoResponse(Estado estado) {
        this(estado.id(), estado.nome());
    }
}
