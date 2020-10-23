package com.food.api.v1.assembler;

import com.food.api.v1.controller.CozinhaController;
import com.food.api.v1.model.response.CozinhaResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.Cozinha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaResponseAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public CozinhaResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(CozinhaController.class, CozinhaResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public CozinhaResponse toModel(Cozinha cozinha) {
        CozinhaResponse cozinhaResponse = new CozinhaResponse(cozinha)
                .add(foodLinks.linkToCozinha(cozinha.id()));
        if (foodSecurity.podeConsultarCozinhas()) {
            cozinhaResponse.add(foodLinks.linkToCozinhas("cozinhas"));
        }
        return cozinhaResponse;
    }
}
