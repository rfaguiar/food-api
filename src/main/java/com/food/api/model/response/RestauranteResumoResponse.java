package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Restaurante;

public record RestauranteResumoResponse(@JsonProperty("id") Long id,
                                        @JsonProperty("nome") String nome) {

    public RestauranteResumoResponse(Restaurante restaurante) {
        this(restaurante.id(), restaurante.nome());
    }
}
