package com.food.api.assembler;

import com.food.api.controller.CozinhaController;
import com.food.api.model.response.CozinhaResponse;
import com.food.domain.model.Cozinha;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CozinhaResponseAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaResponse> {

    public CozinhaResponseAssembler() {
        super(CozinhaController.class, CozinhaResponse.class);
    }

    @Override
    public CozinhaResponse toModel(Cozinha cozinha) {
        CozinhaResponse cozinhaResponse = new CozinhaResponse(cozinha);

        cozinhaResponse.add(linkTo(
                methodOn(CozinhaController.class).porId(cozinhaResponse.getId())
        ).withSelfRel());

        cozinhaResponse.add(linkTo(CozinhaController.class).withRel("cozinhas"));

        return cozinhaResponse;
    }
}
