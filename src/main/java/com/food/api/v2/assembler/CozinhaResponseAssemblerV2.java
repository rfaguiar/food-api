package com.food.api.v2.assembler;

import com.food.api.v2.controller.CozinhaControllerV2;
import com.food.api.v2.model.response.CozinhaResponseV2;
import com.food.domain.model.Cozinha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaResponseAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaResponseV2> {

    private final FoodLinksV2 foodLinks;

    @Autowired
    public CozinhaResponseAssemblerV2(FoodLinksV2 foodLinks) {
        super(CozinhaControllerV2.class, CozinhaResponseV2.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public CozinhaResponseV2 toModel(Cozinha cozinha) {
        return new CozinhaResponseV2(cozinha)
                .add(foodLinks.linkToCozinha(cozinha.getId()))
                .add(foodLinks.linkToCozinhas("cozinhas"));
    }
}
