package com.food.api.v2.assembler;

import com.food.api.v2.controller.CidadeControllerV2;
import com.food.api.v2.controller.CozinhaControllerV2;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FoodLinksV2 {

    public Link linkToCidade(Long cidadeId, String rel) {
        return linkTo(methodOn(CidadeControllerV2.class)
                .porId(cidadeId)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String rel) {
        return linkTo(CidadeControllerV2.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaControllerV2.class).withRel(rel);
    }

    public Link linkToCozinha(Long cozinhaId, String rel) {
        return linkTo(methodOn(CozinhaControllerV2.class)
                .porId(cozinhaId)).withRel(rel);
    }

    public Link linkToCozinha(Long cozinhaId) {
        return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
    }
}
