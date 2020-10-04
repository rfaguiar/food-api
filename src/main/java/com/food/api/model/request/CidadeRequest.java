package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "Cidade", description = "Representa uma cidade")
public record CidadeRequest(@ApiModelProperty(example = "São Paulo")
                            @JsonProperty("nome")
                            @NotBlank
                            String nome,
                            @JsonProperty("estado")
                            @Valid
                            @NotNull
                            EstadoIdRequest estado) {
}
