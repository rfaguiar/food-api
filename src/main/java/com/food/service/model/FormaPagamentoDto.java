package com.food.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.FormaPagamento;

import javax.validation.constraints.NotBlank;

public record FormaPagamentoDto (@JsonProperty("id")
                                 Long id,
                                 @JsonProperty("descricao")
                                 @NotBlank
                                 String descricao) {

    public FormaPagamentoDto(FormaPagamento formaPagamento) {
        this(formaPagamento.id(), formaPagamento.descricao());
    }
}
