package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Cidade;
import com.food.service.validation.EstadoIdGroup;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

public record CidadeDto(@JsonProperty("id")
                        Long id,
                        @JsonProperty("nome")
                        @NotBlank
                        String nome,
                        @JsonProperty("estado")
                        @Valid
                        @ConvertGroup(from = Default.class, to = EstadoIdGroup.class)
                        @NotNull
                        @JsonIgnoreProperties("nome")
                        EstadoDto estado) {

    public CidadeDto(Cidade cidade) {
        this(cidade.id(), cidade.nome(), new EstadoDto(cidade.estado()));
    }
}
