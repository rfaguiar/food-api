package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Permissao;

public record PermissaoResponse(@JsonProperty("id") Long id,
                                @JsonProperty("nome") String nome,
                                @JsonProperty("descricao") String descricao) {

    public PermissaoResponse(Permissao permissao) {
        this(permissao.id(), permissao.nome(), permissao.descricao());
    }
}
