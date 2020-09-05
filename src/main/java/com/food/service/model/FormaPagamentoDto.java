package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.FormaPagamento;

public record FormaPagamentoDto (@JsonProperty("id") Long id,
                                @JsonProperty("descricao") String descricao) {

    public FormaPagamentoDto(FormaPagamento formaPagamento) {
        this(formaPagamento.id(), formaPagamento.descricao());
    }
}
