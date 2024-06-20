package com.food.api.v1.controller;

import com.food.api.v1.assembler.FoodLinks;
import com.food.api.v1.model.response.RootEntryPointResponse;
import com.food.config.FoodSecurity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/v1")
public class RootEntryPointControllerV1 {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public RootEntryPointControllerV1(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @GetMapping
    public RootEntryPointResponse root() {
        var rootEntryPointResponse = new RootEntryPointResponse();

        if (foodSecurity.podeConsultarCozinhas()) {
            rootEntryPointResponse.add(foodLinks.linkToCozinhas("cozinhas"));
        }

        if (foodSecurity.podePesquisarPedidos()) {
            rootEntryPointResponse.add(foodLinks.linkToPedidos("pedidos"));
        }

        if (foodSecurity.podeConsultarRestaurantes()) {
            rootEntryPointResponse.add(foodLinks.linkToRestaurantes("restaurantes"));
        }

        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointResponse
                    .add(foodLinks.linkToGrupos("grupos"))
                    .add(foodLinks.linkToUsuarios("usuarios"))
                    .add(foodLinks.linkToPermissoes("permissoes"));
        }

        if (foodSecurity.podeConsultarFormasPagamento()) {
            rootEntryPointResponse.add(foodLinks.linkToFormasPagamento("formas-pagamento"));
        }

        if (foodSecurity.podeConsultarEstados()) {
            rootEntryPointResponse.add(foodLinks.linkToEstados("estados"));
        }

        if (foodSecurity.podeConsultarCidades()) {
            rootEntryPointResponse.add(foodLinks.linkToCidades("cidades"));
        }

        if (foodSecurity.podeConsultarEstatisticas()) {
            rootEntryPointResponse.add(foodLinks.linkToEstatisticas("estatisticas"));
        }

        return rootEntryPointResponse;
    }
}
