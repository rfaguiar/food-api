package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Restaurante;
import io.swagger.annotations.ApiModelProperty;

public record RestauranteResumoResponse(@ApiModelProperty(example = "1")
                                        @JsonProperty("id") Long id,
                                        @ApiModelProperty(example = "Thai Gourmet")
                                        @JsonProperty("nome") String nome) {

    public RestauranteResumoResponse(Restaurante restaurante) {
        this(restaurante.id(), restaurante.nome());
    }
}
