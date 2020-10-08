package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Permissao;
import io.swagger.annotations.ApiModelProperty;

public record PermissaoResponse(@ApiModelProperty(example = "1")
                                @JsonProperty("id") Long id,
                                @ApiModelProperty(example = "CONSULTAR_COZINHAS")
                                @JsonProperty("nome") String nome,
                                @ApiModelProperty(example = "Permite consultar cozinhas")
                                @JsonProperty("descricao") String descricao) {

    public PermissaoResponse(Permissao permissao) {
        this(permissao.id(), permissao.nome(), permissao.descricao());
    }
}
