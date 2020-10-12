package com.food.api.v1.assembler;

import com.food.api.v1.controller.CozinhaController;
import com.food.api.v1.model.response.CozinhaResponse;
import com.food.domain.model.Cozinha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaResponseAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public CozinhaResponseAssembler(FoodLinks foodLinks) {
        super(CozinhaController.class, CozinhaResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public CozinhaResponse toModel(Cozinha cozinha) {
        return new CozinhaResponse(cozinha)
                .add(foodLinks.linkToCozinha(cozinha.id()))
                .add(foodLinks.linkToCozinhas("cozinhas"));
    }
}
