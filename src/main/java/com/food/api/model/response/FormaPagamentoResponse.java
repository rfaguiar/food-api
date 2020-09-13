package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.FormaPagamento;

public record FormaPagamentoResponse(@JsonProperty("id")
                                     Long id,
                                     @JsonProperty("descricao")
                                     String descricao) {

    public FormaPagamentoResponse(FormaPagamento formaPagamento) {
        this(formaPagamento.id(), formaPagamento.descricao());
    }
}
