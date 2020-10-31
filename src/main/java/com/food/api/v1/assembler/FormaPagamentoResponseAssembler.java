package com.food.api.v1.assembler;

import com.food.api.v1.controller.FormaPagamentoController;
import com.food.api.v1.model.response.FormaPagamentoResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.FormaPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoResponseAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public FormaPagamentoResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(FormaPagamentoController.class, FormaPagamentoResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public FormaPagamentoResponse toModel(FormaPagamento formaPagamento) {
        FormaPagamentoResponse formasPagamento = new FormaPagamentoResponse(formaPagamento)
                .add(foodLinks.linkToFormaPagamento(formaPagamento.getId()));

        if (foodSecurity.podeConsultarFormasPagamento()) {
            formasPagamento
                    .add(foodLinks.linkToFormasPagamento("formasPagamento"));
        }
        return formasPagamento;
    }

    @Override
    public CollectionModel<FormaPagamentoResponse> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        CollectionModel<FormaPagamentoResponse> formaPagamentoResponses = super.toCollectionModel(entities);
        if (foodSecurity.podeConsultarFormasPagamento()) {
            formaPagamentoResponses.add(foodLinks.linkToFormasPagamento());
        }
        return formaPagamentoResponses;
    }
}
