package com.food.api.v1.model.response;

import com.food.domain.model.FormaPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "formasPagamento")
public class FormaPagamentoResponse extends RepresentationModel<FormaPagamentoResponse> {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Cartão de crédito")
    private String descricao;

    public FormaPagamentoResponse(FormaPagamento formaPagamento) {
        this(formaPagamento.getId(), formaPagamento.getDescricao());
    }

    public FormaPagamentoResponse(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
