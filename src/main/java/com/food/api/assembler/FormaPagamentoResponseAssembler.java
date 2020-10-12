package com.food.api.assembler;

import com.food.api.controller.FormaPagamentoController;
import com.food.api.model.response.FormaPagamentoResponse;
import com.food.domain.model.FormaPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoResponseAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public FormaPagamentoResponseAssembler(FoodLinks foodLinks) {
        super(FormaPagamentoController.class, FormaPagamentoResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public FormaPagamentoResponse toModel(FormaPagamento formaPagamento) {
        return new FormaPagamentoResponse(formaPagamento)
                .add(foodLinks.linkToFormaPagamento(formaPagamento.id()))
                .add(foodLinks.linkToFormasPagamento("formasPagamento"));
    }

    @Override
    public CollectionModel<FormaPagamentoResponse> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities)
                .add(foodLinks.linkToFormasPagamento());
    }
}
