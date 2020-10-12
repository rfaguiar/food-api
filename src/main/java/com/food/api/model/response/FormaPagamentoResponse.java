package com.food.api.model.response;

import com.food.domain.model.FormaPagamento;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "formasPagamento")
public class FormaPagamentoResponse extends RepresentationModel<FormaPagamentoResponse> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Cartão de crédito")
    private String descricao;

    public FormaPagamentoResponse(FormaPagamento formaPagamento) {
        this(formaPagamento.id(), formaPagamento.descricao());
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
