package com.food.api.v2.controller;

import com.food.api.RootEntryPointControllerOpenApi;
import com.food.api.v1.model.response.RootEntryPointResponse;
import com.food.api.v2.assembler.FoodLinksV2;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/v2")
public class RootEntryPointControllerV2 implements RootEntryPointControllerOpenApi {

    private final FoodLinksV2 foodLinks;

    @Autowired
    public RootEntryPointControllerV2(FoodLinksV2 foodLinks) {
        this.foodLinks = foodLinks;
    }

    @GetMapping
    public RootEntryPointResponse root() {
        return new RootEntryPointResponse()
                .add(foodLinks.linkToCozinhas("cozinhas"))
                .add(foodLinks.linkToCidades("cidades"));
    }
}
