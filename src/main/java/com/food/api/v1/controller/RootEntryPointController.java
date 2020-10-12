package com.food.api.v1.controller;

import com.food.api.v1.assembler.FoodLinks;
import com.food.api.v1.model.response.RootEntryPointResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RootEntryPointController {

    private final FoodLinks foodLinks;

    @Autowired
    public RootEntryPointController(FoodLinks foodLinks) {
        this.foodLinks = foodLinks;
    }

    @GetMapping
    public RootEntryPointResponse root() {
        return new RootEntryPointResponse()
                .add(foodLinks.linkToCozinhas("cozinhas"))
                .add(foodLinks.linkToPedidos("pedidos"))
                .add(foodLinks.linkToRestaurantes("restaurantes"))
                .add(foodLinks.linkToGrupos("grupos"))
                .add(foodLinks.linkToUsuarios("usuarios"))
                .add(foodLinks.linkToPermissoes("permissoes"))
                .add(foodLinks.linkToFormasPagamento("formas-pagamento"))
                .add(foodLinks.linkToEstados("estados"))
                .add(foodLinks.linkToCidades("cidades"))
                .add(foodLinks.linkToEstatisticas("estatisticas"));
    }
}
