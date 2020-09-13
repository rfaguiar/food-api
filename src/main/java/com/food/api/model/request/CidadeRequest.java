package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.service.model.EstadoDto;
import com.food.service.validation.EstadoIdGroup;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

public record CidadeRequest(@JsonProperty("nome")
                            @NotBlank
                            String nome,
                            @JsonProperty("estado")
                            @Valid
                            @ConvertGroup(from = Default.class, to = EstadoIdGroup.class)
                            @NotNull
                            @JsonIgnoreProperties("nome")
                            EstadoDto estado) {
}
