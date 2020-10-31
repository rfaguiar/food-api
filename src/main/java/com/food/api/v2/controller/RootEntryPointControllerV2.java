package com.food.api.v2.controller;

import com.food.api.v1.model.response.RootEntryPointResponse;
import com.food.api.v2.assembler.FoodLinksV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/v2")
public class RootEntryPointControllerV2 {

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
