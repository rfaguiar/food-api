package com.food.api.v3.assembler;

import com.food.api.v3.model.response.CozinhaResponseV3;
import com.food.api.v3.controller.CozinhaControllerV3;
import com.food.domain.model.Cozinha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaResponseAssemblerV3 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaResponseV3> {

    private final FoodLinksV3 foodLinks;

    @Autowired
    public CozinhaResponseAssemblerV3(FoodLinksV3 foodLinks) {
        super(CozinhaControllerV3.class, CozinhaResponseV3.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public CozinhaResponseV3 toModel(Cozinha cozinha) {
        return new CozinhaResponseV3(cozinha)
                .add(foodLinks.linkToCozinha(cozinha.getId()))
                .add(foodLinks.linkToCozinhas("cozinhas"));
    }
}
