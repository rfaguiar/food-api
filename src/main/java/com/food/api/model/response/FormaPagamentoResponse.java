package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.FormaPagamento;
import io.swagger.annotations.ApiModelProperty;

public record FormaPagamentoResponse(@ApiModelProperty(example = "1")
                                     @JsonProperty("id")
                                     Long id,
                                     @ApiModelProperty(example = "Cartão de crédito")
                                     @JsonProperty("descricao")
                                     String descricao) {

    public FormaPagamentoResponse(FormaPagamento formaPagamento) {
        this(formaPagamento.id(), formaPagamento.descricao());
    }
}
