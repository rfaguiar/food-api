package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Usuario;

public record UsuarioResponse(@JsonProperty("id") Long id,
                              @JsonProperty("nome") String nome,
                              @JsonProperty("email") String email) {
    public UsuarioResponse(Usuario usuario) {
        this(usuario.id(), usuario.nome(), usuario.email());
    }
}
