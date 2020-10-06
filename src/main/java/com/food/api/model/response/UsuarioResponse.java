package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Usuario;
import io.swagger.annotations.ApiModelProperty;

public record UsuarioResponse(@ApiModelProperty(example = "1")
                              @JsonProperty("id") Long id,
                              @ApiModelProperty(example = "João da Silva")
                              @JsonProperty("nome") String nome,
                              @ApiModelProperty(example = "joao.ger@food.com.br")
                              @JsonProperty("email") String email) {
    public UsuarioResponse(Usuario usuario) {
        this(usuario.id(), usuario.nome(), usuario.email());
    }
}
